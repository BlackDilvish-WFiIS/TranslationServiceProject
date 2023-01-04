import React from "react";
import { Nav, NavLink, Bars, NavMenu } from "./NavbarElement/NavbarElement";

const Navbar = () => {
  return (
    <>
      <Nav>
        <NavLink to="/">
          <img className="text-white py-3 px-5" alt="agh logo" />
        </NavLink>
        <Bars />
        <NavMenu>
          <NavLink to="/list">Lista schronisk</NavLink>
          <NavLink to="/pet_list">Lista Zwierzaków</NavLink>
          <NavLink to="/contact">Kontakt</NavLink>
        </NavMenu>
      </Nav>
    </>
  );
};

export default Navbar;
