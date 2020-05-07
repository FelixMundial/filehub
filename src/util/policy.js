import axios from "./axios";

export function getPolicy() {
  return new Promise(resolve => {
    axios
      .get("/file/v1/oss/getPolicy", {
        headers: {
          // noLoading: true,
          loadingText: "上传中，请稍候...",
          Authorization: sessionStorage.token ? sessionStorage.token : ""
        }
      })
      .then(data => {
        resolve(data);
      });
  });
}
