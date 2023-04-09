import { axios } from '@/lib/axios';

export const deleteLike = ({ tjNum }) => {
  return axios.delete(`/likes/${tjNum}`);
};
