// import Vue from "vue";
// import Axios from "axios";
// import router from "./router";

// // Axios.defaults.baseURL = "http://localhost:7000";
// const axios = Axios.create({
//   baseURL: "http://localhost:7000",
//   withCredentials: true,
//   timeout: 15000
// });

// //请求拦截器
// axios.interceptors.request.use(
//   config => {
//     if (localStorage.token) {
//       config.headers["Authorization"] = sessionStorage.token;
//       config.headers["Accept"] = "application/json";
//     }
//     return config;
//   },
//   error => {
//     return Promise.reject(error);
//   }
// );

// // 响应拦截器
// axios.interceptors.response.use(
//   response => {
//     // 判断一下响应中是否有token，如果有就直接使用此token替换掉本地的token
//     let token = response.data.token;
//     // let token = response.headers.authorization;

//     if (token) {
//       // 如果 header 中存在 token，那么触发 refreshToken 方法，替换本地的 token
//       axios.defaults.headers.common["Authorization"] = token;
//     }
//     return response;
//   },
//   error => {
//     if (error.response) {
//       switch (error.response.status) {
//         case 401:
//           // 清除token
//           router.replace({
//             path: "login",
//             query: { redirect: router.currentRoute.fullPath } // 登录成功后跳转浏览的当前页面
//           });
//       }
//     }
//     return Promise.reject(error);
//   }
// );

// export default axios;
