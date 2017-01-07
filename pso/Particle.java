import java.util.Random;

public class Particle implements Comparable<Particle> {
  private int MAX_LENGTH;
  private double position[];
  private double velocity[];
  private double cost;
  private double MIN_VALUE;
  private double MAX_VALUE;
  private Random rand;

  public Particle(int n, double min, double max) {
    MAX_LENGTH = n;
    MIN_VALUE = min;
    MAX_VALUE = max;
    position = new double[MAX_LENGTH];
    velocity = new double[MAX_LENGTH];
    rand = new Random();
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
    double tmp_cost = 0.0;
    for(int i = 0; i < MAX_LENGTH; i++) {
      // tmp_cost += Math.pow(position[i], 2);
      double x = position[i];
      // tmp_cost += Math.pow(x, 2) - 6 * x + 14;
      tmp_cost += Math.pow(x, 2) - 10 * Math.cos(2*x*Math.PI);
    }
    this.cost = tmp_cost;
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
