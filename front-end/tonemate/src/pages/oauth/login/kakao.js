import { useRouter } from 'next/router';
import { useEffect } from 'react';

import { axios } from '@/lib/axios';

export default function LoginKakao() {
  const router = useRouter();

  useEffect(() => {
    if (!router.isReady) return;
    const AUTHORIZE_CODE = router.query.code;

    axios.get(`/oauth/login/kakao?code=${AUTHORIZE_CODE}&state=kakao&prompt=none`).then(() => {
      router.push('/home');
    });
  }, [router]);
}
