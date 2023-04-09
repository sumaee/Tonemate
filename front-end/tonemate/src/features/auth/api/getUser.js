import { axios } from '@/lib/axios';

export const getUser = () => {
  return axios.get('/users').catch((err) => {
    return null;
  });
};
