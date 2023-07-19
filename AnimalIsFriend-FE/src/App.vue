<template>
  <div id="app">
    <MainHeader></MainHeader>
    <main id="wrap">
      <router-view />
    </main>
    <MainFooter></MainFooter>
  </div>
</template>

<script>
import MainHeader from "@/components/MainHeader.vue";
import MainFooter from "@/components/MainFooter.vue";

export default {
  name: 'App',
  components: {
    MainHeader: MainHeader,
    MainFooter: MainFooter,
  },
  data: () => ({}),
}
</script>

<style>
@font-face {
  font-family: 'EF_watermelonSalad';
  src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_2210-EF@1.0/EF_watermelonSalad.woff2') format('woff2');
  font-weight: normal;
  font-style: normal;
}
#app {
  font-family: 'EF_watermelonSalad', cursive;
}

#wrap {
  background-color: rgb(223, 209, 209);
  height: auto;
  min-height: 100%;
  padding-bottom: 60px;
  min-height: 600px;
  display: flex;
  align-items: center;
  justify-content: center;
}
</style>
