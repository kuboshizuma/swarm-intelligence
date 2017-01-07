# Swarm Intelligence

Swarm intelligence (SI) is the collective behavior of decentralized, self-organized systems, natural or artificial. The concept is employed in work on artificial intelligence.([Swarm intelligence - Wikipedia](https://en.wikipedia.org/wiki/Swarm_intelligence))

In this repository, a Java code is implemented for ABC(Artificial Bee Colony) algorithm and PSO(Partical Swarm Optimization) algorithm.
You can get the minimum value of various function.

```
objType: shpere, threshold: [-5.0, 5.0]
objType: rastrigin, threshold: [-5.0, 5.0]
objType: rosenbrock, threshold: [-5.0, 5.0]
objType: griewank, threshold: [-600.0, 600.0]
objType: alpine, threshold: [-10.0, 10.0]
objType: minima (2n-minima), threshold: [-5.0, 5.0]
```


# How To Use

## PSO

At the top directory, execute the command below, and you can get the result (score and graph).
If you want to try other parameters or objective function, please edit the parameters in PSOTester.java.

```
$ javac -cp :modules/jcommon.jar:modules/jfreechart.jar:modules:pso PSOTester.java
$ java -cp :modules/jcommon.jar:modules/jfreechart.jar:modules:pso PSOTester
```

## ABC

At the top directory, execute the command below, and you can get the result (score and graph).
If you want to try other parameters or objective function, please edit the parameters in ABCTester.java.

```
$ javac -cp :modules/jcommon.jar:modules/jfreechart.jar:modules:abc ABCTester.java
$ java -cp :modules/jcommon.jar:modules/jfreechart.jar:modules:abc ABCTester
```
