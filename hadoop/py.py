from pydub import AudioSegment
from midiutil import MIDIFile
import librosa

audio_file_path = 'audio-sample.mp3'
y, sr = librosa.load(audio_file_path)  # audio file을 읽어들입니다.

tempo, beat_frames = librosa.beat.beat_track(y=y, sr=sr)  # 비트와 템포를 계산합니다.
print("템포 추출 끝 tempo :" + str(tempo))

# Load audio file
audio_path = "audio.wav"
audio = AudioSegment.from_wav(audio_path)

# Extract audio data and convert to mono
data = audio.get_array_of_samples()
data = [x / 65536 for x in data]
data = [(x + y) / 2 for x, y in zip(data[0::2], data[1::2])]
downsample_rate = 2000
downsampled_data = []
for i in range(0, len(data), downsample_rate):
    downsampled_data.append(int(sum(data[i:i + downsample_rate]) / downsample_rate))

# Set MIDI parameters
track = 0
channel = 0
time = 0
volume = 100

# Create MIDI file
midi = MIDIFile(1)
midi.addTempo(track, time, int(tempo))
print("미디 생성 완료")

# Add notes to MIDI file
for i, pitch in enumerate(downsampled_data):
    if pitch is not None:
        midi.addNote(track, channel, pitch, time, i, volume)
    if i % 100000 == 0:
        print("총 " + str(len(downsampled_data)) + "중에 " + str(i) + "번째 완료")
print("미디에 노트 입력 완료 최대값 : "+str(max(downsampled_data)))

# Save MIDI file
midi_path = "output.midi"
with open(midi_path, "wb") as file:
    midi.writeFile(file)
print("미디 저장 완료")