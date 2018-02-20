package common.util;

import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.util.Date;
import java.util.StringTokenizer;
import java.text.ParseException;

public class StringFormat {
  private static SimpleDateFormat dateFormat = new SimpleDateFormat();
  private static DecimalFormat numberFormat = new DecimalFormat();

  public static boolean isValidDate(String dateString, String dateFormatPattern) {
    Date validDate = null;
    synchronized (dateFormat) {
      try {
        dateFormat.applyPattern(dateFormatPattern);
        dateFormat.setLenient(false);
        validDate = dateFormat.parse(dateString);
      } catch (ParseException e) {
        // Ignore and return null
      }
    }
    return validDate != null;
  }

  public static boolean isValidInteger(String numberString, int min, int max) {
    Integer validInteger = null;
    try {
      Number aNumber = numberFormat.parse(numberString);
      int anInt = aNumber.intValue();
      if (anInt >= min && anInt <= max) {
        validInteger = new Integer(anInt);
      }
    } catch (ParseException e) {
      // Ignore and return null
    }
    return validInteger != null;
  }

  public static boolean isValidEmailAddr(String mailAddr) {
    if (mailAddr == null) {
      return false;
    }

    boolean isValid = true;
    mailAddr = mailAddr.trim();

    // Check at-sign and white-space usage
    int atSign = mailAddr.indexOf('@');
    if (atSign == -1 || atSign == 0 || atSign == mailAddr.length() - 1 || mailAddr.indexOf('@', atSign + 1) != -1
        || mailAddr.indexOf(' ') != -1 || mailAddr.indexOf('\t') != -1 || mailAddr.indexOf('\n') != -1
        || mailAddr.indexOf('\r') != -1) {
      isValid = false;
    }
    // Check dot usage
    if (isValid) {
      mailAddr = mailAddr.substring(atSign + 1);
      int dot = mailAddr.indexOf('.');
      if (dot == -1 || dot == 0 || dot == mailAddr.length() - 1) {
        isValid = false;
      }
    }
    return isValid;
  }

  public static boolean isValidString(String value, String[] validStrings, boolean ignoreCase) {
    boolean isValid = false;
    for (int i = 0; validStrings != null && i < validStrings.length; i++) {
      if (ignoreCase) {
        if (validStrings[i].equalsIgnoreCase(value)) {
          isValid = true;
          break;
        }
      } else {
        if (validStrings[i].equals(value)) {
          isValid = true;
          break;
        }
      }
    }
    return isValid;
  }

  public static boolean isValidString(String[] values, String[] validStrings, boolean ignoreCase) {

    if (values == null) {
      return false;
    }
    boolean isValid = true;
    for (int i = 0; values != null && i < values.length; i++) {
      if (!isValidString(values[i], validStrings, ignoreCase)) {
        isValid = false;
        break;
      }
    }
    return isValid;
  }

  public static String toHTMLString(String in) {
    StringBuffer out = new StringBuffer();
    for (int i = 0; in != null && i < in.length(); i++) {
      char c = in.charAt(i);
      if (c == '\'') {
        out.append("&#039;");
      } else if (c == '\"') {
        out.append("&#034;");
      } else if (c == '<') {
        out.append("&lt;");
      } else if (c == '>') {
        out.append("&gt;");
      } else if (c == '&') {
        out.append("&amp;");
      } else {
        out.append(c);
      }
    }
    return out.toString();
  }

  public static Date toDate(String dateString, String dateFormatPattern) throws ParseException {
    Date date = null;
    if (dateFormatPattern == null) {
      dateFormatPattern = "yyyy-MM-dd";
    }
    synchronized (dateFormat) {
      dateFormat.applyPattern(dateFormatPattern);
      dateFormat.setLenient(false);
      date = dateFormat.parse(dateString);
    }
    return date;
  }

  public static Number toNumber(String numString, String numFormatPattern) throws ParseException {
    Number number = null;
    if (numFormatPattern == null) {
      numFormatPattern = "######.##";
    }
    synchronized (numberFormat) {
      numberFormat.applyPattern(numFormatPattern);
      number = numberFormat.parse(numString);
    }
    return number;
  }

  public static String replaceInString(String in, String from, String to) {
    if (in == null || from == null || to == null) {
      return in;
    }

    StringBuffer newValue = new StringBuffer();
    char[] inChars = in.toCharArray();
    int inLen = inChars.length;
    char[] fromChars = from.toCharArray();
    int fromLen = fromChars.length;

    for (int i = 0; i < inLen; i++) {
      if (inChars[i] == fromChars[0] && (i + fromLen) <= inLen) {
        boolean isEqual = true;
        for (int j = 1; j < fromLen; j++) {
          if (inChars[i + j] != fromChars[j]) {
            isEqual = false;
            break;
          }
        }
        if (isEqual) {
          newValue.append(to);
          i += fromLen - 1;
        } else {
          newValue.append(inChars[i]);
        }
      } else {
        newValue.append(inChars[i]);
      }
    }
    return newValue.toString();
  }

  public static String toContextRelativeURI(String relURI, String currURI) throws IllegalArgumentException {

    if (relURI.startsWith("/")) {
      // Must already be context-relative
      return relURI;
    }

    String origRelURI = relURI;
    if (relURI.startsWith("./")) {
      // Remove current dir characters
      relURI = relURI.substring(2);
    }

    String currDir = currURI.substring(0, currURI.lastIndexOf("/") + 1);
    StringTokenizer currLevels = new StringTokenizer(currDir, "/");

    // Remove and count all parent dir characters
    int removeLevels = 0;
    while (relURI.startsWith("../")) {
      if (relURI.length() < 4) {
        throw new IllegalArgumentException("Invalid relative URI: " + origRelURI);
      }
      relURI = relURI.substring(3);
      removeLevels++;
    }

    if (removeLevels > currLevels.countTokens()) {
      throw new IllegalArgumentException("Invalid relative URI: " + origRelURI + " points outside the context");
    }
    int keepLevels = currLevels.countTokens() - removeLevels;
    StringBuffer newURI = new StringBuffer("/");
    for (int j = 0; j < keepLevels; j++) {
      newURI.append(currLevels.nextToken()).append("/");
    }
    return newURI.append(relURI).toString();
  }
}
