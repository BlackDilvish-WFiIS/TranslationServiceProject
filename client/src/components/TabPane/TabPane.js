import React from "react";
import { Tabs } from "antd";

const TabPane = ({ children }) => {
  return (
    <>
      <Tabs
        size="large"
        items={children.map((child, key) => {
          return {
            label: child.props.cardTitle,
            key: key,
            children: child,
          };
        })}
      />
    </>
  );
};
export default TabPane;
