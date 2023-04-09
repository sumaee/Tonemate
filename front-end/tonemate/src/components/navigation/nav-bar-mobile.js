import { useState } from 'react';
import { Bars3Icon } from '@heroicons/react/24/solid';

import NavMenuMobile from './nav-menu-mobile';
import Logo from '@/components/common/logo-button';

export default function NavBarMobile() {
  const [menu, setMenu] = useState(false);

  const toggleMenu = () => {
    setMenu((menu) => !menu);
  };

  return (
    <>
      <div className="flex h-14 items-center justify-between bg-transparent md:h-14 lg:hidden">
        <div className="ml-4 flex items-center justify-center md:ml-6">
          <Logo />
        </div>
        <div className="mr-4 flex items-center justify-center md:mr-6">
          <Bars3Icon className="h-7 w-7 text-white md:h-8 md:w-8" onClick={toggleMenu} />
        </div>
      </div>
      {menu ? (
        <div
          className="absolute left-0 top-0 z-10 flex h-screen w-screen bg-slate-400 bg-opacity-60"
          onClick={toggleMenu}
        >
          <NavMenuMobile />
        </div>
      ) : (
        ''
      )}
    </>
  );
}
