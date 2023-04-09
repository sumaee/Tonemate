import { useQuery } from '@tanstack/react-query';

import { axios } from '@/lib/axios';

export const getResultTimbre = ({ timbreId }) => {
  return axios.get(`/music/result/timbre/${timbreId}`);
};

export const useResultTimbre = ({ timbreId, config }) => {
  return useQuery({
    ...config,
    queryKey: ['timbre', timbreId],
    queryFn: () => getResultTimbre({ timbreId }),
  });
};
