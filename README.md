# Vertx + Metrics bug reproducer

In this project I'm trying to use Vertx with Micrometer, more specifically the class `StatsdMeterRegistry`.

When you use `visualvm` to look at the threads that are stuck, you will see the following image:

![hanging threads](hanging_threads.png)