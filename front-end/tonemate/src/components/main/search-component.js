import { useState } from 'react';
import { MagnifyingGlassIcon } from '@heroicons/react/24/solid';

function SearchComponent() {
  const [category, setCategory] = useState('가수');
  const [keyword, setKeyword] = useState('검색어를 입력해주세요.');

  function handleInput() {
    console.log('tlqkf');
  }

  const handleChangeInput = (e) => {
    setKeyword(e.target.value);
  };

  const handleChangeSelect = (e) => {
    setCategory(e.target.value);
  };

  function clickSearchButton() {
    console.log(category);
    console.log(keyword);
    // 내부 내용 바꾸세요
  }

  function clickTJButton() {
    console.log('TJ TOP100');
  }

  function clickKYButton() {
    console.log('ky TOP100');
  }

  // css 먹여야함
  return (
    <>
      <div className="h-18 fade-in-custom-10s mb-5 flex w-full flex-col items-center justify-between lg:h-12 lg:flex-row lg:items-start">
        <div className="flex h-10 w-full flex-row items-center justify-around rounded-full border-2 border-red-600 bg-white lg:w-5/12 ">
          <div className="flex h-1/2 items-center">
            <select className="flex shrink-0 " onChange={handleChangeSelect}>
              <option value="제목">제목</option>
              <option value="가수">가수</option>
            </select>
          </div>
          <div className="flex h-12 w-9/12 items-center">
            <input
              className="h-2/3 w-full"
              placeholder={keyword}
              onChange={handleChangeInput}
            ></input>
          </div>
          <button onClick={clickSearchButton} className="flex  h-12 items-center justify-center">
            <MagnifyingGlassIcon className="h-7 w-7 bg-transparent text-black" />
          </button>
        </div>
        <div className="flex h-10 w-3/4 flex-row items-end justify-around lg:w-1/4 lg:justify-end">
          <button
            onClick={clickTJButton}
            className="mx-3 block rounded-full border border-white px-3"
          >
            <p className="mx-2 font-alatsi text-white">TJ TOP100</p>
          </button>
          <button onClick={clickKYButton} className="block rounded-full border border-white">
            <p className="mx-3 font-alatsi text-white">KY TOP100</p>
          </button>
        </div>
      </div>
    </>
  );
}

export default SearchComponent;
