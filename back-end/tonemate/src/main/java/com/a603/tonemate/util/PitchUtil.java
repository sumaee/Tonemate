package com.a603.tonemate.util;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.pitch.PitchProcessor;
import com.a603.tonemate.dto.common.PitchResult;
import com.a603.tonemate.exception.NoFileException;
import com.a603.tonemate.exception.UnsupportedPitchFileException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class PitchUtil {
    private final float SAMPLE_RATE = 16000;
    private final int BUFFER_SIZE = 1024;
    private final int OVERLAP = 0;
    private final int[] octave = new int[]{
            16, 17, 18, 20, 21, 22, 23, 25, 26, 28, 29, 31,
            33, 35, 37, 39, 41, 44, 46, 49, 52, 55, 58, 62,
            65, 69, 73, 78, 82, 87, 93, 98, 104, 110, 117, 124,
            131, 139, 147, 156, 165, 175, 185, 196, 208, 220, 233, 247,
            262, 278, 294, 311, 330, 349, 370, 392, 415, 440, 466, 494
    };
    private final String[] octaveName = new String[]{
            "C0", "C#0", "D0", "D#0", "E0", "F0", "F#0", "G0", "G#0", "A0", "A#0", "B0",
            "C1", "C#1", "D1", "D#1", "E1", "F1", "F#1", "G1", "G#1", "A1", "A#1", "B1",
            "C2", "C#2", "D2", "D#2", "E2", "F2", "F#2", "G2", "G#2", "A2", "A#2", "B2",
            "C3", "C#3", "D3", "D#3", "E3", "F3", "F#3", "G3", "G#3", "A3", "A#3", "B3",
            "C4", "C#4", "D4", "D#4", "E4", "F4", "F#4", "G4", "G#4", "A4", "A#4", "B4",
    };

    private int getPitch(List<Float> pitchArray, List<Float> timeArray, boolean isHigh) {
        int selectedPitch = isHigh ? -1 : 60;
        int curPitch = binarySearch(octave, pitchArray.get(0));
        float curTime = timeArray.get(0);
        int compPitch = curPitch;
        for (int i = 1; i < pitchArray.size(); i++) {
            int pitch = binarySearch(octave, pitchArray.get(i));
            if (isHigh ? selectedPitch > pitch : selectedPitch < pitch) {
                continue;
            }
            if (Math.abs(curPitch - pitch) > 1 || (curPitch != pitch && curPitch != compPitch && compPitch != pitch)) {
                curPitch = pitch;
                compPitch = pitch;
                curTime = timeArray.get(i);
                continue;
            }
            if (curPitch != pitch) {
                compPitch = pitch;
            }
            float time = timeArray.get(i) - curTime;
            if (time > 0.3f) {
                selectedPitch = isHigh ? Math.min(curPitch, compPitch) : Math.max(curPitch, compPitch);
            }
        }
        return selectedPitch;
    }

    public String getOctaveName(int pitch) {
        pitch = Math.min(59, pitch);
        pitch = Math.max(0, pitch);
        return octaveName[pitch];
    }

    public PitchResult getPitch(MultipartFile multipartFile, boolean isHigh) {
        List<Float> pitchArray = new ArrayList<>();
        List<Float> timeArray = new ArrayList<>();
        File file = saveFile(multipartFile, isHigh);
        PitchProcessor pitchProcessor = new PitchProcessor(PitchProcessor.PitchEstimationAlgorithm.YIN, SAMPLE_RATE, BUFFER_SIZE,
                (pitchDetectionResult, audioEvent) -> {
                    float pitch = pitchDetectionResult.getPitch();
                    if (pitch != -1) {
                        pitchArray.add(pitch);
                        timeArray.add((float) audioEvent.getTimeStamp());
                    }
                });
        try {
            AudioDispatcher dispatcher = AudioDispatcherFactory.fromFile(file, BUFFER_SIZE, OVERLAP);
            dispatcher.addAudioProcessor(pitchProcessor);
            dispatcher.run();
        } catch (UnsupportedAudioFileException | IOException e) {
            throw new UnsupportedPitchFileException((isHigh ? "높은" : "낮은") + " 음역대 파일이 잘못됨");
        } finally {
            file.delete();
        }
        int idx = getPitch(pitchArray, timeArray, isHigh);
        return new PitchResult(idx, idx >= 0 && idx < octave.length);
    }

    private File saveFile(MultipartFile multipartFile, boolean isHigh) {
        File file;
        try {
            file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(multipartFile.getBytes());
        } catch (IOException e) {
            throw new UnsupportedPitchFileException((isHigh ? "높은" : "낮은") + " 음역대 파일이 잘못됨");
        } catch (NullPointerException e) {
            throw new NoFileException((isHigh ? "높은" : "낮은") + " 음역대 파일이 없음");
        }
        return file;
    }

    private int binarySearch(int[] arr, float target) {
        int left = 0;
        int right = arr.length - 1;

        while (right - left > 1) {
            int mid = (left + right) / 2;

            if (arr[mid] >= target) {
                right = mid;
            } else {
                left = mid;
            }
        }
        return left;
    }
}
