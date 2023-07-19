<template>
    <div>
      <div id="adoptInfo">
        <h3>반려동물을 입양후 반려동물 등록은 의무입니다.</h3>
        <h4>입양을 희망하는 반려동물이 등록 되었는지 확인 하세요!</h4>
        <a href="https://www.animal.go.kr/front/community/show.do;jsessionid=9gtu1KzRqQqasi1nYdO9kBeIjDmaI1u0A9PhFa94dytklc2wxhsxe4RAbLebqvKs.aniwas_servlet_front?boardId=contents&seq=66&menuNo=2000000016">
          동물 등록제란?
        </a>
      </div>
      <div id="petViewPage">
        <div v-for="pet in pets" v-bind:key="pet.petId" class="pet-list">
          <div @click="detailPet(pet.petId)">
            <label for="file">
              <img :src="pet.imageUrl" id="img-box" />
            </label>
            <div class="pet-box">
              <div class="pet-box">
                <label>귀한 주인분 : <strong>{{ pet.userNickname }}</strong></label>
              </div>
              <div class="pet-box">
                <label>반려동물 이름 : <strong>{{ pet.petName }}</strong></label>
              </div>
              <div class="pet-box">
                <label>반려동물 탄생일 : <strong>{{ pet.petBirth }}</strong></label>
              </div>
              <div class="pet-box">
                <label>반려동물 성별 : <strong>{{ pet.petGender }}</strong></label>
              </div>
              <div class="pet-box">
                <div>
                  <label>반려동물 크기 :
                    <strong>
                      {{ pet.petCategory === "DOG" ? "강아지" : pet.petCategory === "CAT" ? "고양이" : "기타" }}
                    </strong>
                  </label>
                </div>
              </div>
              <div class="pet-box">
                <label>반려동물 품종 : <strong>{{ pet.petBreed }}</strong></label>
              </div>
              <div class="pet-box">
                <label>
                  반려동물 입양상태 :
                  <strong>
                    {{
                      pet.petStatus === "NOT_ADOPTED" ? "미입양" :
                      pet.petStatus === "ADOPTION_WAITING" ? "입양 대기중" :
                      pet.petStatus === "ADOPTION_COMPLETE" ? "입양완료" : "미파악"
                    }}
                  </strong>
                </label>
              </div>
            </div>
            <input type="hidden" v-bind:value="pet.petId">
          </div>
        </div>
      </div>
      <br>
    </div>
  </template>
    
  <script>
  import { petView } from "../service/api/pets";
  
  export default {
    data() {
      return {
        pets: [],
        petStatus: "",
      }
    },
    async mounted() {
      const res = await petView();
      this.pets = res.data;
      console.log(this.pets.petStatus);
    },
    methods: {
      detailPet(petId) {
        this.$router.push(
          {
            name: "petDetail",
            params: {
              pid: petId,
            }
          }
        )
      }
    }
  }
  </script>
    
<style scoped>
#petViewPage {
  display: flex;
  align-items: center;
}

#adoptInfo{
  width: 100%;
  text-align: center;
  height: 100px;
  background-color: white;
  border-radius: 40px;
  box-shadow: 1px 2px 7px 1px black;
}
.pet-list {
  width: 20rem;
  border: 1px solid black;
  padding: 20px;
  text-align: left;
  margin: 20px;
}

.pet-box {
  margin: 5px;
}

#img-box {
  border-radius: 10px solid black;
  margin: 0 auto;
  width: 20rem;
  height: 12rem;
  box-shadow: 1px 1px 3px 1px black;
}
</style>