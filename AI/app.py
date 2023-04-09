from flask import Flask,request, jsonify
import module.preprocess as PROCESSING
from sklearn.preprocessing import LabelEncoder
from keras.models import load_model
import numpy as np
import os
app = Flask(__name__)
@app.route('/', methods=['GET'])
def aliveTest():
    return "ALIVE"

@app.route('/timbre', methods=['POST'])
def similarityPercent():
    file_wav = request.files['file_wav']
    print(file_wav)
    trans_data = PROCESSING.load_wav_file( file_wav,16000,20,True,True,True,True,True)
    processing_data = PROCESSING.preprocess_features([trans_data],feature_size = trans_data.shape[0])
    pred = model.predict(processing_data)
    
    processing_data_mean = processing_data.mean(axis=1)[0]
    processing_data_var = processing_data.var(axis=1)[0]
    
    processing_data_mean = (processing_data_mean-MEAN_G_MEAN_BY_COL)/MEAN_G_STD_BY_COL
    processing_data_var = (processing_data_var-VAR_G_MEAN_BY_COL)/VAR_G_STD_BY_COL

    return_obj = dict()
    return_obj["singer"] = label_classes.tolist()
    return_obj["similaritypercent"] = pred.tolist()[0]
    return_obj["mfcc_mean"] = processing_data_mean[:20].mean()
    return_obj["stft_mean"] = processing_data_mean[20:31].mean()
    return_obj["zcr_mean"] = processing_data_mean[32]
    return_obj["spc_mean"] = processing_data_mean[33]
    return_obj["spr_mean"] = processing_data_mean[34]
    return_obj["rms_mean"] = processing_data_mean[35]

    return_obj["mfcc_var"] = processing_data_var[:20].mean()
    return_obj["stft_var"] = processing_data_var[20:31].mean()
    return_obj["zcr_var"] = processing_data_var[32]
    return_obj["spc_var"] = processing_data_var[33]
    return_obj["spr_var"] = processing_data_var[34]
    return_obj["rms_var"] = processing_data_var[35]
    return jsonify(return_obj)


if __name__ == '__main__':
    SR = 16000
    ROOT = "/DATA"
    FEATURES = os.environ['FLASK_FEATURES']
    TARGET_EPOCH = os.environ['FLASK_TARGET_EPOCH']
    MODEL_VERSION = os.environ['FLASK_MODEL_VERSION']
    TENSER_PATH = f"{ROOT}/tensor/{FEATURES}"
    CHECKPOINT_PATH = f"{ROOT}/checkpoint/{MODEL_VERSION}"
    CHECK_POINT_FILE = f"{FEATURES}/checkpoint{TARGET_EPOCH}.h5"
    
    encoder = LabelEncoder()
    encoder.fit(np.load(f'{TENSER_PATH}/y.npy'))
    label_classes = encoder.inverse_transform(np.arange(len(encoder.classes_)))
    MEAN_G_MEAN_BY_COL = np.load(f'{TENSER_PATH}/mean_g_mean_by_col.npy')
    MEAN_G_STD_BY_COL = np.load(f'{TENSER_PATH}/mean_g_std_by_col.npy')
    VAR_G_MEAN_BY_COL = np.load(f'{TENSER_PATH}/var_g_mean_by_col.npy')
    VAR_G_STD_BY_COL = np.load(f'{TENSER_PATH}/var_g_std_by_col.npy')
    
    model = load_model(f"{CHECKPOINT_PATH}/{CHECK_POINT_FILE}")
    print("start_flask",model)
    app.run(host="0.0.0.0", port=int(os.environ.get("FLASK_PORT", 5000)))