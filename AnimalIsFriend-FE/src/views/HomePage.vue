<template>
    <div id="homePage">
      <div>
        <h1>Animal Is Friend</h1>
        <h3>반려동물을 기르기 전 고려해야 할 사항</h3>
        <p>1. 반려동물을 입양 또는 분양을 받기 전에 모든 가족 구성원이 동의하고 충분히 생각해 보셨나요?</p>
        <p>2. 개와 고양이의 수명은 약 15년 정도입니다. 살아가면서 질병도 걸릴 수 있습니다. 생활패턴이나 환경이 바뀌어도 오랜 기간 동안 책임지고 잘 돌보아 줄 수 있나요?</p>
        <p>3. 매일 산책을 시켜주거나 함께 있어줄 수 있는 시간이 충분한가요? 개는 물론이고 고양이도 혼자 있으면 외로워하는 사회적 동물입니다.</p>
        <p>4. 식비, 건강 검진비, 예방접종과 치료비 등 관리비용을 충당할 수 있을 정도의 경제적 여유를 갖고 계신가요?</p>
        <p>5. 동물의 소음(짖거나 울음소리), 냄새(배변 등), 털 빠짐 등의 상황이 일어납니다. 또한 물거나 할퀼 수도 있으며 다양한 문제행동을 보일 수도 있습니다.</p>
        <p>6. 개와 고양이로 인한 알레르기 반응은 없나요? 입양 또는 분양받기 전에 반드시 가족 구성원 모두 알레르기 유무를 확인해야 합니다.</p>
        <p>7. 반려동물의 중성화수술 및 동물등록에 동의하시나요?</p>
        <label>출처 : <a href="https://easylaw.go.kr/CSP/CnpClsMain.laf?popMenu=ov&csmSeq=1294&ccfNo=1&cciNo=2&cnpClsNo=1">반려동물 유의사항</a></label>
      </div>
      <br />
      <button @click="petInfo = !petInfo">반려동물 입양 후 도움되는 정보 - click!</button>
      <div v-if="petInfo">
        <a href="https://www.korea.kr/news/policyNewsView.do?newsId=148900789">클릭 후 참고 해서 도움받으세요</a>
      </div>
    </div>
</template>
  
<script>
export default {
    data() {
      return {
        role: "",
        userId: "",
        accessToken: "",
        petInfo: false,
      }
    },
    mounted() {
    if(!(this.$getAccessToken() === null)) {
      const accessToken = this.$getAccessToken();
      this.accessToken = accessToken;
      this.role = this.$getTokenInfo(accessToken).role;
      this.userId = this.$getTokenInfo(accessToken).userId;
    }
    // if(this.role === "GUEST") {
    //   this.$router.push("/signup");
    // }
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
</style>