<template>
  <div>
    <div class="form">
      <div class="login">
        <h2>로그인</h2>
        <br />
        <hr />
        <br />
        <img
          @click="ToGoogleLogin()"
          src="/google_login.png"
          width="295"
          height="60"
        />
        <br />
        <br />
        <img
          src="/kakao_login_large_wide.png"
          @click="ToKakaoLogin()"
          width="290"
        />
        <p></p>
        <p><button @click="toHome()">홈으로</button></p>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "loginPage",
  data() {
    return {
      GoogleLogin:
        process.env.VUE_APP_LOGIN_URL +
        `/oauth2/authorize/google` +
        process.env.VUE_APP_REDIRECT_URL,
      KaKaoLogin:
        process.env.VUE_APP_LOGIN_URL +
        `/oauth2/authorize/kakao` +
        process.env.VUE_APP_REDIRECT_URL,
    };
  },
  methods: {
    ToKakaoLogin() {
      location.href = this.KaKaoLogin;
    },
    ToGoogleLogin() {
      location.href = this.GoogleLogin;
    },
    toHome() {
      this.$router.push("/");
    }
  },
};
</script>
<style scoped>
.form {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 5%;
  background-color: white;
  max-width: 400px;
  margin-left: auto;
  margin-right: auto;
  border-radius: 12px;
  padding-bottom: 20px;
}

.login {
  text-align: center;
}

button {
  width: 100%;
  margin-top: 10px;
}
h5,
h2 {
  font-family: "Hanna";
}
</style>
