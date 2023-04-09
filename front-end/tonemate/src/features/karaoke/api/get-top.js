import { axios } from '@/lib/axios';

export const getTop = ({ page }) => {
  return axios.get('/karaoke/top', { params: { page } });
};
