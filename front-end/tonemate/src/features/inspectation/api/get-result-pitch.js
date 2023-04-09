import { useQuery } from '@tanstack/react-query';

import { axios } from '@/lib/axios';

export const getResultPitch = ({ pitchId }) => {
  return axios.get(`/music/result/pitch/${pitchId}`);
};

export const useResultPitch = ({ pitchId, config }) => {
  return useQuery({
    ...config,
    queryKey: ['pitch', pitchId],
    queryFn: () => getResultPitch({ pitchId }),
  });
};
