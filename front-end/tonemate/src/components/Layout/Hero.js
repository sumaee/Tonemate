export const HeroWrapper = ({ children }) => {
  return <div className="hero min-h-screen bg-base-200">{children}</div>;
};

export const HeroContent = ({ children }) => {
  return (
    <div className="hero-content text-center">
      <div className="max-w-md">{children}</div>
    </div>
  );
};
