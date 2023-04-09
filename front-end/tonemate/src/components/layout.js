import NavBarDesk from './navigation/nav-bar-desk';
import NavBarMobile from './navigation/nav-bar-mobile';
import Content from './content/content';

const Layout = ({ children }) => {
  return (
    <>
      <div className="flex h-screen w-screen flex-col bg-black lg:flex-row">
        <div id="stars" className="h-screen w-screen overflow-hidden"></div>
        <div id="stars2" className="h-screen w-screen overflow-hidden"></div>
        <div id="stars3" className="h-screen w-screen overflow-hidden"></div>
        <NavBarMobile />
        <NavBarDesk />
        <Content>{children}</Content>
      </div>
    </>
  );
};

export default Layout;
