import '../styles/globals.css';
import { AppProvider } from '@/providers/app';

export default function App({ Component, pageProps }) {
  return (
    <AppProvider>
      <Component {...pageProps} />
    </AppProvider>
  );
}
