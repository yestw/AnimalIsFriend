<template>
  <div id="petViewPage">
    <div>
      <label for="file">
        <img :src="pet.imageUrl" id="img-box" />
      </label>
      <div class="black-bg" v-if="chatClick === true">
        <div class="white-bg">
          <h3>채팅방을 만드시겠습니까?</h3>
          <label>채팅방 이름: </label><input type="text" v-model="chatRoomName">
          <button @click="createChatRoom()">채팅방 생성</button>
          <button v-on:click="chatClick = false" class="modal-exit-btn">
            취소
          </button>
        </div>
      </div>
      <div>
        <label>귀한 주인분 : <strong>{{ pet.userNickname }}</strong></label>
        </div>
        <div>
          <label>반려동물 이름 : <strong>{{ pet.petName }}</strong></label>
        </div>
        <div>
          <label>반려동물 탄생일 : <strong>{{ pet.petBirth }}</strong></label>
        </div>
        <div>
          <label>반려동물 취득일 : <strong>{{ pet.petAcquire }}</strong></label>
        </div>
        <div>
          <label>반려동물 성별 : <strong>{{ pet.petGender }}</strong></label>
        </div>
        <div v-if="pet.petCategory === 'DOG'">
          <div>
            <label>
              반려동물 크기 :
              <strong>
                {{
                  pet.petSize === "SMALL" ? "소형견" :
                  pet.petSize === "MIDDLE" ? "중형견" :
                  pet.petSize === "BIG" ? "대형견" : "특수견"
                }}
              </strong>
            </label>
          </div>
        </div>
        <div>
          <label>반려동물 품종 : <strong>{{ pet.petBreed }}</strong></label>
        </div>
        <div>
          <label>반려동물 사료 : <strong>{{ pet.petFeed }}</strong></label>
        </div>
        <div>
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
        <div>
          <label>반려동물 소개 : <strong>{{ pet.petDescription }}</strong></label>
        </div>
        <input type="hidden" v-bind:value="pet.petId">
        <input type="hidden" v-bind:value="pet.id">
        <button @click="toChatting()">채팅하기</button>
    </div>
    <br>
    <router-link to="/">홈으로</router-link>
  </div>
</template>
    
<script>
import { petDetail } from "../service/api/pets";
import { checkChatRoom, createChatRoom } from "../service/api/chat";

export default {
  data() {
    return {
      pet: {},
      chatClick: false,
      chatRoomName: "",
      userId: null,
    }
  },
  methods: {
    async toChatting() {
      this.chatRoomName = this.pet.petName+" 채팅방";
      const userId = {
        userId : Number(this.pet.id),
      };
      const res = await checkChatRoom(userId, this.$getAccessToken());
      if(res) {
        if(res.data === true) {
          //이미 만들어져있는 채팅방으로 이동
          this.$router.push("/chatroom");
        }
        this.chatClick=true;
      }
    },
    async createChatRoom() {
      this.userId = this.pet.id;
      const chatRoomCreateDto = {
        chatroomName: this.chatRoomName,
        userId: this.userId,
        petId: this.pet.petId,
      };
      const res = await createChatRoom(chatRoomCreateDto, this.$getAccessToken());
      if(res) {
        if(res.data === "CHATROOM_CREATE_SUCCESS") {
          if(confirm("채팅방이 만들어 졌습니다. 바로 이동하시겠습니까?")) {
            this.$router.push("/chatroom");
          }
        }
      }
    },
  },
  async mounted() {
  const petId = this.$route.params.pid;
  const pet = await petDetail(petId);
  this.pet = pet.data;
  },
}
</script>
    
<style scoped>
.black-bg {
  display: flex;
  align-items: center;
  width: 40%;
  height: 40%;
  background-color: rgba(0, 0, 0, 0.432);
  position: fixed;
  padding: 20px;
}

.white-bg {
  width: 100%;
  background-color: white;
  padding: 20px;
  border-radius: 8px;
}

.modal-exit-btn {
  margin-top: 30px;
}

.modal-exit-btn:hover {
  cursor: pointer;
}

#img-box {
  border-radius: 10px solid black;
  margin: 0 auto;
  width: 100%;
  height: 100%;
  box-shadow: 2px 2px 2px 2px #a9eaa6;
}
</style>