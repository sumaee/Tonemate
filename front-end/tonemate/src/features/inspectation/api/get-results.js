import { axios } from '@/lib/axios';

export const getResults = () => {
  return axios.get('/music/result');
};

export const useResults = ({ config } = {}) => {
  return useQuery({
    ...config,
    queryKey: ['results'],
    queryFn: () => getResults(),
  });
};
