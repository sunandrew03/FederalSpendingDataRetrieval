import org.junit.Test;

import static org.junit.Assert.*;

public class ControlTest {

  @Test
  public void testOperate() {
    Control.getAgency("Department of Agriculture");
    Control.getYear(2015);
    //c.getYear(2017);
  }
}