import { axios } from '@/lib/axios';

export const logout = () => {
  return axios.post('/logout');
};
