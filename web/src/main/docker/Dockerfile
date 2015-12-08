FROM java:8

ADD ./${project.name}-${project.version} .

VOLUME /tmp

EXPOSE 8080

RUN chmod u+x bin/${project.name}

ENTRYPOINT ["bin/${project.name}"]