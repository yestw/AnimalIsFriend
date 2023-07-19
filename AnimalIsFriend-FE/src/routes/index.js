import Vue from "vue";
import Router from "vue-router";
import HomePage from "@/views/HomePage.vue";
import LoginPage from "@/views/LoginPage.vue";
import SignupPage from "@/views/SignupPage.vue";
import PetRegisterPage from "@/views/PetRegisterPage.vue";
import PetPage from "@/views/PetPage.vue";
import PetDetailPage from "@/views/PetDetailPage.vue";
import MessengerPage from "@/views/MessengerPage.vue";
import ChatRoomPage from "@/views/ChatRoomPage.vue";

Vue.use(Router);

export default new Router({
  mode: "history",
  routes: [
    {
      path: "/",
      name: "home",
      component: HomePage,
    },
    {
      path: "/login",
      name: "login",
      component: LoginPage,
    },
    {
      path: "/signup",
      name: "signup",
      component: SignupPage,
    },
    {
      path: "/register",
      name: "register",
      component: PetRegisterPage,
    },
    {
      path: "/pet",
      name: "pet",
      component: PetPage,
    },
    {
      path: "/pet/:pid",
      name: "petDetail",
      component: PetDetailPage,
      props: true
    },
    {
      path: "/messenger",
      name: "messenger",
      component: MessengerPage,
    },
    {
      path: "/chatroom",
      name: "chatroom",
      component: ChatRoomPage,
    },
  ],
});
