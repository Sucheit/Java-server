запустить zookeper:
bin\windows\zookeeper-server-start.bat config\zookeeper.properties
запустить kafka:
bin\windows\kafka-server-start.bat config\server.properties
посмотреть топик:
bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic users-topic --from-beginning