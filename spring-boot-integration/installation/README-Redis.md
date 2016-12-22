


# Install Redis: /usr/local/Cellar/redis/3.2.6
```
brew install redis
```

# To have launchd start redis now and restart at login:
  brew services start redis

# Start service you can just run:
  * redis-server /usr/local/etc/redis.conf
  * redis-server

# Stop redis
  ps aux |grep redis-server
  then kill it

  
