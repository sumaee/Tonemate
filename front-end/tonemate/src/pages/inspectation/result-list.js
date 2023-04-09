import Head from 'next/head';
import { useRouter } from 'next/router';
import { useQuery } from '@tanstack/react-query';

import { useUser } from '@/features/auth';
import Layout from '@/components/layout';
import TitleContainer from '@/components/content/title-container';
import MainContainer from '@/components/content/main-container';
import { LoadingFallback } from '@/components/Fallbacks';
// import { useResults } from '@/features/inspectation';
import { axios } from '@/lib/axios';

export default function ResultList() {
  const router = useRouter();

  const getResults = () => {
    return axios.get('/music/result');
  };

  const useResults = ({ config } = {}) => {
    return useQuery({
      ...config,
      queryKey: ['results'],
      queryFn: () => getResults(),
    });
  };

  const resultsQuery = useResults();

  const { user, isUserLoading } = useUser({ redirectTo: '/', redirectIfFound: false });
  if (isUserLoading || !user || resultsQuery.isLoading) {
    return <LoadingFallback />;
  }

  const result = resultsQuery.data;
  console.log(result);

  return (
    <>
      <Head>
        <title>검사결과</title>
        <meta name="description" content="Generated by create next app" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <main>
        <Layout>
          <TitleContainer>
            <p className="text-xl text-white lg:text-4xl">검사 결과</p>
          </TitleContainer>
          <MainContainer>
            <div className="fade-in-custom-10s flex h-12 w-full flex-col items-center justify-center lg:items-start">
              <p className="text-md text-white lg:text-2xl">
                {user.nickname?.slice(-6)}은 총 {result?.timbre?.length + result?.pitch?.length}건의
                검사 결과를 가지고 있습니다.
              </p>
            </div>
            <div className="fade-in-custom-15s flex h-2/5 w-full flex-col">
              <div className="flex h-10 w-full flex-col items-center justify-center lg:items-start">
                <p className="text-sm text-white lg:text-xl">
                  음색 검사 결과 : {result?.timbre?.length} 건
                </p>
              </div>
              <div className="flex w-full grow snap-x flex-row flex-nowrap items-center justify-start overflow-x-auto scrollbar-hide ">
                {result?.timbre?.map((item) => (
                  <div
                    key={item.timbreId}
                    className="mx-3 h-5/6 w-4/5 flex-shrink-0 grow-0 basis-auto snap-center rounded-xl bg-gradient-to-r from-pink-500 via-red-500 to-yellow-500 p-1 lg:w-1/4"
                  >
                    <button
                      className="h-full w-full rounded-xl bg-black"
                      onClick={() =>
                        router.push('/inspectation/vocal-color-result/' + item.timbreId)
                      }
                    >
                      <p className="m-2 text-xl text-white lg:text-2xl">음색검사</p>
                      <p className="m-2 text-xl text-white lg:text-2xl">
                        {item.time?.slice(5, 10)}
                      </p>
                      <div className="m-3 flex flex-row justify-around">
                        {item.singer.map((item) => (
                          <p key={item} className="text-sm text-white lg:text-xl">
                            {item}
                          </p>
                        ))}
                      </div>
                    </button>
                  </div>
                ))}
              </div>
            </div>
            <div className="fade-in-custom-20s flex h-2/5 w-full flex-col ">
              <div className="flex h-10 w-full flex-col items-center justify-center lg:items-start ">
                <p className="text-sm text-white lg:text-xl">
                  음역대 검사 결과 : {result?.pitch?.length} 건
                </p>
              </div>
              <div className="flex w-full grow snap-x flex-row flex-nowrap items-center justify-start overflow-x-auto scrollbar-hide ">
                {result?.pitch?.map((item) => (
                  <div
                    key={item.pitchId}
                    className="mx-3 h-5/6 w-4/5 flex-shrink-0 grow-0 basis-auto snap-center rounded-xl bg-gradient-to-r from-pink-500 via-red-500 to-yellow-500 p-1 lg:w-1/4"
                  >
                    <button
                      className="h-full w-full rounded-xl bg-black"
                      onClick={() =>
                        router.push('/inspectation/vocal-range-result/' + item.pitchId)
                      }
                    >
                      <p className="m-2 text-xl text-white lg:text-2xl">음역대 검사</p>
                      <p className="m-2 text-xl text-white lg:text-2xl">
                        {item.time?.slice(5, 10)}
                      </p>
                      <p className="text-mg m-2 text-white">최고 음역대 : {item.highOctave}</p>
                      <p className="text-mg m-2 text-white">최저 음역대 : {item.lowOctave}</p>
                    </button>
                  </div>
                ))}
              </div>
            </div>
          </MainContainer>
        </Layout>
      </main>
    </>
  );
}