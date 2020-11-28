# yautils
Yet another java utilities for various tasks

## Async 
Utility for launching asynchronous tasks easilly

### Initialize
```java
    Async async = new Async(); 
```

### Submit simple task
```java
    async.submit(() -> System.out.println("execute asynchronously"));     
```

### Submit repeatable task
```java
    async.submitWithInterval(() -> {
        System.out.println("sending heartbeat....");
    }, 1000L);
```

### Run task which returns value
```java
    Result<String> result = async.submit(()-> "result");
    assert result.get() == "result";
```
     
### Shutdown executor
```java
    async.shutdown();
```
