import NavMenu from './nav-menu';
import UserCard from '@/components/card/user-card';

export default function NavMenuMobile() {
  return (
    <>
      <div className="fade-in-custom-10s absolute bottom-0 z-10 flex w-full flex-col items-center justify-center rounded-2xl bg-black lg:hidden">
        <UserCard />
        <NavMenu />
      </div>
    </>
  );
}
