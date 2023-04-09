import os
import librosa
import numpy as np

# 데이터 크기 조정
def resize(feature, new_size):
    pad_width = [(0, 0), (0, new_size - len(feature[0]))]
    
    if len(feature[0]) < new_size:
        feature = np.pad(feature, pad_width, mode='constant', constant_values=0)
    else:
        feature = feature[:, :new_size]
    return feature

# wav 파일 불러오기
def load_wav_file(file_path, sr=16000, MFCC= 20, STFT=False, ZCR=False, SPC=False, SPR=False, RMS=False):
    new_size = 3000
    y, _ = librosa.load(file_path, sr=sr)

    # wav 파일에서 추출한 특징 저장
    features = []

    # [ mfcc ] 음성 데이터를 특징 벡터로 변환해주는 알고리즘
    # shape : (20, x)
    
    mfcc = librosa.feature.mfcc( y = y, sr = sr, n_mfcc= MFCC)
    mfcc = resize(mfcc, new_size)
    features.extend(mfcc)

    # [ chroma_stft ] 옥타브는 무시하고, 12개의 음계에 대한 분포 나타냄
    # shape : (12, x) 
    if STFT:
        chroma_stft = librosa.feature.chroma_stft( y = y, sr =sr ) 
        chroma_stft = resize(chroma_stft, new_size)
        features.extend(chroma_stft)

    # [ zero_crossing_rate ]
    # shape : (1, x)
    if ZCR:
        zero_crossing_rate = librosa.feature.zero_crossing_rate( y = y, pad=False )
        zero_crossing_rate = resize(zero_crossing_rate, new_size)
        features.extend(zero_crossing_rate)

    # [ spectral_centroid ]
    # shape : (1, x)
    if SPC:
        spectral_centroid = librosa.feature.spectral_centroid( y = y, sr = sr )
        spectral_centroid = resize(spectral_centroid, new_size)
        features.extend(spectral_centroid)

    # [ spectral_rolloff ] 
    # shape : (1, x)
    if SPR:
        spectral_rolloff = librosa.feature.spectral_rolloff( y = y, sr = sr )
        spectral_rolloff = resize(spectral_rolloff, new_size)
        features.extend(spectral_rolloff)

    # [ rms ] 오디오 평균 음량 측정
    # shape : (1, x)
    if RMS:
        rms = librosa.feature.rms(y = y)
        rms = resize(rms, new_size)
        features.extend(rms)

    features = np.array(features)
    print(file_path, features.shape)
    return features

# wav 파일을 LSTM 모델에 적용하기 위해 데이터 전처리
def preprocess_features(features,min_len = 3000,feature_size = 20):
    new_features = []

    for feature in features:
        if feature.shape[1] >= min_len:
            new_features.append(feature[:, :min_len].T)
        else:
            temp_feature = np.zeros((min_len, feature_size))
            temp_feature[:feature.shape[1], :] = feature.T[:, :min_len]
            new_features.append(temp_feature)

    new_features_arr = np.array(new_features)
    return new_features_arr


def createDATA(PATH,SR,MFCC= 20, STFT=False, ZCR=False, SPC=False, SPR=False, RMS=False):
    labels = []
    features = []
    global encoder
    for root, dirs, files in os.walk(PATH):
        for file in files:
            if file.endswith(".wav"):
                file_path = os.path.join(root, file)
                label = os.path.basename(root)

                feature = load_wav_file(file_path,SR,MFCC, STFT, ZCR, SPC, SPR, RMS)
                labels.append(label)

                if not os.path.exists(root.replace("wav","numpy")):
                    os.makedirs(root.replace("wav","numpy"))
                np.save(f'{root.replace("wav","numpy")}/{file[:-4]}.npy', feature)


                features.append(feature)
    cnt = np.sum([MFCC, STFT, ZCR, SPC, SPR, RMS])
    features = preprocess_features(features,feature_size=cnt)
    return features, labels
