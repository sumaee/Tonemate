import { Spinner } from '@/components/Elements';

export const LoadingFallback = () => {
  return (
    <div className="flex h-screen w-screen items-center justify-center">
      <Spinner size="xl" />
    </div>
  );
};
