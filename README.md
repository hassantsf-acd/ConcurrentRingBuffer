## Concurrent Ring Buffer
- Course: Operating System
- Semester: Spring 2023
- Instructor: Dr. Hamed Khan Mirza

This project focuses on understanding the data structure called Ring Buffer and its applications in operating systems. A Ring Buffer is a data structure similar to a Circular Queue, commonly used in operating systems for tasks like performance testing. In typical CPUs, each core has its own Ring Buffer. Multiple threads are running on each core, each with a function that is randomly called. These threads have two events: one before calling the main function of the thread and one after the main function is executed. These events are responsible for storing information in the Ring Buffer.

### Components of the System

#### Processor
The system has N cores, and each core has a Ring Buffer.
### System Threads
Each system thread has a main function that can perform various tasks (e.g., a thread can go to sleep).
Two events mentioned earlier must be executed in order, but the main function of each thread is called randomly.
#### Ring Buffer
The Ring Buffer acts like a Circular Queue.
It has two methods: put for adding data to the Ring Buffer and get for reading from it.
The initial size of the buffer is M, and it can grow up to a maximum size of M5. When buffers are full, they must expand until they reach their maximum size, and no further expansion is allowed.
#### Pulling Thread
This thread constantly checks the buffers and, when a Ring Buffer reaches its maximum size, it stores its information and empties it.
At the end of the system's operation, this thread gathers information from all buffers and saves it in a file. There is one Pulling thread for each buffer.

#### Input
- Number of cores N
- Initial size of buffers M
#### Output:
Information within the buffers should be stored in a file as per the specified process.
### Constraints
- Implementation should be done in Java, and the use of thread-safe data structures from java.util.concurrent is required
- The use of synchronized code blocks is prohibited. You must handle data protection yourself
