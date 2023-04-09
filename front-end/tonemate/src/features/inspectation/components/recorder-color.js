import { useRef, useState } from 'react';
import { useRouter } from 'next/router';
import RecordRTC from 'recordrtc';

import { postTimbre } from '@/features/inspectation';

export const RecorderColor = () => {
  const router = useRouter();

  const [isRecording, setIsRecording] = useState(false);
  const [isFinish, setIsFinish] = useState(false);
  const [isDrawing, setIsDrawing] = useState(true);
  const [drawID, setDrawID] = useState(null);
  const [recordedBlob, setRecordedBlob] = useState(null);
  const mediaStreamRef = useRef(null);
  const canvasRef = useRef(null);
  const recorderRef = useRef(null);

  const startRecording = async () => {
    navigator.mediaDevices
      .getUserMedia({ audio: true })
      .then((stream) => {
        const audioCtx = new AudioContext();
        const source = audioCtx.createMediaStreamSource(stream);
        const analyser = audioCtx.createAnalyser();
        analyser.fftSize = 256;
        const bufferLength = analyser.frequencyBinCount;
        const dataArray = new Uint8Array(bufferLength);
        source.connect(analyser);

        function draw() {
          if (isDrawing) {
            const ID = requestAnimationFrame(draw);
            setDrawID(ID);
            analyser.getByteFrequencyData(dataArray);
            const canvasCtx = canvasRef.current.getContext('2d');
            const WIDTH = canvasRef.current.width;
            const HEIGHT = canvasRef.current.height;
            canvasCtx.clearRect(0, 0, WIDTH, HEIGHT);
            const barWidth = (WIDTH / bufferLength) * 2.5;
            let x = 0;
            for (let i = 0; i < bufferLength; i++) {
              const barHeight = dataArray[i];
              canvasCtx.fillStyle = 'white';
              canvasCtx.fillRect(x, HEIGHT - barHeight / 2, barWidth, barHeight);
              x += barWidth + 1;
            }
          }
        }

        mediaStreamRef.current = stream;

        let recorder = RecordRTC(stream, {
          type: 'audio',
          mimeType: 'audio/wav',
          recorderType: RecordRTC.StereoAudioRecorder,
        });
        recorderRef.current = recorder;

        recorderRef.current.startRecording();
        setIsRecording(true);
        draw();
      })
      .catch((error) => {
        console.error('Error accessing microphone:', error);
      });
  };

  const stopRecording = () => {
    recorderRef.current.stopRecording(function () {
      let blob = recorderRef.current.getBlob();
      setRecordedBlob(blob);
      const audioURL = window.URL.createObjectURL(blob);
      const audio = new Audio(audioURL);
      audio.play();
    });

    mediaStreamRef.current.getTracks().forEach((track) => track.stop());
    cancelAnimationFrame(drawID);
    setIsFinish(true);
    setIsDrawing(false);
  };

  const finishTest = () => {
    const formData = new FormData();
    formData.append('fileWav', recordedBlob);

    postTimbre({ formData })
      .then((res) => {
        router.push(`/inspectation/vocal-color-result/${res.timbreId}`);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  return (
    <>
      <div className="fade-in-custom-10s flex h-40  w-full flex-col lg:h-48">
        <p className="my-2 flex font-nanum text-xl text-white lg:text-3xl">
          음색 검사 절차 및 유의사항
        </p>
        <p className="my-1 ml-2 flex font-nanum text-sm text-white lg:text-lg">
          1. 조용한 환경에서 녹음을 실시합니다.
        </p>
        <p className="my-1 ml-2 flex font-nanum text-sm text-white lg:text-lg">
          2. 녹음 시작을 클릭하고 아래 제시된 문장을 읽습니다.
        </p>
        <p className="my-1 ml-2 flex font-nanum text-sm text-white lg:text-lg">
          3. 녹음이 완료된 후 녹음 완료 버튼을 클릭합니다.
        </p>
        <p className="my-1 ml-2 flex font-nanum text-sm text-white lg:text-lg">
          4. 검사 제출 버튼을 클릭합니다.
        </p>
      </div>
      <div className="fade-in-custom-15s flex h-44 w-full flex-col items-center justify-start bg-transparent lg:h-60 lg:items-start">
        <div className="mb-2 flex flex-row items-start justify-start">
          <p className="flex font-nanum text-lg text-white lg:text-3xl">제시된 문장</p>
        </div>
        <div className="flex w-full grow flex-col items-center justify-center rounded-xl bg-slate-500">
          <p className="my-1 flex text-center font-nanum text-sm text-white lg:text-xl">
            본 강의는 삼성청년 SW아카데미의 컨텐츠로 보안서약
          </p>
          <p className="my-1 flex text-center font-nanum text-sm text-white lg:text-xl">
            의거하여 강의 내용을 어떠한 사유로도 임의로 복사,
          </p>
          <p className="my-1 flex text-center font-nanum text-sm text-white lg:text-xl">
            복제, 보관, 전송하거나 허가 받지 않은 저장매체를 이용
          </p>
          <p className="my-1 flex text-center font-nanum text-sm text-white lg:text-xl">
            제 3자에게 누설, 공개 또는 사용하는 등의 행위를 금
          </p>
        </div>
      </div>
      <div className="fade-in-custom-20s flex h-44 w-full flex-row lg:h-60">
        <div className="flex h-full w-1/2 rounded-xl bg-gradient-to-r from-pink-500 via-red-500 to-yellow-500 p-1 ">
          <div className="flex h-full w-full items-center justify-center rounded-xl bg-black">
            <canvas ref={canvasRef} className="flex h-5/6 w-5/6 " />
          </div>
        </div>
        <div className="flex h-full w-1/2 flex-col items-center justify-around bg-transparent">
          <button
            onClick={startRecording}
            disabled={isRecording}
            className="flex h-1/4 w-5/6 flex-row items-center justify-center rounded-lg border-2"
          >
            <p className="text-lg text-white">녹음 시작</p>
          </button>
          <button
            onClick={stopRecording}
            disabled={isFinish}
            className="flex h-1/4 w-5/6 flex-row items-center justify-center rounded-lg border-2"
          >
            <p className="text-lg text-white">녹음 완료</p>
          </button>
          <button
            onClick={finishTest}
            className="flex h-1/4 w-5/6 flex-row items-center justify-center rounded-lg border-2"
          >
            <p className="text-lg text-white">검사 제출</p>
          </button>
        </div>
      </div>
      ;
    </>
  );
};
