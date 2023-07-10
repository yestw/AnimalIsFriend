<template>
    <div id="signupPage">
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
      <h1>{{ accessToken }}</h1>
    </div>
  </template>
  
    <script>
    import { signUp } from "../service/api";
  
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
            const signupForm = {
              userName: this.userName,
              userHp: this.hp1 + this.hp2 + this.hp3,
              userGender: this.userGender,
              userAddress: this.userAddress,
            };
            alert("sadasdasdsd");
            const res = signUp(signupForm, this.$getAccessToken());
            console.log(res);
            alert(res);
          } catch(err) {
            console.log(err);
          }
        },
      },
    }
    </script>
  
<style scoped>
</style>