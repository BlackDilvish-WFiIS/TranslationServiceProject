import React from "react";
import { Tabs } from "antd";
import { MESSAGES, LANGUAGES, TAGS } from "../../constants";

const CardPane = () => {
  const tabs = [
    {
      label: MESSAGES,
      key: "1",
      children: <p>Content of Tab Pane 1 TODO</p>,
    },
    {
      label: LANGUAGES,
      key: "2",
      children: <p>Content of Tab Pane 2 TODO</p>,
    },
    {
      label: TAGS,
      key: "3",
      children: <p>Content of Tab Pane 3 TODO</p>,
    },
  ];

  return (
    <>
      <Tabs
        defaultActiveKey="1"
        // type="card"
        // centered={true}
        size="large"
        items={tabs.map((tab) => {
          return {
            label: tab.label,
            key: tab.key,
            children: tab.children,
          };
        })}
      />
    </>
  );
};
export default CardPane;
