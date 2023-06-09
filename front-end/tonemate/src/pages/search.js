import Head from 'next/head';

import Layout from '@/components/layout';
import Table from '@/components/table/table';
import TitleContainer from '@/components/content/title-container';
import MainContainer from '@/components/content/main-container';
import SearchComponent from '@/components/main/search-component';

export default function Search() {
  return (
    <>
      <Head>
        <title>TONEMATE - 노래 검색</title>
        <meta name="description" content="Generated by create next app" />
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="icon" href="/favicon.ico" />
      </Head>
      <main>
        <Layout>
          <TitleContainer>
            <p className="text-xl text-white lg:text-4xl">노래 검색</p>
          </TitleContainer>
          <MainContainer>
            {/* <SearchComponent /> */}
            <Table />
          </MainContainer>
        </Layout>
      </main>
    </>
  );
}
