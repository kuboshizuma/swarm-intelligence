import java.util.ArrayList;
import java.util.Random;
import java.util.Collections;

// For Graph
import org.jfree.chart.JFreeChart;
import org.jfree.chart.ChartFactory;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.chart.plot.PlotOrientation;
import javax.swing.JFrame;
import org.jfree.chart.ChartPanel;
import java.awt.BorderLayout;

public class ArtificialBeeColony extends JFrame{
  public int MAX_LENGTH;
  public int NP;
  public int FOOD_NUMBER;
  public int LIMIT;
  public int MAX_EPOCHS;
  public double MIN_SHRESHOLD;
  public double MAX_SHRESHOLD;
  private DefaultCategoryDataset graph;

  public Random rand;
  public ArrayList<Honey> foodSources;
  // public ArrayList<Honey> solutions;
  public Honey gBest;
  public int epoch;

  public ArtificialBeeColony(int beeNumber) {
    MAX_LENGTH = 2;
    NP = beeNumber;
    FOOD_NUMBER = NP / 2;
    LIMIT = 50;
    MAX_EPOCHS = 1000;
    gBest = null;
    epoch = 0;
    MIN_SHRESHOLD = -5.0;
    MAX_SHRESHOLD = 5.0;
    graph = new DefaultCategoryDataset();
  }

  public void algorithm() {
    foodSources = new ArrayList<Honey>();
    // solutions = new ArrayList<Honey>();
    rand = new Random();
    boolean done = false;
    epoch = 0;

    initialize();
    memorizeBestFoodSource();

    while(!done) {
      if(epoch < MAX_EPOCHS) {
          sendEmployedBees();
          getFitness();
          calculateProbabilities();
          sendOnlookerBees();
          memorizeBestFoodSource();
          sendScoutBees();

          epoch++;
          // System.out.println("Epoch: " + epoch);
      } else {
          done = true;
      }

      if ((epoch-1) % (MAX_EPOCHS/100) == 0) {
        graph.addValue(gBest.getCost(), "cost", String.valueOf(epoch-1));
      }
    }


    System.out.println("--- BEST SOLUTION ---");
    System.out.println("cost:" + gBest.getCost());
    for(int i = 0; i < MAX_LENGTH; i++) {
      System.out.println("n = " + (i+1) + " : " + gBest.getNectar(i));
    }

    // For Graph
    JFreeChart chart = ChartFactory.createLineChart("", "epoch", "cost", graph, PlotOrientation.VERTICAL, true, false, false);
    ChartPanel cpanel = new ChartPanel(chart);
    getContentPane().add(cpanel, BorderLayout.CENTER);

    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setBounds(10, 10, 1300, 1300);
    this.setTitle("Cost transition");
    this.setVisible(true);

  }

  public void initialize() {
    int newFoodIndex = 0;

    for(int i = 0; i < FOOD_NUMBER; i++) {
        Honey newHoney = new Honey(MAX_LENGTH, MIN_SHRESHOLD, MAX_SHRESHOLD);

        foodSources.add(newHoney);
        newFoodIndex = foodSources.indexOf(newHoney);

        foodSources.get(newFoodIndex).computeCost();
    }
  }

  public void memorizeBestFoodSource() {
    gBest = Collections.min(foodSources);
  }

  public void sendEmployedBees() {
      int neighborBeeIndex = 0;
      Honey currentBee = null;
      Honey neighborBee = null;

      for(int i = 0; i < FOOD_NUMBER; i++) {
          neighborBeeIndex = getExclusiveRandomNumber(FOOD_NUMBER-1, i);
          currentBee = foodSources.get(i);
          neighborBee = foodSources.get(neighborBeeIndex);
          sendToWork(currentBee, neighborBee);
      }
  }

  public void sendOnlookerBees() {
    int i = 0;
    int t = 0;
    int neighborBeeIndex = 0;
    Honey currentBee = null;
    Honey neighborBee = null;

    while(t < FOOD_NUMBER) {
      currentBee = foodSources.get(i);
      if(rand.nextDouble() < currentBee.getSelectionProbability()) {
        t++;
        neighborBeeIndex = getExclusiveRandomNumber(FOOD_NUMBER-1, i);
        neighborBee = foodSources.get(neighborBeeIndex);
        sendToWork(currentBee, neighborBee);
      }
      i++;
      if(i == FOOD_NUMBER) {
          i = 0;
      }
    }
  }

  public void sendToWork(Honey currentBee, Honey neighborBee) {
    double newValue = 0.0;
    double tmpValue = 0.0;
    int tmpIndex = 0;
    double prevCost = 0.0;
    double currCost = 0.0;
    int parameterToChange = 0;

    prevCost = currentBee.getCost();

    parameterToChange = getRandomNumber(0, MAX_LENGTH-1);

    tmpValue = currentBee.getNectar(parameterToChange);
    newValue = tmpValue + (tmpValue - neighborBee.getNectar(parameterToChange)) * (rand.nextDouble()-0.5) * 2;

    if(newValue < MIN_SHRESHOLD) {
        newValue = MIN_SHRESHOLD;
    }

    if(newValue > MAX_SHRESHOLD) {
        newValue = MAX_SHRESHOLD;
    }

    currentBee.setNectar(parameterToChange, newValue);
    currentBee.computeCost();
    currCost = currentBee.getCost();

    if(prevCost < currCost) {
        currentBee.setNectar(parameterToChange, tmpValue);
        currentBee.computeCost();
        currentBee.setTrials(currentBee.getTrials() + 1);
    } else {
        currentBee.setTrials(0);
    }
  }

  public void sendScoutBees() {
      Honey currentBee = null;

      for(int i = 0; i < FOOD_NUMBER; i++) {
        currentBee = foodSources.get(i);
        if (currentBee.getTrials() > LIMIT) {
          currentBee.initNectar();
          currentBee.computeCost();
          currentBee.setTrials(0);
        }
      }
  }

  public void getFitness() {
    Honey currentBee = null;
    double cost = 0;
    double fitness = 0;

    for(int i = 0; i < FOOD_NUMBER; i++) {
        currentBee = foodSources.get(i);
        cost = currentBee.getCost();

        if (cost >= 0) {
          fitness = 1.0 / ( 1.0 + cost);
        } else {
          fitness= 1.0 - cost; // 1.0 + abs(cost)
        }
        currentBee.setFitness(fitness);
    }
  }

  public void calculateProbabilities() {
    Honey currentBee = null;
    double fitnessSum = 0;

    for(int i = 1; i < FOOD_NUMBER; i++) {
        currentBee = foodSources.get(i);
        fitnessSum += currentBee.getFitness();
    }

    for(int j = 0; j < FOOD_NUMBER; j++) {
        currentBee = foodSources.get(j);
        currentBee.setSelectionProbability(currentBee.getFitness() / fitnessSum);
    }
  }


  public int getRandomNumber(int low, int high) {
      return (int)Math.round((high - low) * rand.nextDouble() + low);
  }

  public int getExclusiveRandomNumber(int high, int except) {
      boolean done = false;
      int getRand = 0;

      while(!done) {
          getRand = rand.nextInt(high);
          if(getRand != except){
              done = true;
          }
      }

      return getRand;
  }
}
