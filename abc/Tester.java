// javac -classpath :jcommon.jar:jfreechart.jar Tester.java
// java -cp :jcommon.jar:jfreechart.jar Tester

public class Tester {
  ArtificialBeeColony abc;

  public void test(int beeNumber, int demension, int epochs, double minShreshold, double maxShreshold, int trialLimit, String objType) {
    abc = new ArtificialBeeColony(beeNumber, demension, epochs, minShreshold, maxShreshold, trialLimit, objType);
    abc.algorithm();
  }

  public static void main(String[] args) {
    Tester tester = new Tester();
    tester.test(40, 2, 100, -5.0, 5.0, 50, "shpere");
    // argument: (beeNumber, demension, epochs, minShreshold, maxShreshold, trialLimit, objType)
    // objType: shpere, threshold: [-5.0, 5.0]
    // objType: rastrigin, threshold: [-5.0, 5.0]
    // objType: rosenbrock, threshold: [-5.0, 5.0]
    // objType: griewank, threshold: [-600.0, 600.0]
    // objType: alpine, threshold: [-10.0, 10.0]
    // objType: minima, threshold: [-5.0, 5.0]
  }
}
