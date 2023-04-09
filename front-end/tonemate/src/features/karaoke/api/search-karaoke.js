import { axios } from '@/lib/axios';

export const searchKaraoke = ({ page, singer, title, tjNum }) => {
  return axios.get('/karaoke/search', { params: { page, singer, title, tjNum } });
};
