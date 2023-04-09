import NavButton from '../common/nav-button';

import {
  BeakerIcon,
  HomeIcon,
  ChartBarIcon,
  MusicalNoteIcon,
  SwatchIcon,
  HeartIcon,
} from '@heroicons/react/24/solid';

export default function NavMenu() {
  return (
    <>
      <div className="flex w-64 flex-col items-start justify-start">
        {/* 홈 Navigation Botton */}
        <NavButton icon={HomeIcon} name="홈 화면" value="/home" />

        {/* 음색 검사 Navigation Botton */}
        <NavButton icon={SwatchIcon} name="음색 검사" value="/inspectation/vocal-color" />

        {/* 음역대 검사 Navigation Botton */}
        <NavButton icon={MusicalNoteIcon} name="음역대 검사" value="/inspectation/vocal-range" />

        {/* 검사 결과 Navigation Botton */}
        <NavButton icon={ChartBarIcon} name="검사 결과" value="/inspectation/result-list" />

        {/* 노래 검색 Navigation Botton */}
        <NavButton icon={MusicalNoteIcon} name="노래 검색" value="/search" />

        {/* 애창곡리스트 검색 Navigation Botton */}
        <NavButton icon={HeartIcon} name="애창곡 리스트" value="/user/music-list" />

        {/* 서비스 소개 Navigation Botton */}
        <NavButton icon={BeakerIcon} name="서비스 소개" value="/about" />
      </div>
    </>
  );
}
