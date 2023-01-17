import { Tag as AntdTag } from "antd";
import hexRgb from "hex-rgb";
import rgbHex from "rgb-hex";

const stc = require("string-to-color");

const Tag = ({ text, index }) => {
  let c = hexRgb(stc(text));
  c.red = (c.red * 12) % 255;
  c.alpha = 0.15;
  var color = "#" + rgbHex(c.red, c.green, c.blue, c.alpha);

  return (
    <AntdTag
      style={{
        fontWeight: "700",
        backgroundColor: `${color}`,
        borderColor: `${color}`,
        margin: "5px",
      }}
      key={index}
    >
      {text}
    </AntdTag>
  );
};

export default Tag;
