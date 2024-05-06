function WordLadderGrid({ words }) {

  return (
    <div className={`grid grid-rows-${words.length} gap-2`}>
      {words.map((word, wordIndex) => (
         <div key={wordIndex} className="flex w-full">
          {word.toUpperCase().split('').map((char, charIndex) => {
            let bgColor = 'bg-gray-200';
            if (wordIndex > 0) {
              // Membandingkan dengan kata sebelumnya
              const endChar = words[words.length - 1][charIndex] || '';
              if (char == endChar.toUpperCase()) {
                bgColor = 'bg-green-500';
              }
            }

            return (
              <div key={charIndex} className={`w-16 h-16 flex justify-center items-center border border-gray-300 ${bgColor} text-xl text-black font-bold mx-2 my-2`}>
                {char}
              </div>
            );
          })}
        </div>
      ))}
    </div>
  );
}

export default WordLadderGrid;
