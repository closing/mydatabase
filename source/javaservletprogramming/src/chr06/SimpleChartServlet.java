package chr06;

import java.awt.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import Acme.JPM.Encoders.GifEncoder;

import javachart.chart.*;  // from Visual Engineering

public class SimpleChartServlet extends HttpServlet {

  static final int WIDTH = 450;
  static final int HEIGHT = 320;

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException ,IOException {
    ServletOutputStream out = res.getOutputStream();

    Frame frame = null;
    Graphics g = null;

    try {
      // Create a simple chart
      BarChart chart = new BarChart("Apples and Oranges");

      // Give it a title
      chart.getBackground().setTitleFont(new Font("Serif", Font.PLAIN, 24));
      chart.getBackground().setTitleString("Comparing Apples and Oranges");

      // Show, place, and customize its legend
      chart.setLegendVisible(true);
      chart.getLegend().setLlX(0.4);  // normalized from lower left
      chart.getLegend().setLlY(0.75); // normalized from lower left
      chart.getLegend().setIconHeight(0.04);
      chart.getLegend().setIconWidth(0.04);
      chart.getLegend().setIconGap(0.02);
      chart.getLegend().setVerticalLayout(false);

      // Give it its data and labels
      double[] appleData = {950, 1005, 1210, 1165, 1255};
      chart.addDataSet("Apples", appleData);

      double[] orangeData = {1435, 1650, 1555, 1440, 1595};
      chart.addDataSet("Oranges", orangeData);

      String[] labels = {"1993", "1994", "1995", "1996", "1997"};
      chart.getXAxis().addLabels(labels);

      // Color apples red and oranges orange
      chart.getDatasets()[0].getGc().setFillColor(Color.red);
      chart.getDatasets()[1].getGc().setFillColor(Color.orange);

      // Name the axes
      chart.getXAxis().setTitleString("Year");
      chart.getYAxis().setTitleString("Tons Consumed");

      // Size it appropriately
      chart.resize(WIDTH, HEIGHT);

      // Create an unshown frame
      frame = new Frame();
      frame.addNotify();

      // Get a graphics region of appropriate size, using the Frame
      Image image = frame.createImage(WIDTH, HEIGHT);
      g = image.getGraphics();

      // Ask the chart to draw itself to the off screen graphics context
      chart.drawGraph(g);

      // Encode and return what it painted
      res.setContentType("image/gif");
      GifEncoder encoder = new GifEncoder(image, out);
      encoder.encode();
    }
    finally {
      // Clean up resources
      if (g != null) g.dispose();
      if (frame != null) frame.removeNotify();
    }
  }
}
