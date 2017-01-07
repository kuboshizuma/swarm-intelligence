import java.util.Random;

public class Honey implements Comparable<Honey> {
  private int MAX_LENGTH;
  private double nectar[];
  private int trials;
  private double cost;
  private double fitness;
  private double selectionProbability;
  private double MIN_VALUE;
  private double MAX_VALUE;
  private Random rand;
  private ObjectiveFunctions objFunc;

  public Honey(int n, double min, double max, String objType) {
    MAX_LENGTH = n;
    nectar = new double[MAX_LENGTH];
    trials = 0;
    cost = 0.0;
    fitness = 0.0;
    selectionProbability = 0.0;
    MIN_VALUE = min;
    MAX_VALUE = max;
    rand = new Random();
    objFunc = new ObjectiveFunctions(objType);
    initNectar();
  }

  public int compareTo(Honey h) {
    int result = 0;
    if (this.cost < h.getCost()) {
      result = -1;
    } else if (this.cost > h.getCost()) {
      result = 1;
    }

    return result;
  }

  public void initNectar() {
    for(int i = 0; i < MAX_LENGTH; i++) {
      nectar[i] = RandomValue(MIN_VALUE, MAX_VALUE);
    }
  }

  public void computeCost() {
    double calcCost = objFunc.calc(nectar);
    this.cost = calcCost;
  }

  public double getCost() {
    return this.cost;
  }

  public double getNectar(int index) {
    return this.nectar[index];
  }

  public void setNectar(int index, double value) {
    this.nectar[index] = value;
  }

  public void setFitness(double value) {
    this.fitness = value;
  }

  public double getFitness() {
    return this.fitness;
  }

  public void setSelectionProbability(double value) {
    this.selectionProbability = value;
  }

  public double getSelectionProbability() {
    return this.selectionProbability;
  }

  public void setTrials(int value) {
    this.trials = value;
  }

  public int getTrials() {
    return this.trials;
  }

  public double RandomValue(double low, double high) {
    return (high - low) * rand.nextDouble() + low;
  }

}
