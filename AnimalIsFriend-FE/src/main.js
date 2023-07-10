import Vue from 'vue'
import App from './App.vue'
import router from "./routes";
import VueCookies from "vue-cookies";
import jwtDecode from "jwt-decode";

Vue.config.productionTip = false
Vue.use(VueCookies);
Vue.$cookies.config("7d");

Vue.prototype.$getTokenInfo = function (accessToken) {
  if (!(accessToken == null)) {
    const { sub, role } = jwtDecode(accessToken);
    const tokenInfo = {
      userId: sub,
      role: role,
    };
    return tokenInfo;
  }
};

Vue.prototype.$getAccessToken = function () {
  const accessToken = this.$cookies.get("accessTokenCookie");
  return accessToken;
};

new Vue({
  render: h => h(App),
  router,
}).$mount('#app')
