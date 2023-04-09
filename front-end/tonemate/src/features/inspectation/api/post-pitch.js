import { axios } from '@/lib/axios';

export const postPitch = ({ formData }) => {
  return axios.post('/music/pitch', formData);
};

export const usePostPitch = ({ formData, config }) => {
  return useQuery({
    ...config,
    queryKey: ['postPitch', formData],
    queryFn: () => postPitch({ formData }),
  });
};
