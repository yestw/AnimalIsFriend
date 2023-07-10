<template>
    <div id="homePage">
      <h1>메인페이지</h1>
      <router-link to="/login">로그인</router-link><br>
      <router-link to="/signup">회원가입</router-link>
    
      <h1>롤: {{ role }}</h1>
      <h1>토큰정보: {{ userId }}</h1>
      <h1>액세스토큰: {{ accessToken }}</h1>
      <h1>액세스토큰: {{ tokenInfo }}</h1>
    </div>
</template>
  
<script>
export default {
    data() {
      return {
        role: "",
        userId: "",
        tokenInfo: "",
      }
    },
    mounted() {
      const accessToken = this.$getAccessToken();
      this.role = this.$getTokenInfo(accessToken).role;
      this.userId = this.$getTokenInfo(accessToken).userId;
      this.tokenInfo = this.$getAccessToken2(accessToken);
      // if(this.role === "GUEST") {
      //   this.$router.push("/signup");
      // }
    }
}
</script>
  