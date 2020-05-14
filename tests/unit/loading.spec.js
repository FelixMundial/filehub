import { parseLoadingBackgroundColor } from "../../src/util/loading";

test("测试parseLoadingBackgroundColor(color)", () => {
  let colorParam = "rgba(250, 250, 250, 0.5)";
  expect(parseLoadingBackgroundColor(colorParam)).toBe(colorParam);
});
