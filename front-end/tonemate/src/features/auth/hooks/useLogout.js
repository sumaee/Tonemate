import { useRouter } from 'next/router';

import { logout } from '../api/logout';

export const useLogout = () => {
  const router = useRouter();
  return () => {
    logout().then(() => {
      router.push('/');
    });
  };
};
