FROM ubuntu:16.04

RUN apt-get update -y && \
    apt-get upgrade -y && \
    apt-get install -y ca-certificates \
     wget \
     curl \
     git \
     apt-transport-https \
     vim \
     openjdk-8-jdk-headless \
     openjfx \
     maven \
     nano && \
    apt-get clean && \
    rm -rf /var/lib/apt/lists/*

VOLUME /build
WORKDIR /build
CMD [mvn,install]

