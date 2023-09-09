<template>
  <div class="chatBody">
    <div id="chatHeader">
      <div>채팅방 목록</div>
    </div>
    <div id="myChatContent">
      <div v-for="i in chatrooms" v-bind:key="i.chatroomId">
        <div class="msg-box">
          <label for="file">
            <img :src="i.imageUrl" id="img-box" />
          </label>
          <div>
            {{
              i.petStatus === "NOT_ADOPTED" ? "미입양" :
              i.petStatus === "ADOPTION_WAITING" ? "입양 대기중" :
              i.petStatus === "ADOPTION_COMPLETE" ? "입양완료" : "미파악"
            }}
          </div>
          <div
            @click="ToChatting(
              i.chatroomId, i.unReadChatCount, i.chatroomName, i.imageUrl, i.petId, i.petStatus, i.isPetOwner
            )"
          >
            <div class="row">
              <div class="info-box col">
                <div class="site-name">{{ i.chatroomName }}</div>
                <div v-if="i.creatorId !== userId">
                  <div class="last-msg">
                    {{ i.creatorName }}
                  </div>
                  <div v-if="i.unReadChatCount" id="unread-box">
                    {{ i.unReadChatCount }}
                  </div>
                </div>
                <div v-if="i.invitedId !== userId">
                  <div class="last-msg">
                    {{ i.invitedName }}
                  </div>
                  <div v-if="i.unReadChatCount" id="unread-box">
                    {{ i.unReadChatCount }}
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
      <div v-if="chatrooms.length===0" class="centered-container">
        <div>
          <h5><p>아직 개설한 채팅방이 없습니다!</p></h5>
        </div>
      </div>
    </div>
  </div>
</template>
  
<script>
import { getChatRoom } from "../service/api/chat";

export default {
  name: "ChatRoom",
  data() {
    return {
      roomId: "",
      userId: null,
      role: null,
      chatrooms: [],
      nickname: "",
    };
  },
  async mounted() {
    try {
      const accessToken = this.$getAccessToken();
      const role = this.$getTokenInfo(accessToken).role;
      const userId = this.$getTokenInfo(accessToken).userId;
      this.role = role;
      this.userId = Number(userId);

      const res = await getChatRoom(accessToken);
      this.chatrooms = res.data;
    } catch (err) {
      console.log(err);
    }
  },
  methods: {
    ToChatting(chatroomId, unReadChatCount, chatroomName, imageUrl, petId, petStatus, isPetOwner) {
      this.$router.push(
        {
          name: "messenger",
          params: {
            chatroomId: chatroomId,
            unReadChatCount: unReadChatCount,
            chatroomName: chatroomName,
            imageUrl: imageUrl,
            petId: petId,
            petStatus: petStatus,
            isPetOwner: isPetOwner,
          }
        }
      )
    },
  },
};
</script>
  
<style scoped>
body {
  position: relative;
  width: 100%;
  height: 100%;
}


#chatHeader {
  height: 60px;
  text-align: center;
  line-height: 60px;
  font-size: 25px;
  font-weight: 900;
  border-bottom: 1px solid #ddd;
  background-color: rgb(198, 226, 133);
}

.chatBody {
  width: 30%;
  height: 100%;
  border: 1px solid black;
}

.msg-box {
  border-bottom: 1px solid black
}

.outHeader {
  justify-content: center;
  display: flex;
}

.innerHeader {
  padding-top: 20px;
}

#img-box {
  border-radius: 10px solid black;
  margin: 0 auto;
  width: 100%;
  height: 100%;
}

#unread-box {
  width: 10px;
  height: 10px;
  background-color: red;
}

</style>