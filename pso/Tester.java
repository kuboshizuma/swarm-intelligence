// javac -classpath :jcommon.jar:jfreechart.jar Tester.java
// java -cp :jcommon.jar:jfreechart.jar Tester

public class Tester {
  ParticleSwarmOptimization pso;

  public void test(int n) {
    pso = new ParticleSwarmOptimization(n);
    pso.algorithm();
  }

  public static void main(String[] args) {
    Tester tester = new Tester();
    tester.test(40, 100); // argument: (particleCount, epochs)
  }
}
