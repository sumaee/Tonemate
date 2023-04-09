from keras.models import Sequential
from keras.layers import Dense, LSTM, Dropout, BatchNormalization
from keras.callbacks import ModelCheckpoint

# LSTM 모델 생성
def create_lstm_model_v1(default=3, sr=16000, feature_size = 20):
    model = Sequential()
    model.add(LSTM(256, input_shape=(None, feature_size), return_sequences=True))
    model.add(BatchNormalization())
    model.add(Dropout(0.2))      
    
    model.add(LSTM(128, return_sequences=True))
    model.add(Dropout(0.2))
    
    model.add(LSTM(64))
    model.add(Dense(64,activation='relu'))
    model.add(Dense(default, activation='softmax'))
    return model

def create_lstm_model_v2(default=3, sr=16000, feature_size = 20):
    model = Sequential()
    model.add(LSTM(256, input_shape=(None, feature_size), return_sequences=True))
    model.add(LSTM(units=128))
    model.add(Dense(128, activation='relu'))
    model.add(Dropout(0.2))      
    model.add(Dense(default, activation='softmax'))
    return model

def Checkpoint(filepath):
    return ModelCheckpoint(filepath,save_weights_only=False, 
                            save_best_only=True, 
                            monitor='val_loss', 
                            verbose=1)