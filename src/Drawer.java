import com.googlecode.charts4j.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.Random;

/**
 * Creates graph links for given inputs and prints data to terminal.
 */
public class Drawer {

  /**
   * Returns a link containing expenditures by year of the given agency.
   * @param items map of years to millions spent per year.
   * @return link to chart of expenditures.
   */
  public String lineGraph(Map<String, Double> items) {
    double[] inputs = new double[items.size() + 1];
    int i = 0;
    double max = 0;
    System.out.println("Expenditure by year in Millions:");
    for (Map.Entry<String, Double> pair : items.entrySet()) {
      System.out.println(pair.getKey() + " : " + pair.getValue());
      inputs[i] = pair.getValue();
      if (inputs[i] > max) {
        max = inputs[i];
      }
      i++;
    }
    for (int j = 0; j < inputs.length; j++) {
      inputs[j] = inputs[j] * (100.0 / max);
    }
    inputs[inputs.length - 1] = inputs[inputs.length - 2];
    Data d = new Data(inputs);
    Plot plot = Plots.newPlot(d);
    LineChart chart = GCharts.newLineChart(plot);
    chart.setSize(720, 360);
    chart.setTitle("Expenditures by Year in Millions", Color.BLACK, 16);
    return chart.toURLString();
  }

  /**
   * Returns a link containing expenditures by agency for the given year.
   * @param items map of agencies to millions spent.
   * @return link to chart of expenditures.
   */
  public String barGraph(Map<String, Double> items) {
    //Plot[] plots = new Plot[items.size()];
    ArrayList<BarChartPlot> plots = new ArrayList<>();
    double max = Collections.max(items.values());
    int i = 0;
    for (Map.Entry<String, Double> pair : items.entrySet()) {
      System.out.println(pair.getKey() + " : " + pair.getValue());
      double current = pair.getValue() * (100.0 / max);
      if (current > 5) {
        plots.add(Plots.newBarChartPlot(Data.newData(current), randomColor(), pair.getKey()));
      }
      i++;
    }
    BarChart chart = GCharts.newBarChart(plots);
    chart.setSize(720, 360);
    chart.setBarWidth(30);
    chart.setTitle("Major Expenditures by Agency in Millions", Color.BLACK, 16);
    return chart.toURLString();
  }

  private Color randomColor() {
    Color[] list = {
            Color.ALICEBLUE,
            Color.BLUEVIOLET,
            Color.LAVENDER,
            Color.LIMEGREEN,
            Color.ORANGERED,
            Color.AQUA,
            Color.BISQUE,
            Color.CHOCOLATE,
            Color.GOLD
    };
    Random r = new Random();
    int value = r.nextInt(list.length);
    return list[value];
  }

  private String drawBarGraph() {
    BarChartPlot team1 = Plots.newBarChartPlot(
            Data.newData(25, 43, 12, 30), Color.BLUEVIOLET, "Sales Department");
    BarChartPlot team2 = Plots.newBarChartPlot(
            Data.newData(8, 35, 11, 5), 	Color.ORANGERED, "Marketing Department");
    BarChartPlot team3 = Plots.newBarChartPlot(
            Data.newData(10, 20, 30, 30), Color.LIMEGREEN, "Implementation Department");

    // Instantiating chart.
    BarChart chart = GCharts.newBarChart(team1, team2, team3);

    // Defining axis info and styles
    AxisStyle axisStyle = AxisStyle.newAxisStyle(Color.BLACK, 13,
            AxisTextAlignment.CENTER);
    AxisLabels score = AxisLabelsFactory.newAxisLabels("Score", 50.0);
    score.setAxisStyle(axisStyle);
    AxisLabels year = AxisLabelsFactory.newAxisLabels("Year", 50.0);
    year.setAxisStyle(axisStyle);

    // Adding axis info to chart.
    chart.addXAxisLabels(AxisLabelsFactory.newAxisLabels("2002", "2003",
            "2004", "2005"));
    chart.addYAxisLabels(AxisLabelsFactory
            .newNumericRangeAxisLabels(0, 100));
    chart.addYAxisLabels(score);
    chart.addXAxisLabels(year);

    chart.setSize(600, 450);
    chart.setBarWidth(100);
    chart.setSpaceWithinGroupsOfBars(20);
    chart.setDataStacked(true);
    chart.setTitle("Growth Rates", Color.BLACK, 16);
    chart.setGrid(100, 10, 3, 2);
    chart.setBackgroundFill(Fills.newSolidFill(Color.ALICEBLUE));
    LinearGradientFill fill = Fills.newLinearGradientFill(0,
            Color.LAVENDER, 100);
    fill.addColorAndOffset(Color.WHITE, 0);
    chart.setAreaFill(fill);

    return chart.toURLString();
  }
}
