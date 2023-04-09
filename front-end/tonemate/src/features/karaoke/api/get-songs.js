import { axios } from '@/lib/axios';

export const getSongs = ({ page }) => {
  return axios.get('/karaoke/songs', { params: { page } });
};
