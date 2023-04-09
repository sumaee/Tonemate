FROM python:3.10.9
ARG FLASK_ROOT
ARG FLASK_FEATURES
ARG FLASK_TARGET_EPOCH

ENV FLASK_ROOT=${FLASK_ROOT}
ENV FLASK_FEATURES=${FLASK_FEATURES}
ENV FLASK_TARGET_EPOCH=${FLASK_TARGET_EPOCH}

WORKDIR /app
COPY app.py /app
COPY requirements.txt /app
COPY ./module /app/module

RUN pip install --no-cache-dir -r requirements.txt 