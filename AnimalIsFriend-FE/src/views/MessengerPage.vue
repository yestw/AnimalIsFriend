<template>
  <div id="chatPage">
    <div id="chat-header">
      {{ chatroomName }}
    </div>
    <label for="file">
      <img :src="imageUrl" id="img-box" />
    </label>
    <div v-if="isPetOwner > 0">
      <select v-model="petStatus">
        <option selected>{{ petStatus }}</option>
        <option value="미입양" >미입양</option>
        <option value="입양 대기중">입양 대기중</option>
        <option value="입양 완료">입양 완료</option>
      </select>
      <button @click="adoptUpdate(petStatus)">상태 변경</button>
    </div>
    <div class="message-box">
      <div class="chat-box" v-for="(message, index) in messageList.slice().reverse()" v-bind:key="index">
        <div>
        </div>
        <div v-if="message.senderId===userId">
          <div class="my-chat">
            <div class="nickname-font">
              {{ message.senderName }}
            </div>
            <div class="message-font">
              {{ message.message }}
            </div>
          </div>
          <div class="my-read-span">
            <label class="read-label">{{ message.readChat === false ? 1 : "읽음" }}</label>
            <label>{{ message.createdDate }}</label>
          </div>
        </div>
        <div v-else>
          <div class="other-chat">
            <div class="nickname-font">
              {{ message.senderName }}
            </div>
            <div class="message-font">
              {{ message.message }}
            </div>
          </div>
          <div class="other-read-span">
            <label class="read-label">{{ message.readChat === false ? 1 : "읽음" }}</label>
            <label>{{ message.createdDate }}</label>
          </div>
        </div>
      </div>
    </div>
    <div class="send-box">
      <input type="text" v-model="message" class="message-input">
      <button @click="send()" class="send-btn">전송</button>
    </div>
  </div>
</template>

<script>
import Stomp from "webstomp-client";
import SockJS from "sockjs-client";
import { getChat, readChat } from "../service/api/chat";
import { adoptSuccess } from "../service/api/adopt";
import { petStatusUpdate } from "../service/api/pets";

