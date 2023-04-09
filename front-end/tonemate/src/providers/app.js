import * as React from 'react';
import { ErrorBoundary } from 'react-error-boundary';
import { QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';

import { ErrorFallback, LoadingFallback } from '@/components/Fallbacks';
import { queryClient } from '@/lib/react-query';

export const AppProvider = ({ children }) => {
  return (
    <React.Suspense fallback={LoadingFallback}>
      <ErrorBoundary FallbackComponent={ErrorFallback}>
        <QueryClientProvider client={queryClient}>
          {children}
          {process.env.NODE_ENV !== 'test' && <ReactQueryDevtools />}
        </QueryClientProvider>
      </ErrorBoundary>
    </React.Suspense>
  );
};
