import org.junit.Test;

import static org.junit.Assert.*;

public class GraphsTest {

  @Test
  public void testExample() {
    Drawer d = new Drawer();
    System.out.println(d.lineGraph(Control.getAgency("Department of Agriculture")));
    System.out.println(d.barGraph(Control.getYear(2015)));
  }

}