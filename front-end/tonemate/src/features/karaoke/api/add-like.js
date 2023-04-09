import { axios } from '@/lib/axios';

export const addLike = ({ tjNum }) => {
  return axios.post(`/likes/${tjNum}`);
};