export default {
  data() {
    return {
      accessToken: "",
      readChat: false,
      unReadChatCount: 0,
      roomId: null,
      userId: "",
      message: null,
      stompClient: null,
      connected: false,
      messageList: [],
      nickname: null,
      date: null,
      chatRoomName: null,
      dateFormat: "",
      chatroomName: "",
      petId: null,
      imageUrl: null,
      petStatus: null,
      currentStatus: null,
      isPetOwner: null,
    }
  },
  async mounted() {
    const userId = this.$getTokenInfo(this.$getAccessToken()).userId;
    this.userId = Number(userId);
    this.roomId = this.$route.params.chatroomId;
    this.unReadChatCount = this.$route.params.unReadChatCount;
    this.chatroomName = this.$route.params.chatroomName;
    this.petId = this.$route.params.petId;
    this.isPetOwner = this.$route.params.isPetOwner;
    this.imageUrl = this.$route.params.imageUrl;
    this.petStatus = this.$route.params.petStatus;
    this.petStatus = this.petStatus === "NOT_ADOPTED" ? "미입양" :
                     this.petStatus === "ADOPTION_WAITING" ? "입양 대기중" :
                     this.petStatus === "ADOPTION_COMPLETE" ? "입양완료" : "미파악";
    this.currentStatus = this.petStatus;

    await this.webSocketConnect();

    setTimeout(() => {
      this.getChat();
    }, 1000);
  },
  methods: {
    async adoptUpdate(petStatus) {

      if(this.currentStatus === "입양 완료") {
        alert("입양 완료가 되었으면 변경 불가 합니다.")
        return;
      }

      if (this.currentStatus === petStatus) {
        alert("같은 상태로는 변경이 안됩니다.");
        return;
      } else {
        const adoptRequestForm = {
          petId: this.petId,
          petStatus: "",
        }
  
        if(this.petStatus == "미입양") {
          adoptRequestForm.petStatus = "NOT_ADOPTED";
  
          alert(adoptRequestForm.petStatus);
          await petStatusUpdate(adoptRequestForm, this.$getAccessToken())
          this.currentStatus = petStatus;

        } else if(this.petStatus == "입양 대기중") {
          adoptRequestForm.petStatus = "ADOPTION_WAITING";
  
          alert(adoptRequestForm.petStatus);
          await petStatusUpdate(adoptRequestForm, this.$getAccessToken())
          this.currentStatus = petStatus;
        } else {
          adoptRequestForm.petStatus = "ADOPTION_COMPLETE";
          alert(adoptRequestForm.petStatus);
          await adoptSuccess(adoptRequestForm, this.$getAccessToken());
          this.currentStatus = petStatus;
        }
      }
    },
    async readChatRequest() {
      if(this.unReadChatCount > 0) {
        this.readChat = true;
        await readChat(this.$getAccessToken(), this.roomId, this.readChat);
      }
    },
    async getChat() {
      const accessToken = this.$getAccessToken();

      const date = new Date();
      const year = date.getFullYear();
      const month = ('0' + (date.getMonth() + 1)).slice(-2);
      const day = ('0' + date.getDate()).slice(-2);
      const requestDate = `${year}-${month}-${day}`;

      const res = await getChat(accessToken, this.roomId, requestDate);
      
      if(res) {
        this.messageList = res.data;
        this.messageList.forEach((message) => {
          message.createdDate =
          `
            ${message.createdDate[0]} 년
            ${message.createdDate[1]} 월
            ${message.createdDate[2]} 일
            ${message.createdDate[3]} :
            ${message.createdDate[4]} 
          `;
        })
      }
    },
    async send() {
      const chatDto = {
        senderId: this.userId,
        message: this.message,
        readChat: false,
      }

      if(this.message == "" || this.roomId === undefined) {
        alert("채팅을 입력해주세요.")
        return;
      } else if(this.stompClient) {
        await this.stompClient.send(`/pub/queue/${this.roomId}`, JSON.stringify(chatDto));
        await this.readChatRequest();
      }
    },
    async webSocketConnect() {
      const serverUrl = process.env.VUE_APP_WEBSOCKET_URL
      const socket = new SockJS(serverUrl + "/ws");
      this.stompClient = Stomp.over(socket);
      await this.readChatRequest();

      this.stompClient.connect(
        {
          Authorization: this.$getAccessToken(), 
        },
        frame => {
          this.connected = true;
          this.stompClient.subscribe(`/queue/${this.roomId}`, (res) => {
            console.log(frame);

            const chatting = JSON.parse(res.body);
            const inputDate = new Date(chatting.createdDate);

            const year = inputDate.getFullYear();
            const month = inputDate.getMonth() + 1; // 월은 0부터 시작하므로 1을 더해줍니다.
            const day = inputDate.getDate();
            const hour = inputDate.getHours();
            const minute = inputDate.getMinutes();

            const formattedDate = `${year} 년 ${month} 월 ${day} 일 ${hour} : ${minute}`;
            chatting.createdDate = formattedDate;

            this.messageList.unshift(chatting);
            
          });
        },
        error => {
          console.log("소켓 연결 실패", error);
          this.connected = false;
        }
      );
    },
  },
}
</script>

<style scoped>
#chatPage {
  width: 60%;
  border: 1px solid black;
  height: calc(85% - 3rem) !important;
  margin-top: 10px;
}

#chat-header {
  height: 50px;
  text-align: center;
  line-height: 60px;
  font-size: 25px;
  font-weight: 900;
  border-bottom: 1px solid #ddd;
  background-color: darkseagreen;
}

.message-box {
  width: 100%;
  height: 600px;
  border: 1px solid black;
  overflow: hidden scroll;
  text-align: center
}

.send-box {
  width: 100%;
  height: 50px;
  background-color: brown;
}

.message-input {
  width: 80%;
  height: 50px;
}
.send-btn {
  width: 40px;
  height: 25px;
  margin: 10px;
  border: 0px;
  border-radius: 50px;
}

.my-chat {
  margin: 10px;
  text-align: right;
  background-color: rgb(232, 172, 172);
  border-radius: 20px;
  padding: 10px;
  word-wrap: break-word;
  margin-bottom: 10px;
  margin-top: 5px;
}

.other-chat {
  margin: 10px;
  text-align: left;
  background-color: white;
  border-radius: 20px;
  padding: 10px;
  word-wrap: break-word;
  margin-bottom: 10px;
  margin-top: 5px;
}

.nickname-font {
  margin: 10px;
  font-size: 7px;
}
.message-font {
  margin: 10px;
  font-size: 20px;
}

.my-read-span {
  font-size: 5px;
  text-align: right;
  margin-right: 20px;
  margin-bottom: 20px;
} 

.other-read-span {
  font-size: 5px;
  text-align: left;
  margin-right: 20px;
  margin-bottom: 20px;
} 

.read-label {
  margin-right: 10px;
}

#img-box {
  border-radius: 10px solid black;
  margin: 0 auto;
  width: 50%;
  height: 30%;
  border-radius: 50px;
  text-align: center;
  display: flex;
}

</style>