public class ObjectiveFunctions {
  private int funcTypeNum;

  public ObjectiveFunctions(String funcType) {
    funcTypeNum = 0;
    switch (funcType) {
      case "shpere":
        funcTypeNum = 0;
        break;
      case "rastrigin":
        funcTypeNum = 1;
        break;
      case "rosenbrock":
        funcTypeNum = 2;
        break;
      case "griewank":
        funcTypeNum = 3;
        break;
      case "alpine":
        funcTypeNum = 4;
        break;
      case "minima":
        funcTypeNum = 5;
        break;
      default:
        System.out.println("You should set a label of objective function");
    }
  }

  public double calc(double... values) {
    double result = 0.0;
    switch (funcTypeNum) {
      case 0:
        result = shpere(values);
        break;
      case 1:
        result = rastrigin(values);
        break;
      case 2:
        result = rosenbrock(values);
        break;
      case 3:
        result = griewank(values);
        break;
      case 4:
        result = alpine(values);
        break;
      case 5:
        result = minima(values);
        break;
    }

    return result;
  }

  public double shpere(double... values) {
    double tmpCost = 0.0;

    for(int i = 0; i < values.length; i++){
      tmpCost += Math.pow(values[i], 2);
    }

    return tmpCost;
  }

  public double rastrigin(double... values) {
    double tmpCost = 10.0 * values.length;

    for(int i = 0; i < values.length; i++){
      tmpCost += Math.pow(values[i], 2) - 10 * Math.cos(2 * values[i] * Math.PI);
    }

    return tmpCost;
  }

  public double rosenbrock(double... values) {
    double tmpCost = 0.0;

    for(int i = 0; i < values.length-1; i++){
      tmpCost += 100 * Math.pow(values[i+1] - values[i], 2) + Math.pow(1 - values[i], 2);
    }

    return tmpCost;
  }

  public double griewank(double... values) {
    double tmpCost = 0.0;
    double tmpSum = 0.0;
    double tmpProduct = 1.0;

    for(int i = 0; i < values.length; i++){
      tmpSum += Math.pow(values[i], 2);
      tmpProduct *= Math.cos(values[i] / Math.sqrt(i+1));
    }

    tmpCost += 1.0 + (1 / 4000) * tmpSum - tmpProduct;

    return tmpCost;
  }

  public double alpine(double... values) {
    double tmpCost = 0.0;

    for(int i = 0; i < values.length; i++){
      tmpCost += Math.abs(values[i] * Math.sin(values[i]) + 0.1 * values[i]);
    }

    return tmpCost;
  }

  public double minima(double... values) {
    double tmpCost = 0.0;

    for(int i = 0; i < values.length; i++){
      tmpCost += Math.pow(values[i], 4) - 16 * Math.pow(values[i], 2) + 5 * values[i];
    }

    return tmpCost;
  }
}
