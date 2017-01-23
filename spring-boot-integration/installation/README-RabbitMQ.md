# Install: /usr/local/Cellar/rabbitmq/3.6.4/
```
  brew install rabbitmq
```

# update .bash_profile
#RabbitMQ: start: ./rabbitmq-server
export RABBITMQ_HOME=/usr/local/Cellar/rabbitmq/3.6.4/
export PATH=$PATH:$RABBIT_MQ_HOME/sbin:$RABBIT_MQ_HOME/ebin

# To have launchd start rabbitmq now and restart at login:
brew services start rabbitmq

# Start server
rabbitmq-server

# Check status
$ cd /usr/local/Cellar/rabbitmq/3.6.4/sbin
$ sh rabbitmqctl status

# Enable rabbitmq-plugins:
sh rabbitmq-plugins enable rabbitmq_management

# Monitor : guest/guest
http://localhost:15672/

# Stop server:
$ cd /usr/local/Cellar/rabbitmq/3.6.4/sbin
$ sh rabbitmqctl stop
