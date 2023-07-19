<template>
  <div id="footer">
    반려동물은 나의 가족입니다.
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
    }
  },
  mounted() {
    if (!(this.$getAccessToken() === null)) {
      const accessToken = this.$getAccessToken();
      this.accessToken = accessToken;
      this.role = this.$getTokenInfo(accessToken).role;
      this.userId = this.$getTokenInfo(accessToken).userId;
    }
    // if(this.role === "GUEST") {
    //   this.$router.push("/signup");
    // }
  }
}
</script>
<style scoped>
#footer {
  background-color: silver;
  height: 60px;
  position: relative;
  transform: translateY(0%);

  display: flex;
  align-items: center;
  justify-content: center;
}
</style>