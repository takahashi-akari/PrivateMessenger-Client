# PrivateMessenger-Client
PrivateMessenger-Client with Kafka

# This repository is currently incomplete.

## Description
PrivateMessenger with Kafka.  
The Linux client software and the Kafka server software are both written in Java.  
Secure and Private messaging with Kafka.  
TCP/IP communication with Kafka.  
Kafka is a distributed publish-subscribe messaging system.  
Kafka is a high-throughput, high-availability, and low-latency messaging system.
~~~
+----------------------+          +---------------------------+          +----------------------+
|   Linux Client(GUI)  |   TCP    | Central Kafka Server(CUI) |   TCP    |   Linux Client(GUI)  |
|ClientSoftware(JFrame)+<-------->+    RealTimeMessaging      +<-------->+ClientSoftware(JFrame)|
|Ubuntu 20.04LTS x86_64| Messages |       KafkaServer         | Messages |Ubuntu 20.04LTS x86_64| 
|       Desktop        |          |  Ubuntu 20.04LTS  x86_64  |          |       Desktop        |
+----------------------+          +---------------------------+          +----------------------+ 
~~~

## Install
Install the following packages:

```
$ sudo apt-get install openjdk-8-jdk
```

## Compile
```bash
$ mvn clean compile assembly:single
```

## Usage
### Start
Start the following commands:

```
$ java -jar PrivateMessenger-Client-x.x.x-jar-with-dependencies.jar
```


### Stop
Stop the following commands:

```
$ java -jar PrivateMessenger-Client-x.x.x-jar-with-dependencies.jar stop
```

## Links
- [Kafka](https://kafka.apache.org/)
- [PrivateMessenger](https://github.com/takahashi-akari/PrivateMessenger)
- [PrivateMessenger-Client](https://github.com/takahashi-akari/PrivateMessenger-Client)
- [PrivateMessenger-Server](https://github.com/takahashi-akari/PrivateMessenger-Server)
- [JFrame OpenJDK](https://www.openjdk.java.net/projects/javafx/javafx-swing-components.html)
- [Kafka Server](https://kafka.apache.org/documentation/)
- [Kafka Client](https://kafka.apache.org/documentation/)
- [Kafka Connect](https://kafka.apache.org/documentation/)
- [Kafka Streams](https://kafka.apache.org/documentation/)

## License
MIT License
copyright (c) 2022 [Takahashi Akari](https://github.com/takahashi-akari)
