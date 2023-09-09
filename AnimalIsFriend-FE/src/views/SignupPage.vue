<template>
    <div id="signupPage">
      <h3>추가회원가입이 필요합니다. 정보를 입력해주세요.</h3>
      <div>
        <label>이름 : </label>
        <input type="text" v-model="userName">
      </div>
      <div>
        <label>전화번호 : </label>
        <input type="text" v-model="hp1" maxlength="3">-
        <input type="text" v-model="hp2" maxlength="4">-
        <input type="text" v-model="hp3" maxlength="4">
      </div>
      <div>
        <label>성별 : </label>
        <label>남자</label>
        <input type="radio" name="gender" v-model="userGender" value="남자">
        <label>여자</label>
        <input type="radio" name="gender" v-model="userGender" value="여자">
      </div>
      <div>
        <label>주소 : </label>
        <input type="text" v-model="userAddress">
      </div>
      <button @click="signupUser()">회원가입</button>
      <br>
      <router-link to="/">홈으로</router-link>
    </div>
  </template>
  
    <script>
    import { signUp } from "../service/api/users";
  
    export default {
      data() {
        return {
          userName: "",
          hp1: "",
          hp2: "",
          hp3: "",
          userGender: "",
          userAddress: "",
          accessToken: "",
        }
      },
      mounted() {
        this.accessToken = this.$getAccessToken();
      },
      methods: {
        async signupUser() {
          try {
            if(this.userName === "" || this.userHp === "" ||
               this.userGender === "" || this.userAddress === "" ) {
                alert("빈칸을 모두 채워주세요");
            } else {
              const signupForm = {
                userName: this.userName,
                userHp: this.hp1 + this.hp2 + this.hp3,
                userGender: this.userGender,
                userAddress: this.userAddress,
              };
              const res = await signUp(signupForm, this.$getAccessToken());
              if(res.data === "SIGNUP_SUCCESS") {
                alert("회원가입이 완료되었습니다. 다시 로그인 해주세요");
                document.cookie = "accessTokenCookie=; path=/; domain=.animalisfriend.shop; SameSite=None; Secure; Max-Age=0;";
                location.href = "/";
              }
            }
          } catch(err) {
            console.log(err);
          }
        },
      },
    }
    </script>
  
<style scoped>
</style>