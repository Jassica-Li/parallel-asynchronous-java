
# Concurrency VS Parallelism

# Using thread 

## Runnable and Thread in Java
1. In `ProductServiceUsingThread`, we reduce the executing time by almost half. The limitations of thread api![img.png](limitations_of_thread.png)

## Using ExecutorService (an improvement of thread)
1. ExecutorService in Java is an Asynchronous Task Execution Engine
2. The working process of ExecutorService ![img_1.png](working_of_executor.png)

##  Fork/Join Framework
1. an extension of ExecutorService
2. Is designed to achieve **Data Parallelism** while ExecutorService is designed to achieve **Task Based Parallelism**.


# Streams API

## Mix use of sequential() and parallel()
 ``List<String> mappedNameList = names
 .parallelStream()
 .map(ComputeUtil::addNameLengthTransform)
 .sequential()
 .toList();``  will take around 2 seconds

``List<String> mappedNameList = names
.stream()
.map(ComputeUtil::addNameLengthTransform)
.parallel()
.toList();`` will take reduce the time by 1/4

## the performance of your tasks is closely related with your number of cores,
try execute `Runtime.getRuntime().availableProcessors()`
As I have 11 cores, so it will take the same time for 6 parallel tasks and 11 tasks, while it's also the same for 12 and 22 tasks executed parallely.

## How parallelStream works?
![img.png](how_parallelstream_works.png)

## Invoking parallelStream() **does not guarantee** faster performance of your code
![img.png](summary_of_parallelStream.png)

# Q & A
1. Callable and Runnable?
2. Data Parallelism and Task Based Parallelism


