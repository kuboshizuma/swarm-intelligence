import java.util.Random;

public class Particle implements Comparable<Particle> {
  private int MAX_LENGTH;
  private double position[];
  private double velocity[];
  private double cost;
  private double MIN_VALUE;
  private double MAX_VALUE;
  private Random rand;
  private ObjectiveFunctions objFunc;

  public Particle(int n, double min, double max, String objType) {
    MAX_LENGTH = n;
    MIN_VALUE = min;
    MAX_VALUE = max;
    position = new double[MAX_LENGTH];
    velocity = new double[MAX_LENGTH];
    rand = new Random();
    objFunc = new ObjectiveFunctions(objType);
    initData();
  }

  public int compareTo(Particle p) {
    int result = 0;
    if (this.cost < p.getCost()) {
      result = -1;
    } else if (this.cost > p.getCost()) {
      result = 1;
    }

    return result;
  }

  public void initData() {
    for(int i = 0; i < MAX_LENGTH; i++) {
      position[i] = RandomValue(MIN_VALUE, MAX_VALUE);
      velocity[i] = 0;
    }
  }

  public void computeCost() {
    double calcCost = objFunc.calc(position);
    this.cost = calcCost;
  }

  public double getCost() {
    return this.cost;
  }

  public double getPosition(int index) {
    return position[index];
  }

  public void setPosition(int index, double value) {
    this.position[index] = value;
  }

  public double getVelocity(int index) {
    return velocity[index];
  }

  public void setVelocity(int index, double value) {
    this.velocity[index] = value;
  }

  public double getData(int index) {
    return this.position[index];
  }

  public void setData(int index, double value) {
    this.position[index] = value;
  }

  public double RandomValue(double low, double high) {
    return (high - low) * rand.nextDouble() + low;
  }
}
