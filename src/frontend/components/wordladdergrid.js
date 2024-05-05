function WordLadderGrid({ words }) {
  const maxLength = words.reduce((max, word) => Math.max(max, word.length), 0);
  
  // Array untuk menyimpan status hijau pada setiap kolom
  const greenColumns = Array(maxLength).fill(false);

  return (
    <div className={`grid grid-rows-${words.length} gap-2`}>
      {words.map((word, wordIndex) => (
         <div key={wordIndex} className="flex w-full">
          {word.toUpperCase().split('').map((char, charIndex) => {
            let bgColor = 'bg-gray-200';
            if (greenColumns[charIndex]) {
              // Jika kolom ini sudah hijau, teruskan hijau
              bgColor = 'bg-green-500';
            } else if (wordIndex > 0) {
              // Membandingkan dengan kata sebelumnya
              const previousChar = words[wordIndex - 1][charIndex] || '';
              if (char !== previousChar.toUpperCase()) {
                bgColor = 'bg-green-500';
                greenColumns[charIndex] = true; // Set kolom ini menjadi hijau permanen
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
