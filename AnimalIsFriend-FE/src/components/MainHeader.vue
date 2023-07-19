<template>
  <div id="header">
    <div id="logo">
      <p @click="goHome()">AnimalIF</p>
    </div>
    <div id="menu">
      <ul class="menu-ul">
        <li><button @click="goPetRegister()">반려동물 등록</button></li>
        <li><button @click="goPetView()">반려동물 조회</button></li>
        <li><button @click="noService()">입양 할게요</button></li>
      </ul>
    </div>
    <div id="tab">
      <div v-if="!isLogined">
        <div class="login">
          <button @click="goLogin()">로그인</button>
        </div>
      </div>
      <div v-else>
        <div class="mypage">
          <button @click="logout()">로그아웃</button>
          <button @click="noService()">마이페이지</button>
        </div>
      </div>
    </div>
  </div>
</template>
  
<script>
export default {
  data() {
    return {
      role: "",
      userId: "",
      tokenInfo: "",
      accessToken: "",
      isLogined: false,
    }
  },
  mounted() {
    if(!(this.$getAccessToken() === null)) {
      const accessToken = this.$getAccessToken();
      this.accessToken = accessToken;
      this.role = this.$getTokenInfo(accessToken).role;
      this.userId = this.$getTokenInfo(accessToken).userId;
      this.isLogined = accessToken != "" && accessToken != null;
    }
  },
  methods: {
    goPetRegister() {
      if(!this.isLogined) {
        alert("로그인 후 이용해주세요");
        return;
      } else {
        this.$router.push("/register");
      }
    },
    goPetView() {
      this.$router.push("/pet");
    },
    goLogin() {
      this.$router.push("/login");
    },
    goHome() {
      this.$router.push("/");
    },
    logout() {
      document.cookie = "accessTokenCookie=; path=/; domain=localhost; SameSite=None; Secure; Max-Age=0;";
      this.isLogined = false;
      location.href = "/";
    },
    noService() {
      alert("아직 준비중인 서비스 입니다.");
    }
  },
}
</script>
<style scoped>
button {
  font-family: 'EF_watermelonSalad', cursive;
  border: 0px;
  height: 30px;
  border-radius: 10px;
}

button:hover {
  background-color: wheat;
}

#header {
  border-bottom: 1px solid black;
  height: 80px;
  margin: 0 auto;
  display: flex;
  justify-content: center;
}

#logo {
  width: 10rem;
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50px;
}

#logo:hover {
  background-color: wheat;
}

#menu {
  width: 30rem;
  display: flex;
  align-items: center;
  justify-content: center;
}

.menu-ul>li {
  width: 100px;
  padding: 30px;
}

#tab {
  float: right;
  width: 30rem;
  display: flex;
  align-items: center;
  justify-content: center;
}

ul {
  list-style: none;
  display: flex;
}

</style>