# Homework 8

## Discussion 

We ask you to run the Driver program, as well as the SystemRuntimeTest 
and MemoryMonitorTest and report their outcome in your README file, for the following 
endpoints (using the baltimore.streets.txt file):
System Test:
Please highlight (note in README) if you make an interesting observation,
for example, if your program fails to find a path, if the performance changes
for different endpoints, etc.

The memory usage stayed consistent (for the most part) for each of the endpoints.
Compared to the example memory usage, each of my endpoints had a smaller memory 
usage for the creation of an empty graph, but for loading and finding, the memory
usage seemed similar. 

//-----------------------------------------------
JHU to Druid Lake
Starting location: -76.6175,39.3296
Ending location: -76.6383,39.3206

Memory Monitor Test:
Config: baltimore.streets.txt from -76.6175,39.3296 to -76.6383,39.3206
	Used memory: 6274.19 KB (Δ = 0.000)
Instantiating empty Graph data structure
Instantiating empty StreetSearcher object
	Used memory: 6448.18 KB (Δ = 173.992)
Loading the network
	Used memory: 15888.98 KB (Δ = 9440.797)
Finding the shortest path
	Used memory: 16066.46 KB (Δ = 177.484)
Setting objects to null (so GC does its thing!)
	Used memory: 8882.02 KB (Δ = -7184.445)

SystemRunTest:
Loading network took 160 milliseconds.
Finding shortest path took 65 milliseconds.
//----------------------------------------------
7-11 to Druid Lake
Starting location: -76.6214,39.3212
Ending location: -76.6383,39.3206

Memory Monitor Test:
Config: baltimore.streets.txt from -76.6214,39.3212 to -76.6383,39.3206
	Used memory: 6286.86 KB (Δ = 0.000)
Instantiating empty Graph data structure
Instantiating empty StreetSearcher object
	Used memory: 6460.08 KB (Δ = 173.219)
Loading the network
	Used memory: 15854.78 KB (Δ = 9394.703)
Finding the shortest path
	Used memory: 16023.14 KB (Δ = 168.359)
Setting objects to null (so GC does its thing!)
	Used memory: 8833.45 KB (Δ = -7189.695)

SystemRunTest:
Loading network took 138 milliseconds.
Finding shortest path took 35 milliseconds.

//----------------------------------------
Inner Harbor to JHU
Starting location: -76.6107,39.2866
Ending location: -76.6175,39.3296

Memory Monitor Test:
Used memory: 6294.41 KB (Δ = 0.000)
Instantiating empty Graph data structure
Instantiating empty StreetSearcher object
Used memory: 6460.70 KB (Δ = 166.297)
Loading the network
Used memory: 15845.87 KB (Δ = 9385.164)
Finding the shortest path
Used memory: 16038.83 KB (Δ = 192.961)
Setting objects to null (so GC does its thing!)
Used memory: 8851.76 KB (Δ = -7187.070)

System Test:
Loading network took 217 milliseconds.
Finding shortest path took 53 milliseconds.

