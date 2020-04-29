/* eslint-disable no-unused-vars */
function startLoading() {
  return this.$loading({
    lock: true,
    text: "登录中...请稍候...",
    background: "rgba(0, 0, 0, 0.7)"
  });
}
//无法关闭loading对象：_element_loading__WEBPACK_IMPORTED_MODULE_5__.default.close is not a function
function endLoading(loadingInstance) {
  loadingInstance.close();
}

export default { startLoading, endLoading };
