// javac -classpath :jcommon.jar:jfreechart.jar Tester.java
// java -cp :jcommon.jar:jfreechart.jar Tester

public class Tester {
  ArtificialBeeColony abc;

  public void test(int n) {
    abc = new ArtificialBeeColony(n);
    abc.algorithm();
  }

  public static void main(String[] args) {
    Tester tester = new Tester();
    tester.test(40); // argument: (beeNumber)
  }
}
