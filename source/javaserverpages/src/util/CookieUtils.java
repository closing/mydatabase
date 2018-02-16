package util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

  public static String getCookieValue(String name, HttpServletRequest req) {
    Cookie[] cookies = req.getCookies();
    if (cookies == null) {
      return null;
    }

    String value = null;
    for (int i = 0; i < cookies.length; i++) {
      if (cookies[i].getName().equals(name)) {
        value = cookies[i].getValue();
        break;
      }
    }
    return value;
  }

  public static void sendCookie(String name, String value, int maxAge, HttpServletResponse res) {
    Cookie cookie = new Cookie(name, value);
    cookie.setMaxAge(maxAge);
    res.addCookie(cookie);
  }

  public static boolean isCookieSet(String name, HttpServletRequest req) {
    return getCookieValue(name, req) != null;
  }
}
