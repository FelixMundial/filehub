import { Loading } from "element-ui";
import _ from "lodash";

let loadingInstance;
let requestCount = 0;

const startLoading = (loadingText, target, loadingBackgroundColor) => {
  let color;
  if (loadingBackgroundColor) {
    color = parseLoadingBackgroundColor(loadingBackgroundColor);
  } else {
    color = "rgba(250, 250, 250, 0.7)";
  }
  loadingInstance = Loading.service({
    lock: true,
    text: loadingText,
    background: color,
    target: target || "body"
  });
};

const endLoading = () => {
  // loadingInstance.close();
  mergeLoadingClosure();
};

export const showLoading = (loadingText, target, loadingBackgroundColor) => {
  if (requestCount === 0) {
    startLoading(loadingText, target, loadingBackgroundColor);
  }
  requestCount++;
};

export const hideLoading = () => {
  if (requestCount > 0) {
    requestCount--;
    if (requestCount === 0) {
      endLoading();
    }
  }
};

// 防抖：将300ms内的loading关闭行为合并为一次。防止在连续请求时loading闪烁的问题。
const mergeLoadingClosure = _.debounce(() => {
  if (loadingInstance) {
    loadingInstance.close();
    loadingInstance = null;
  }
}, 300);

export const parseLoadingBackgroundColor = loadingBackgroundColor => {
  if (
    typeof loadingBackgroundColor === "string" &&
    /((25[0-5]|2[0-4][0-9]|[0-1]?[0-9]?[0-9]),\s?(25[0-5]|2[0-4][0-9]|[0-1]?[0-9]?[0-9]),\s?(25[0-5]|2[0-4][0-9]|[0-1]?[0-9]?[0-9],\s?0.\d+))/.test(
      loadingBackgroundColor
    )
  ) {
    return loadingBackgroundColor;
  } else if (typeof loadingBackgroundColor === "object") {
    switch (loadingBackgroundColor.length) {
      case 1:
        return (
          "rgba(" +
          loadingBackgroundColor[0] +
          ", " +
          loadingBackgroundColor[0] +
          ", " +
          loadingBackgroundColor[0] +
          ", 0.7)"
        );
      case 2:
        return (
          "rgba(" +
          loadingBackgroundColor[0] +
          ", " +
          loadingBackgroundColor[1] +
          ", 250, 0.7)"
        );
      case 3:
        return (
          "rgba(" +
          loadingBackgroundColor[0] +
          ", " +
          loadingBackgroundColor[1] +
          ", " +
          loadingBackgroundColor[2] +
          ", 0.7)"
        );
      default:
        return (
          "rgba(" +
          loadingBackgroundColor[0] +
          ", " +
          loadingBackgroundColor[1] +
          ", " +
          loadingBackgroundColor[2] +
          ", " +
          loadingBackgroundColor[3]
        );
    }
  } else if (typeof loadingBackgroundColor === "number") {
    return (
      "rgba(" +
      loadingBackgroundColor +
      ", " +
      loadingBackgroundColor +
      ", " +
      loadingBackgroundColor +
      ", 0.7)"
    );
  }
  return "rgba(250, 250, 250, 0.7)";
};
