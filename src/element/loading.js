/* eslint-disable no-unused-vars */
let loading;
function startLoading() {
  loading = this.$loading({
    lock: true,
    text: "登录中...请稍候...",
    background: "rgba(0, 0, 0, 0.7)"
  });
}
function endLoading() {
  loading.close();
}

export default { startLoading, endLoading };
