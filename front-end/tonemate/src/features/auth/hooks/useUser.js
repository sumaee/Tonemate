import { useEffect } from 'react';
import { useQuery } from '@tanstack/react-query';
import { useRouter } from 'next/router';

import { getUser } from '@/features/auth';

export const useUser = ({ redirectTo = '', redirectIfFound = false } = {}) => {
  const router = useRouter();

  const userQuery = useQuery({
    queryKey: ['user'],
    queryFn: () => getUser(),
  });

  useEffect(() => {
    if (!redirectTo || userQuery.isLoading) return;

    if (
      (redirectTo && !redirectIfFound && !userQuery.data) ||
      (redirectIfFound && userQuery.data)
    ) {
      router.push(redirectTo);
    }
  }, [redirectIfFound, redirectTo, router, userQuery.isLoading, userQuery.data]);

  return { user: userQuery.data, isUserLoading: userQuery.isLoading };
};
