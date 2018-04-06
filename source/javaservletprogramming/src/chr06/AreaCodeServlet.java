package chr06;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AreaCodeServlet extends HttpServlet {

  Properties lookup = new Properties();

  public void init() {
    // Transfer raw data from below into a fast-lookup Properties list
    for (int i = 0; i < data.length; i++) {
      Object[] record = data[i];
      String state = (String) record[0];
      int[] codes = (int[]) record[1];
      for (int j = 0; j < codes.length; j++) {
        lookup.put(String.valueOf(codes[j]), state);
      }
    }
  }

  public void doGet(HttpServletRequest req, HttpServletResponse res)
                               throws ServletException, IOException {
    res.setContentType("text/vnd.wap.wml");
    PrintWriter out = res.getWriter();

    String msg = null;

    String code = req.getParameter("code");
    String region = null;
    if (code != null) {
      region = lookup.getProperty(code);
    }

    out.println("<?xml version=\"1.0\"?>");
    out.println("<!DOCTYPE wml PUBLIC " +
                "\"-//WAPFORUM//DTD WML 1.1//EN\" " +
                "\"http://www.wapforum.org/DTD/wml_1.1.xml\">");

    out.println("<wml>");
    out.println("<card id=\"Code\" title=\"Code\">");
    out.println("  <p>");
    out.println("  Area code '" + code + "'<br/>");
    if (region != null) {
      out.println("  is " + region + ".<br/>");
    }
    else {
      out.println("  is not valid.<br/>");
    }
    out.println("  </p>");
    out.println("</card>");
    out.println("</wml>");
  }

  // Raw area code data for each region
  private Object[][] data = new Object[][] {
    { "Toll Free", new int[] { 800, 855, 866, 877, 888 } },
    { "Alabama", new int[] { 205, 256, 334 } },
    { "Alaska", new int[] { 907 } },
    { "Alberta", new int[] { 403, 780 } },
    { "Arizona", new int[] { 480, 520, 602, 623 } },
    { "Arkansas", new int[] { 501, 870 } },
    { "British Columbia", new int[] { 250, 604 } },
    { "California", new int[] { 209, 213, 310, 323, 369, 408, 415, 424, 510,
      530, 559, 562, 619, 626, 627, 650, 661, 707, 714, 760, 805, 818, 831,
      858, 909, 916, 925, 949 } },
    { "Colorado", new int[] { 303, 719, 720, 970 } },
    { "Connecticut", new int[] { 203, 475, 860, 959 } },
    { "Deleware", new int[] { 302 } },
    { "District of Columbia", new int[] { 202 } },
    { "Florida", new int[] { 305, 321, 352, 407, 561, 727, 786, 813, 850, 863,
      904, 941, 954 } },
    { "Georgia", new int[] { 229, 404, 478, 678, 706, 770, 912 } },
    { "Hawaii", new int[] { 808 } },
    { "Idaho", new int[] { 208 } },
    { "Illinois", new int[] { 217, 224, 309, 312, 618, 630, 708, 773, 815,
      847 } },
    { "Indiana", new int[] { 219, 317, 765, 812 } },
    { "Iowa", new int[] { 319, 515, 712 } },
    { "Kansas", new int[] { 316, 785, 913 } },
    { "Kentucky", new int[] { 270, 502, 606, 859 } },
    { "Louisiana", new int[] { 225, 318, 337, 504 } },
    { "Maine", new int[] { 207 } },
    { "Manitoba", new int[] { 204 } },
    { "Maryland", new int[] { 240, 301, 410, 443 } },
    { "Massachusetts", new int[] { 413, 508, 617, 781, 978 } },
    { "Michigan", new int[] { 231, 248, 313, 517, 586, 616, 734, 810, 906 } },
    { "Minnesota", new int[] { 218, 320, 507, 612, 651, 763, 952 } },
    { "Mississippi", new int[] { 228, 601, 662 } },
    { "Missouri", new int[] { 314, 417, 573, 636, 660, 816 } },
    { "Montana", new int[] { 406 } },
    { "Nebraska", new int[] { 308, 402 } },
    { "Nevada", new int[] { 702, 775 } },
    { "New Brunswick", new int[] { 506 } },
    { "New Hampshire", new int[] { 603 } },
    { "New Jersey", new int[] { 201, 609, 732, 856, 908, 973 } },
    { "New Mexico", new int[] { 505 } },
    { "New York", new int[] { 212, 315, 347, 516, 518, 607, 631, 646, 716,
      718, 845, 914, 917 } },
    { "Newfoundland", new int[] { 709 } },
    { "North Carolina", new int[] { 252, 336, 704, 828, 910, 919, 980 } },
    { "North Dakota", new int[] { 701 } },
    { "Northwest Territories", new int[] { 867 } },
    { "Nova Scotia", new int[] { 902 } },
    { "Ohio", new int[] { 216, 234, 330, 419, 440, 513, 614, 740, 937 } },
    { "Oklahoma", new int[] { 405, 580, 918 } },
    { "Ontario", new int[] { 416, 519, 613, 647, 705, 807, 905 } },
    { "Oregon", new int[] { 503, 541, 971 } },
    { "Pennsylvania", new int[] { 215, 267, 412, 484, 570, 610, 717, 724, 814,
      878, 902 } },
    { "Puerto Rico", new int[] { 787 } },
    { "Quebec", new int[] { 418, 450, 514, 819 } },
    { "Rhode Island", new int[] { 401 } },
    { "Saskatchewan", new int[] { 306 } },
    { "South Carolina", new int[] { 803, 843, 864 } },
    { "South Dakota", new int[] { 605 } },
    { "Tennessee", new int[] { 423, 615, 865, 901, 931 } },
    { "Texas", new int[] { 210, 214, 254, 281, 361, 409, 469, 512, 682, 713,
      806, 817, 830, 832, 903, 915, 940, 956, 972 } },
    { "US Virgin Islands", new int[] { 340 } },
    { "Utah", new int[] { 435, 801 } },
    { "Vermont", new int[] { 802 } },
    { "Virginia", new int[] { 540, 571, 703, 757, 804 } },
    { "Washington", new int[] { 206, 253, 360, 425, 509, 564 } },
    { "West Virginia", new int[] { 304 } },
    { "Wyoming", new int[] { 307 } },
    { "Yukon Territory", new int[] { 867 } },
  };
}
