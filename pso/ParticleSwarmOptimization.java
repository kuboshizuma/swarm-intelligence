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

public class ParticleSwarmOptimization extends JFrame{
  private int MAX_LENGTH;
  private int PARTICLE_COUNT;
  private int MAX_EPOCHS;
  private Particle GLOBAL_BEST_PARTICLE;
  private ArrayList<Particle> particles;
  private ArrayList<Particle> solutions;
  private Random rand;
  private int epoch;
  private double MIN_SHRESHOLD;
  private double MAX_SHRESHOLD;
  private String OBJ_TYPE;
  private DefaultCategoryDataset graph;

  public ParticleSwarmOptimization(int particleCount, int demension, int epochs, double minShreshold, double maxShreshold, String objType) {
    MAX_LENGTH = demension;
    PARTICLE_COUNT = particleCount;
    MAX_EPOCHS = epochs;
    rand = new Random();
    graph = new DefaultCategoryDataset();
    OBJ_TYPE = objType;
    MIN_SHRESHOLD = minShreshold;
    MAX_SHRESHOLD = maxShreshold;
  }

  public void algorithm() {
    particles = new ArrayList<Particle>();
    solutions = new ArrayList<Particle>();
    epoch = 0;
    boolean done = false;
    Particle aParticle = null;

    initialize();
    updateMinCost();

    while(!done) {
      if(epoch < MAX_EPOCHS) {
        for(int i = 0; i < PARTICLE_COUNT; i++) {
          aParticle = particles.get(i);
          aParticle.computeCost();
        }

        updatePosition();
        updateVelocity(0.5, 0.14);
        updateMinCost();

        epoch++;
        // System.out.println("epoch: " + epoch);
      } else {
        done = true;
      }

      if ((epoch-1) % (MAX_EPOCHS/100) == 0) {
        graph.addValue(GLOBAL_BEST_PARTICLE.getCost(), "cost", String.valueOf(epoch-1));
      }

      // System.out.println("cost:"+GLOBAL_BEST_PARTICLE.getCost());
    }

    System.out.println("--- BEST SOLUTION ---");
    System.out.println("cost:"+GLOBAL_BEST_PARTICLE.getCost());
    for(int i = 0; i < MAX_LENGTH; i++) {
      System.out.println("n = " + (i+1) + " : " + GLOBAL_BEST_PARTICLE.getPosition(i));
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
    int newParticleIndex = 0;
    int shuffles = 0;

    for(int i = 0; i < PARTICLE_COUNT; i++) {
      Particle newParticle = new Particle(MAX_LENGTH, MIN_SHRESHOLD, MAX_SHRESHOLD, OBJ_TYPE);

      particles.add(newParticle);
      newParticleIndex = particles.indexOf(newParticle);
      particles.get(newParticleIndex).computeCost();

    }

    solutions = particles;
    GLOBAL_BEST_PARTICLE = Collections.min(solutions);
  }

  public void updateMinCost() {
    Particle aParticle = null;
    Particle bParticle = null;
    Particle minParticle = null;
    double ascore = 0;
    double bscore = 0;

    for(int i = 0; i < PARTICLE_COUNT; i++) {
      aParticle = particles.get(i);
      bParticle = solutions.get(i);
      aParticle.computeCost();
      ascore = aParticle.getCost();
      bscore = bParticle.getCost();

      if(ascore < bscore) {
        solutions.set(i, aParticle);
      }
    }
    minParticle = Collections.min(solutions);
    if (minParticle.getCost() < GLOBAL_BEST_PARTICLE.getCost()) {
      GLOBAL_BEST_PARTICLE = minParticle;
    }
  }

  public void updateVelocity(double w, double ro_max) {
    Particle aParticle = null;
    Particle bParticle = null;
    for(int i = 0; i < PARTICLE_COUNT; i++) {
      aParticle = particles.get(i);
      Particle aSolution = solutions.get(i);
      for(int j = 0; j < MAX_LENGTH; j++) {
        double aPos = aParticle.getPosition(j);
        double aVel = aParticle.getVelocity(j);
        double pBest = aSolution.getPosition(j);
        double gBest = GLOBAL_BEST_PARTICLE.getPosition(j);
        double ro1 = rand.nextDouble() * ro_max;
        double ro2 = rand.nextDouble() * ro_max;
        aParticle.setVelocity(j, w * aVel + ro1 * (pBest - aPos) + ro2 * (gBest - aPos));
      }
    }
  }

  public void updatePosition() {
    Particle aParticle = null;
    for(int i = 0; i < PARTICLE_COUNT; i++) {
      aParticle = particles.get(i);
      for(int j = 0; j < MAX_LENGTH; j++) {
        aParticle.setPosition(j, aParticle.getPosition(j) + aParticle.getVelocity(j));
      }
    }
  }

}
