import { useState } from 'react';
import { useQuery } from '@tanstack/react-query';
import {
  HeartIcon as HeartSolidIcon,
  ChevronLeftIcon,
  ChevronRightIcon,
} from '@heroicons/react/24/solid';
import { HeartIcon as HeartOutlineIcon } from '@heroicons/react/24/outline';
import { getLikes, addLike, deleteLike } from '@/features/karaoke';

function TableLike() {
  const [page, setPage] = useState(1);

  const { isLoading, isError, error, data, isFetching, isPreviousData } = useQuery({
    queryKey: ['likes', page],
    queryFn: () => getLikes({ page }),
    keepPreviousData: true,
  });

  function clickHeart(isLike, tjNum) {
    if (isLike) {
      deleteLike({ tjNum });
    } else {
      addLike({ tjNum });
    }
  }

  return (
    <>
      {isLoading ? (
        <div>Loading...</div>
      ) : isError ? (
        <div>Error: {error.message}</div>
      ) : (
        <div className="flex h-full w-full flex-col items-center justify-start">
          {/* Table Head : 테이블 태그로 작성시에 사이즈 조절에 문제가 생겨서 div태그로 커스터마이징 */}
          <div className="fade-in-custom-15s mb-3 flex h-10 w-full flex-row justify-between border-y border-yellow-400">
            <div className="mx-2 flex w-4/12 flex-row items-center bg-black">
              <p className="font-nanum text-sm text-white lg:text-lg">
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;제목
              </p>
            </div>
            <div className="flex w-4/12 flex-row items-center justify-center bg-black">
              <p className="font-nanum text-sm text-white lg:text-lg">가수</p>
            </div>
            <div className="flex w-1/12 flex-row items-center justify-center bg-black">
              <p className="font-nanum text-sm text-white lg:text-lg">TJ</p>
            </div>
            <div className="mx-2 flex w-1/12 flex-row items-center justify-center bg-black">
              <p className="font-nanum text-sm text-white lg:text-lg">담기</p>
            </div>
          </div>
          {/* Table Body : 테이블 태그로 작성시에 사이즈 조절에 문제가 생겨 div태그로 커스터마이징 */}
          <div className="fade-in-custom-15s mb-2 flex w-full flex-col">
            {data?.songs?.map((song) => (
              <div
                key={song?.tjNum}
                className="mb-1 flex h-14 w-full flex-row justify-between rounded-full bg-white/50"
              >
                <div className="mx-2 flex w-4/12 flex-row items-center ">
                  <p className="font-nanum text-sm text-white lg:text-lg">
                    &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;{song?.title}
                  </p>
                </div>
                <div className="flex w-4/12 flex-row items-center justify-center ">
                  <p className="font-nanum text-sm text-white lg:text-lg">{song?.singer}</p>
                </div>
                <div className="flex w-1/12 flex-row items-center justify-center ">
                  <p className="font-nanum text-sm text-white lg:text-lg">{song?.tjNum}</p>
                </div>
                <div className="mx-2 flex w-1/12 flex-row items-center justify-center ">
                  <button>
                    <HeartSolidIcon className="h-4 w-4 text-white lg:h-5 lg:w-5" />
                  </button>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}
      <div className="fade-in-custom-20s mb-2 flex h-10 w-2/5 flex-row items-center justify-between lg:w-1/5">
        <button onClick={() => setPage((old) => Math.max(old - 1, 1))} disabled={page === 1}>
          <ChevronLeftIcon className="h-6 w-6 text-white" />
        </button>
        <span className="font-nanum text-sm text-white lg:text-lg">{page}</span>
        <button
          onClick={() => {
            if (page !== data?.totalPageNumber) {
              setPage((old) => old + 1);
            }
          }}
          disabled={page === data?.totalPageNumber}
        >
          <ChevronRightIcon className="h-6 w-6 text-white" />
        </button>
      </div>
    </>
  );
}

export default TableLike;
