import { axios } from '@/lib/axios';

export const getLikes = ({ page }) => {
  return axios.get('/likes', { params: { page } });
};
