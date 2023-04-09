import { useRef, useState } from 'react';
import { useRouter } from 'next/router';
import RecordRTC from 'recordrtc';

import { postPitch } from '@/features/inspectation';

export const RecorderRange = () => {
  const router = useRouter();

  const [isHighRecording, setIsHighRecording] = useState(false);
  const [isHighFinish, setIsHighFinish] = useState(false);
  const [isLowRecording, setIsLowRecording] = useState(false);
  const [isLowFinish, setIsLowFinish] = useState(false);
  const [recordedHighBlob, setRecordedHighBlob] = useState(null); // High Blob
  const [recordedLowBlob, setRecordedLowBlob] = useState(null); // Low Blob
  const mediaStreamRef = useRef(null);
  const recorderRef = useRef(null);
  const [isDrawing, setIsDrawing] = useState(true);
  const [drawID, setDrawID] = useState(null);
  const canvasRef = useRef(null);

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
        if (!isHighRecording) setIsHighRecording(true);
        else setIsLowRecording(true);
        draw();
      })
      .catch((error) => {
        console.error('Error accessing microphone:', error);
      });
  };

  const stopRecording = () => {
    recorderRef.current.stopRecording(function () {
      let blob = recorderRef.current.getBlob();
      if (isHighRecording) {
        setRecordedHighBlob(blob);
      }
      if (isHighFinish) {
        setRecordedLowBlob(blob);
      }
      const audioURL = window.URL.createObjectURL(blob);
      const audio = new Audio(audioURL);
      audio.play();
    });

    mediaStreamRef.current.getTracks().forEach((track) => track.stop());
    cancelAnimationFrame(drawID);
    if (isHighRecording) setIsHighFinish(true);
    else setIsLowFinish(true);
  };

  const finishTest = () => {
    const formData = new FormData();
    formData.append('highOctave', recordedHighBlob);
    formData.append('lowOctave', recordedLowBlob);

    postPitch({ formData })
      .then((res) => {
        console.log(res);
        router.push(`/inspectation/vocal-range-result/${res.pitchId}`);
      })
      .catch((err) => {
        console.log(err);
      });
  };
  return (
    <>
      {/* 음역대 검사 주의사항 및 방법 */}
      <div className="fade-in-custom-10s flex h-40  w-full flex-col lg:h-48">
        <p className="my-2 flex font-nanum text-xl text-white lg:text-3xl">
          음역대 검사 절차 및 유의사항
        </p>
        <p className="my-1 ml-2 flex font-nanum text-sm text-white lg:text-lg">
          1. 조용한 환경에서 녹음을 실시합니다.
        </p>
        <p className="my-1 ml-2 flex font-nanum text-sm text-white lg:text-lg">
          2. 최고 및 최저음은 최소 5초 이상 지속해야합니다.
        </p>
        <p className="my-1 ml-2 flex font-nanum text-sm text-white lg:text-lg">
          3. 최고음 측정 후 최저음 측정을 진행합니다.
        </p>
        <p className="my-1 ml-2 flex font-nanum text-sm text-white lg:text-lg">
          4. 두가지 검사를 실시 후 검사 제출 버튼을 클릭합니다.
        </p>
      </div>
      {/* 명령어(high, low) */}
      <div className="fade-in-custom-15s flex h-44 w-full flex-col items-center justify-center bg-transparent lg:h-60">
        <div className="flex h-full w-full rounded-xl bg-gradient-to-r  from-pink-500 via-red-500 to-yellow-500 p-1 ">
          <div className="flex h-full w-full items-center justify-center rounded-xl bg-black">
            <canvas ref={canvasRef} className="flex h-5/6 w-5/6 "></canvas>
          </div>
        </div>
      </div>
      {/* 녹음 버튼 및 오디오 비주얼라이제이션 */}
      <div className="fade-in-custom-20s flex h-44 w-full flex-row flex-wrap justify-around lg:h-60">
        <button
          onClick={startRecording}
          disabled={isHighRecording}
          className="flex h-1/4 w-5/12 flex-row items-center justify-center rounded-lg border-2"
        >
          <p className="text-lg text-white">최고음 측정</p>
        </button>
        <button
          onClick={stopRecording}
          disabled={isHighFinish}
          className="flex h-1/4 w-5/12 flex-row items-center justify-center rounded-lg border-2"
        >
          <p className="text-lg text-white ">최고음 종료</p>
        </button>
        <button
          onClick={startRecording}
          disabled={isLowRecording}
          className="flex h-1/4 w-5/12 flex-row items-center justify-center rounded-lg border-2"
        >
          <p className="text-lg text-white">최저음 측정</p>
        </button>
        <button
          onClick={stopRecording}
          disabled={isLowFinish}
          className="flex h-1/4 w-5/12 flex-row items-center justify-center rounded-lg border-2"
        >
          <p className="text-lg text-white">최저음 종료</p>
        </button>
        <button
          onClick={finishTest}
          disabled={!(isHighFinish || isLowFinish)}
          className="flex h-1/4 w-5/12 flex-row items-center justify-center rounded-lg border-2"
        >
          <p className="text-lg text-white">검사 제출</p>
        </button>
      </div>
    </>
  );
};
