import java.util.Scanner;

/**
 * Runs this program.
 */
public class Main {
  /**
   * Main method to run this program.
   * @param args
   */
  public static void main(String[] args) {
    Scanner scan = new Scanner(System.in);
    System.out.println("Type in the name of a valid agency or year to receive spending data for the " +
            "specified input.");
    while (true) {
      String in = scan.nextLine();
      Drawer d = new Drawer();
      if (in.matches("\\d+")) {
        try {
          System.out.println(d.barGraph(Control.getYear(Integer.parseInt(in))));
        } catch (Exception e) {

        }
      } else {
        try {
          System.out.println(d.lineGraph(Control.getAgency(in)));
        } catch (Exception e) {

        }
      }
      if (in.equalsIgnoreCase("q")) {
        System.exit(1);
      }
    }
  }
}
