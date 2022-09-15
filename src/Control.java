import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.net.*;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

/**
 * Class for accessing federal spending API data, parses JSON into simpler data form.
 */
public class Control {
  public void operate() {
    Scanner in = new Scanner(System.in);
    int year = in.nextInt();
    Map<String, Double> values = getYear(year);
    System.out.println(values);
  }

  /**
   * Gets expenditures per year of the given agency.
   * @param name of agency to be retrieved
   * @return expenditures per year of this agency
   */
  public static Map getAgency(String name) {
    String fields = "?fields=net_cost_bil_amt,restmt_flag,stmt_fiscal_year";
    String filter = "&filter=agency_nm:eq:";
    String urlName = name.replace(" ", "%20");

    return get(urlName, fields, filter, "stmt_fiscal_year");
  }

  /**
   * Gets expenditures by agency of the given year.
   * @param year to get data for
   * @return expenditures by agency of this year.
   */
  public static Map getYear(int year) {
    String fields = "?fields=agency_nm,net_cost_bil_amt,restmt_flag";
    String filter = "&filter=record_fiscal_year:eq:";

    return get(String.valueOf(year), fields, filter, "agency_nm");
  }

  private static Map get(String target, String fields, String filter, String flag) {

    String base = "https://api.fiscaldata.treasury.gov/services/api/fiscal_service";
    String endpoint = "/v2/accounting/od/statement_net_cost";

    Map<String, Double> expenditures = null;

    try {
      //Connection
      URL url = new URL(base + endpoint + fields + filter + target);
      HttpURLConnection conn = (HttpURLConnection)url.openConnection();
      conn.setRequestMethod("GET");
      conn.connect();
      int responsecode = conn.getResponseCode();
      if (responsecode != 200) {
        //bad response
        System.out.println(responsecode);
      }

      //Output data to string
      Scanner sc = new Scanner(url.openStream());
      StringBuilder text = new StringBuilder();
      while(sc.hasNext())
      {
        text.append(sc.nextLine());
      }
      sc.close();

      //Parse string
      JSONParser parse = new JSONParser();
      JSONObject jobj = (JSONObject)parse.parse(text.toString());
      JSONArray a1 = (JSONArray) jobj.get("data");

      expenditures = new TreeMap<>();

      for (int i = 0; i < a1.size(); i++) {
        JSONObject ob = (JSONObject)a1.get(i);
        if (ob.get("restmt_flag").equals("Y")) {
          String name = (String) ob.get(flag);
          if (! ob.get("net_cost_bil_amt").equals("null")) {
            Double cost = Double.valueOf((String) ob.get("net_cost_bil_amt"));
            expenditures.put(name, cost);
          }
        }
      }

    } catch (MalformedURLException me) {
      //issue with URL
    } catch (IOException e) {
      //issue with http connection
    } catch (ParseException e) {
      throw new RuntimeException(e);
    }
    expenditures.remove("Total");
    return expenditures;
  }


}
