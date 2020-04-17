import Vue from "vue";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import Axios from "axios";
import ElementUI from "element-ui";
import "element-ui/lib/theme-chalk/index.css";

// Axios.defaults.baseURL = "http://localhost:18080";
Vue.prototype.axios = Axios.create({
  // baseURL: "http://localhost:18080",
  baseURL: "http://localhost:18010",
  timeout: 15000
});

Vue.use(ElementUI);

Vue.config.productionTip = false;

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount("#app");
