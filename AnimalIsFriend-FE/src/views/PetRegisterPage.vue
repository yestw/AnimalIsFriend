<template>
  <div id="signupPage">
    <div>
      <label for="file">
        <div class="btn-upload"><img :src="imageUrl" id="img-box" /></div>
        <label>⬆️클릭해서 이미지를 넣어주세요!</label>
      </label>
      <input
        id="file"
        type="file"
        ref="fileInput"
        accept="image/*"
        @change="getFileName($event.target.files)"
      />
    </div>
    <div>
      <label>반려동물 이름 : </label>
      <input type="text" v-model="petName">
    </div>
    <div>
      <label>반려동물 성별 : </label>
      <label>남자</label>
      <input type="radio" name="gender" v-model="petGender" value="남자">
      <label>여자</label>
      <input type="radio" name="gender" v-model="petGender" value="여자">
    </div>
    <div>
      <label>반려동물 탄생일 : </label>
      <input type="date" name="petBirth" v-model="petBirth">
    </div>
    <div>
      <label>반려동물 취득일 : </label>
      <input type="date" name="petAcquire" v-model="petAcquire">
    </div>
    <div>
      <label>반려동물 사료 정보: </label>
      <br />
      <label>이전에 먹었던 사료를 적어주세요</label>
      <br />
      <label>모르겠으시면 '미파악' 이라고 적어주세요!</label>
      <input type="text" v-model="petFeed">
    </div>
    <div>
      <label>반려동물 카테고리 : </label>
      <select v-model="petCategory">
        <option value="DOG">강아지</option>
        <option value="CAT">고양이</option>
      </select>
    </div>
    <div v-if="petCategory === 'DOG'">
      <div>
        <label>반려동물 크기 : </label>
        <select v-model="petSize">
          <option value="SMALL">소형견</option>
          <option value="MIDDLE">중형견</option>
          <option value="BIG">대형견</option>
        </select>
      </div>
      <div>
        <label>강아지 품종 : </label>
        <select v-model="petBreed">
          <option v-for="(i) in dogBreedList" v-bind:key="i">
            {{ i }}
          </option>
        </select>
      </div>
    </div>
    <div v-else-if="petCategory === 'CAT'">
      <div>
        <label>고양이 품종 : </label>
        <select v-model="petBreed">
          <option v-for="(i) in catBreedList" v-bind:key="i">
            {{ i }}
          </option>
        </select>
      </div>
    </div>
    <div>
      <label>반려동물 간단 설명 : </label>
      <br />
      <p>
        이 공간에는 반려동물이 입양되었을 때 입양자에게 도움이 될만 한
        정보를 100자 이하로 적어주세요
      </p>
      <p>
        ex) 반려동물 부모의 정보 및 반려동물의 성격, 산책정보, 예방접종 유무, 예방접종의 정보
      </p>
      <p>잘못된 정보일 시 불이익이 존재할수도 있습니다.</p>
      <textarea v-model="petDescription"></textarea>
    </div>
    <button @click="registerPet()">반려동물 등록</button>
    <br>
  </div>
</template>
  
<script>
import { register } from "../service/api/pets";
import { test } from "../service/api/amazon";
import axios from "axios";

export default {
  data() {
    return {
      petName: "",
      petGender: "",
      petBirth: "",
      petAcquire: "",
      petFeed: "",
      petCategory: "",
      petSize: "",
      dogBreedList: [],
      dogBreedObject: [
        [
          "포메라니안", "푸들", "요크셔테리어", "치와와",
          "닥스훈트", "페키니즈", "시추", "말티즈", "기타",
        ],
        [
          "시바견", "슈나우저", "스피츠", "불독",
          "웰시코기", "코커스파니엘", "비글", "기타",
        ],
        [
          "말라뮤트", "도베르만", "롯트와일러", "러프콜리",
          "사모예드", "셰퍼트", "허스키", "골든리트리버",
          "진돗개", "기타",
        ]
      ],
      catBreedList: [
        "도메스틱숏헤어", "아비시니안", "샴", "아메리칸숏헤어",
        "러시안블루", "페르시안", "터키쉬앙고라", "벵갈", "노르웨이숲",
        "랙돌", "기타",
      ],
      petBreed: "",
      petDescription: "",
      imageUrl: "",
      fileName: "",
      preSignedUrl: null,
      encodedFileName: null,
    }
  },
  watch: {
    petSize () {
      switch(this.petSize) {
        case "SMALL":
          this.dogBreedList = this.dogBreedObject[0];
          break;
        case "MIDDLE":
          this.dogBreedList = this.dogBreedObject[1];
          break;
        case "BIG":
          this.dogBreedList = this.dogBreedObject[2];
          break;
      }
    }
  },
  methods: {
    async getFileName(files) {
      this.fileName = files[0];
      await this.base64(this.fileName);
    },
    base64(file) {
      return new Promise((resolve) => {
        let success = new FileReader();
        success.onload = (e) => {
          resolve(e.target.result);
          const previewImage = document.getElementById("img-box");
          previewImage.src = e.target.result;
        };
        success.readAsDataURL(file);
      });
    },
    async imageUploadS3() {
      const res = await test(this.$getAccessToken());
      this.preSignedUrl = res.data.preSignedUrl;
      this.encodedFileName = res.data.encodedFileName;
      // this.uploadImage(this.preSignedUrl, this.encodedFileName);

      const fileInput = this.$refs.fileInput;
      const file = fileInput.files[0];
      // const fileName = file.name;

      await axios.put(this.preSignedUrl, file);

      const uploadUrl = `${process.env.VUE_APP_S3_PATH}/${this.encodedFileName}`;
      this.imageUrl = uploadUrl;
      console.log(this.imageUrl)

    },
    async registerPet() {
      await this.imageUploadS3();

      if(this.petCategory === "CAT") {
        this.petSize = "X";
      }

      if (
        this.petName === "" || this.petGender === "" || this.petBirth === "" ||
        this.petAcquire === "" || this.petFeed === "" || this.petCategory === "" || this.petSize === "" ||
        this.petBreed === "" || this.petDescription === ""
      ) {
        alert("빈 칸이 있으면안됩니다.");
      } else {
        const registerPetForm = {
          petName: this.petName,
          petGender: this.petGender,
          petBirth: this.petBirth,
          petAcquire: this.petAcquire,
          petFeed: this.petFeed,
          petCategory: this.petCategory,
          petSize: this.petSize,
          petBreed : this.petBreed,
          petDescription: this.petDescription,
          imageUrl: this.imageUrl
        };
  
        register(registerPetForm, this.$getAccessToken());
      }
    },
  },
}
</script>
  
<style scoped>
#signupPage {
  align-items: center;
}

#img-box {
  border-radius: 100px;
  border-radius: 10px solid black;
  margin: 0 auto;
  width: 100%;
  height: 100%;
  box-shadow: 2px 2px 2px 2px rgb(129, 118, 118);
}

.btn-upload {
  width: 200px;
  height: 200px;
  background: #fff;
  border-radius: 100px;
  font-weight: 500;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
}

#file {
  display: none;
}

button {
  font-family: 'EF_watermelonSalad', cursive;
  border: 0px;
  height: 30px;
  border-radius: 10px;
}

button:hover {
  background-color: wheat;
}

textarea {
  width: 500px;
  height: 50px;
  word-wrap: break-word;
}
</style>