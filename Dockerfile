FROM ubuntu:latest
LABEL authors="paloma"

ENTRYPOINT ["top", "-b"]