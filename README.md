# this is a kafka project that subscribes to a topic and writes the messages that display on the console of the consumer

## How to run the project

> i install kafka Broker in wsl ubuntu, so i have to run a command to be able to publish in topic from windows

```bash
> netsh interface portproxy add v4tov4 listenport=9092 listenaddress=0.0.0.0 connectport=9092 connectaddress=172.X.X.X
```