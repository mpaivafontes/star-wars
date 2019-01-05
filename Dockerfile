FROM java:8-alpine

#RUN wget http://download-keycdn.ej-technologies.com/jprofiler/jprofiler_linux_8_0_7.tar.gz -P /tmp/ &&\
# tar -xzf /tmp/jprofiler_linux_8_0_7.tar.gz -C /usr/local &&\
# rm /tmp/jprofiler_linux_8_0_7.tar.gz

#-agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n
#-agentpath:/usr/local/jprofiler8/bin/linux-x64/libjprofilerti.so=nowait
ENV JPAGENT_PATH=""
#ENV JPAGENT_PATH="$JPAGENT_PATH -agentlib:jdwp=transport=dt_socket,address=8000,server=y,suspend=n"

#EXPOSE 8849

WORKDIR /AppServer

#RUN rm -rf /tmp/star-wars

ADD target/star-wars.jar application.jar
ENV JAVA_OPTS="-XX:+HeapDumpOnOutOfMemoryError -Xmx1g -XX:+UseG1GC -Duser.timezone=America/Sao_Paulo"

ENTRYPOINT ["sh", "-c", "java $JAVA_OPTS $JPAGENT_PATH -jar application.jar"]