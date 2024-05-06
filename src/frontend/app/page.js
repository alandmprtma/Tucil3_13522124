"use client"
import React, { useState } from 'react';
import StickyBar from '../components/stickybar';
import WordLadderGrid from '../components/wordladdergrid';
import { BeatLoader } from "react-spinners";

export default function Home() {
  //Fungsi untuk membuat delay pada website
  function delay(ms) {
    return new Promise(resolve => setTimeout(resolve, ms));
  }

  // State untuk menyimpan panjang kata, kata awal, dan kata akhir
  const [length, setLength] = useState('');
  const [startWord, setStartWord] = useState('');
  const [endWord, setEndWord] = useState('');
  const [activeAlgorithm, setActiveAlgorithm] = useState('');
  const [results, setResults] = useState(null);
  const [errorMessage, setErrorMessage] = useState(null);
  const [submitted, setSubmitted] = useState(false);
  const [loading, setLoading] = useState(false);

  const baseStyle = "mx-4 border-black rounded px-7 pb-[8px] pt-[10px] font-black text-sm uppercase leading-normal transition duration-150 ease-in-out focus:outline-none focus:ring-0";

  const dynamicStyle = (isActive) => 
    `${baseStyle} ${isActive ? 'text-black border-neutral-100 bg-neutral-500 bg-opacity-50' : 'text-black border-black border-2 hover:border-neutral-100 hover:bg-neutral-500 hover:bg-opacity-10 hover:text-neutral-40'}`;
  
  const handleAlgorithmClick = (algorithm) => {
    console.log("Algorithm", algorithm);
    setActiveAlgorithm(algorithm);
  };

  const handleSubmit = async (event) => {
    setLoading(true);
    setSubmitted(false);
    event.preventDefault();
    if (startWord =='' || endWord == ''){
      setErrorMessage("Please complete the start and the end word.");
      await delay(1500);
      setLoading(false)
      setErrorMessage(null);
      return;
    }
    else if (activeAlgorithm == ''){
      setErrorMessage("Please choose the algorithm.");
      await delay(1500);
      setLoading(false)
      setErrorMessage(null);
      return;
    }
    else if (startWord.length < length || endWord.length < length){
      setErrorMessage("Start Word or End Word not suitable with the length input.");
      await delay(1500);
      setLoading(false)
      setErrorMessage(null);
      return;
    }
    else if (startWord == endWord){
      setErrorMessage("Start Word and End Word must a different value.");
      await delay(1500);
      setLoading(false)
      setErrorMessage(null);
      return;
    }
    try {
      const response = await fetch(`http://localhost:8080/api?start=${startWord}&target=${endWord}&algorithm=${activeAlgorithm}`, {
        method: 'GET', // Metode HTTP yang digunakan
        headers: {
          'Content-Type': 'application/json'
        },
      });
      if (!response.ok) {
        throw new Error(`HTTP error! Status: ${response.status}`);
      }
  
      const data = await response.json(); // Mengonversi response menjadi JSON
      if(data.message != null){
        setErrorMessage(data.message);
        await delay(1500);
        setLoading(false);
        setErrorMessage(null);
      }
      else{
        console.log(data);
        setResults(data);
      }
    } catch (error) {
      console.error("Error:", error);
      setErrorMessage(error.message || "An unexpected error occurred");
    } finally {
      setLoading(false);
      setSubmitted(true);
    }
  }

  return (
  <html>
    <body>
    <div className="flex flex-col w-full items-center bg-white">
      <StickyBar />
      <div className="mt-5 ">
        <label htmlFor="lengthInput" className="flex text-sm font-medium text-black justify-center">
          Panjang Kata:
        </label>
        <input
          type="number"
          id="lengthInput"
          value={length}
          onChange={(e) => {
            setLength(e.target.value);
            setStartWord('');
            setEndWord('');
            setActiveAlgorithm('');
          }}
          className="mt-1 block w-full px-3 py-2 border border-gray-300 text-gray-700 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
        />
      </div>
      {length > 0 && (
        <div>
          <form onSubmit={handleSubmit}>
          <div className="mt-5">
            <label htmlFor="startWordInput" className="flex text-sm font-medium text-black justify-center">
              Start Word:
            </label>
            <input
              type="text"
              id="startWordInput"
              value={startWord}
              onChange={(e) => {
                const { value } = e.target; // Mengambil nilai dari input
                setStartWord(value); // Mengatur nilai startWord dengan nilai dari input
                setSubmitted(false); // Menetapkan submitted menjadi false
            }}
              maxLength={length}
              className="mt-1 block w-full px-3 py-2 border border-gray-300 text-gray-700 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
          </div>
          <div className="mt-5">
            <label htmlFor="endWordInput" className="flex text-sm font-medium text-black justify-center">
              End Word:
            </label>
            <input
              type="text"
              id="endWordInput"
              value={endWord}
              onChange={(e) => {
                const { value } = e.target;
                setEndWord(value); // Mengatur nilai endWord dengan nilai dari input
                setSubmitted(false);
            }}
              maxLength={length}
              className="mt-1 block w-full px-3 py-2 border text-gray-700 border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-indigo-500 focus:border-indigo-500 sm:text-sm"
            />
          <h4 className="mb-2 text-xl font-semibold">Algorithm Type</h4>
          <div>
            <div className='flex justify-center'>
            <button
              type="button"
              className={dynamicStyle(activeAlgorithm === 'UCS')}
              onClick={() => handleAlgorithmClick('UCS')}
              data-twe-ripple-init
              data-twe-ripple-color="light"
            >
              UCS
            </button>
            <button
              type="button"
              className={dynamicStyle(activeAlgorithm === 'ASTAR')}
              onClick={() => handleAlgorithmClick('ASTAR')}
              data-twe-ripple-init
              data-twe-ripple-color="light"
            >
              A*
            </button>
            <button
              type="button"
              className={dynamicStyle(activeAlgorithm === 'GBFS')}
              onClick={() => handleAlgorithmClick('GBFS')}
              data-twe-ripple-init
              data-twe-ripple-color="light"
            >
              Greedy Best First Search
            </button>
            </div>
            {errorMessage && (
              <div className='text-black text-center mt-4 mb-4'>{errorMessage}</div>
            )}
             {loading && (
                <div className="flex justify-center items-center mt-[25px] mb-[50px]">
                  <BeatLoader color="#000000" loading={loading} size={15} />
                  <p className="ml-2 text-black">Loading...</p>
                </div>
              )}
            {!loading && (<div className='flex justify-center'><button type="submit"
            className='border-black text-black mt-4 mx-4 mb-[15px] rounded border-2 px-7 pb-[8px] pt-[10px] text-sm font-bold uppercase leading-normal  transition duration-150 ease-in-out hover:border-neutral-100 hover:bg-neutral-500 hover:bg-opacity-10 hover:text-black focus:border-neutral-100 focus:text-neutral-100 focus:outline-none focus:ring-0 active:border-neutral-200 active:text-neutral-200 dark:hover:bg-neutral-100 dark:hover:bg-opacity-10'
            data-twe-ripple-init
            data-twe-ripple-color="light">
              Submit!
          </button></div>
          )}
          {results && results.data.length !== 0 && submitted && (
              <div className="p-8 flex flex-col items-center">
                  <WordLadderGrid words={results.data} />
                  <p className="text-center mt-4 text-xl text-black">Found Word Ladder Solution from <strong>{startWord}</strong> to <strong>{endWord}</strong> in <strong>{results.executionTime} milliseconds</strong>!</p>
                  <p className="text-black text-center mt-4 text-xl">Solution Length: <strong>{results.data.length}</strong></p>
                  <p className="text-black text-center mt-4 text-xl">Nodes Traversed: <strong>{results.visitedNode}</strong></p>
                  <p className="text-black text-center mt-4 text-xl">Memory Used: <strong>{results.executionMemory} KB</strong></p>
              </div>
          )}
          {results && results.data.length == 0 && submitted && (
             <div className="p-8 flex flex-col items-center">
             <WordLadderGrid words={results.data} />
             <p className="text-center mt-4 text-xl text-black">No Word Ladder Solution found from <strong>{startWord}</strong> to <strong>{endWord}</strong> in <strong>{results.executionTime} milliseconds</strong>!</p>
             <p className="text-black text-center mt-4 text-xl">Nodes Traversed: <strong>{results.visitedNode}</strong></p>
             <p className="text-black text-center mt-4 text-xl">Memory Used: <strong>{results.executionMemory} KB</strong></p>
           </div>
          )}
          </div>
         </div>
         </form>
        </div>
      )}
    </div>
    </body>
    </html>
  );
}
