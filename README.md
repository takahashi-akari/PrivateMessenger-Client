# PrivateMessenger-Client
PrivateMessenger-Client with Kafka

## Description
PrivateMessenger with Kafka.  
The Linux client software and the Kafka server software are both written in Java.  
Secure and Private messaging with Kafka.  
TCP/IP communication with Kafka.  
Kafka is a distributed publish-subscribe messaging system.  
Kafka is a high-throughput, high-availability, and low-latency messaging system.
~~~
+----------------------+          +--------------------------+          +----------------------+
|    Linux Client      |   TCP    |   Central Kafka Server   |   TCP    |    Linux Client      |
|   ClientSoftware     +<-------->+    RealTimeMessaging     +<-------->|   ClientSoftware     +
|Ubuntu 20.04LTS x86_64| Messages |  Ubuntu 20.04LTS  x86_64 | Messages |Ubuntu 20.04LTS x86_64|
+----------------------+          +--------------------------+          +----------------------+ 
~~~

## Install
Install the following packages:

```
$ sudo apt-get install openjdk-11-jdk
$ sudo apt-get install kafka
```

## Usage
### Start
Start the following commands:

```
$ java -jar PrivateMessenger-Client.jar
```


### Stop
Stop the following commands:

```
$ java -jar PrivateMessenger-Client.jar stop
```

## Links
- [Kafka](https://kafka.apache.org/)
- [PrivateMessenger](https://github.com/takahashi-akari/PrivateMessenger)
- [PrivateMesenger-Client](https://github.com/takahashi-akari/PrivateMessenger-Client)
- [PrivateMessenger-Server](https://github.com/takahashi-akari/PrivateMessenger-Server)

## License
MIT License
copyright (c) 2020 [Takahashi Akari](https://github.com/takahashi-akari)
