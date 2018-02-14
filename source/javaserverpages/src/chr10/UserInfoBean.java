package chr10;

import util.StringFormat;
import java.io.Serializable;

public class UserInfoBean implements Serializable {
  // Validation constants
  private static String DATE_FORMAT_PATTERN = "yyyy-MM-dd";
  private static String[] GENDER_LIST = { "m", "f" };
  private static String[] FOOD_LIST = { "z", "p", "c" };
  private static int MIN_LUCKY_NUMBER = 1;
  private static int MAX_LUCKY_NUMBER = 100;

  // Properties
  private String birthDate;
  private String emailAddr;
  private String[] food;
  private String luckyNumber;
  private String gender;
  private String userName;

  public String getBirthDate() {
    return (birthDate == null ? "" : birthDate);
  }

  public void setBirthDate(String birthDate) {
    this.birthDate = birthDate;
  }

  public boolean isBirthDateValid() {
    boolean isValid = false;
    if (birthDate != null && StringFormat.isValidDate(birthDate, DATE_FORMAT_PATTERN)) {
      isValid = true;
    }
    return isValid;
  }

  public String getEmailAddr() {
    return (emailAddr == null ? "" : emailAddr);
  }

  public void setEmailAddr(String emailAddr) {
    this.emailAddr = emailAddr;
  }

  public boolean isEmailAddrValid() {
    boolean isValid = false;
    if (emailAddr != null && StringFormat.isValidEmailAddr(emailAddr)) {
      isValid = true;
    }
    return isValid;
  }

  public String[] getFood() {
    return (food == null ? new String[0] : food);
  }

  public void setFood(String[] food) {
    this.food = food;
  }

  public boolean isFoodValid() {
    boolean isValid = false;
    if (food == null || StringFormat.isValidString(food, FOOD_LIST, true)) {
      isValid = true;
    }
    return isValid;
  }

  public boolean isPizzaSelected() {
    return isFoodTypeSelected("z");
  }

  public boolean isPastaSelected() {
    return isFoodTypeSelected("p");
  }

  public boolean isChineseSelected() {
    return isFoodTypeSelected("c");
  }

  public String getLuckyNumber() {
    return (luckyNumber == null ? "" : luckyNumber);
  }

  public void setLuckyNumber(String luckyNumber) {
    this.luckyNumber = luckyNumber;
  }

  public boolean isLuckyNumberValid() {
    boolean isValid = false;
    if (luckyNumber != null && StringFormat.isValidInteger(luckyNumber, MIN_LUCKY_NUMBER, MAX_LUCKY_NUMBER)) {
      isValid = true;
    }
    return isValid;
  }

  public String getGender() {
    return (gender == null ? "" : gender);
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public boolean isGenderValid() {
    boolean isValid = false;
    if (gender != null && StringFormat.isValidString(gender, GENDER_LIST, true)) {
      isValid = true;
    }
    return isValid;
  }

  public String getUserName() {
    return (userName == null ? "" : userName);
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public boolean isUserNameValid() {
    boolean isValid = false;
    if (userName != null) {
      isValid = true;
    }
    return isValid;
  }

  public boolean isValid() {
    return isBirthDateValid() && isEmailAddrValid() && isFoodValid() && isLuckyNumberValid() && isGenderValid()
        && isUserNameValid();
  }

  private boolean isFoodTypeSelected(String foodType) {
    if (food == null) {
      return false;
    }
    boolean selected = false;
    for (int i = 0; i < food.length; i++) {
      if (food[i].equals(foodType)) {
        selected = true;
        break;
      }
    }
    return selected;
  }
}
