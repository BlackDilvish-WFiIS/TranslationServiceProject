import "./Layout.css";
import { Layout as LayoutAntd, Image } from "antd";
import { Link } from "react-router-dom";
import TabPane from "../TabPane/TabPane";
import MessagesCard from "../TabPane/Cards/MessagesCard";
import LanguagesCard from "../TabPane/Cards/LanguagesCard";
import TagsCard from "../TabPane/Cards/TagsCard";
import { MESSAGES, LANGUAGES, TAGS } from "../../constants";

const { Header, Footer, Content } = LayoutAntd;

const Layout = () => {
  return (
    <>
      <LayoutAntd id="layout-container">
        <Header id="header">
          <Link to="/">
            <Image
              id="logo-img"
              src={require("./../../assets/logo-img.png")}
              height={60}
              alt="Logo"
              preview={false}
            />
          </Link>
          <span id="app-header">Translation service</span>
        </Header>
        <Content id="content">
          <TabPane>
            <MessagesCard cardTitle={MESSAGES} id={1} />
            <LanguagesCard cardTitle={LANGUAGES} id={2} />
            <TagsCard cardTitle={TAGS} id={3} />
          </TabPane>
        </Content>
        <Footer id="footer">@ 2022 CISCO University Course Project</Footer>
      </LayoutAntd>
    </>
  );
};
export default Layout;
