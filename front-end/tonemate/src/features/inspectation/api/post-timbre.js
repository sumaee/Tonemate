import { axios } from '@/lib/axios';

export const postTimbre = ({ formData }) => {
  return axios.post('/music/timbre', formData);
};

export const usePostTimbre = ({ formData, config }) => {
  return useQuery({
    ...config,
    queryKey: ['postTimbre', formData],
    queryFn: () => postTimbre({ formData }),
  });
};
