import NavMenu from './nav-menu';
import UserCard from '@/components/card/user-card';
import Logo from '@/components/common/logo-button';
import { useLogout } from '@/features/auth';

export default function NavBarDesk() {
  const logout = useLogout();

  return (
    <>
      <div className="hidden h-screen w-80 flex-col border-r-2 border-gray-600 bg-transparent lg:flex">
        <Logo />
        <div className="mx-2 flex grow flex-col justify-start">
          <div className="flex justify-center">
            <UserCard />
          </div>
          <NavMenu />
        </div>
        <div className="mb-6 flex w-full flex-col items-center justify-center ">
          <div className="flex w-5/6 flex-col rounded-lg bg-gradient-to-r from-pink-500 via-red-500 to-yellow-500 p-0.5">
            <button
              onClick={() => logout()}
              className="flex h-10 w-full flex-col items-center justify-center rounded-lg bg-black"
            >
              <p className="font-nanum text-white lg:text-xl">로그아웃</p>
            </button>
          </div>
        </div>
      </div>
    </>
  );
}
