package chr09;

import java.math.BigDecimal;
import java.io.Serializable;

public class CalcBean implements Serializable {
  private static final long serialVersionUID = -1;

  private static final int NO_OPER = -1;
  private static final int ADD_OPER = 1;
  private static final int SUB_OPER = 2;
  private static final int DIV_OPER = 3;
  private static final int MULT_OPER = 4;

  private String newDigit = "";
  private String currentNumber = "";
  private String previousNumber = "";
  private int newOper = NO_OPER;
  private int currentOperation = NO_OPER;
  private boolean reset = false;
  private boolean operClicked = false;
  private boolean clearClicked = false;
  private boolean dotClicked = false;

  public void setDigit(String value) {
    newDigit = value.trim();
  }

  public void setDot(String value) {
    dotClicked = true;
  }

  public void setOper(String value) {
    newOper = toOperValue(value.trim());
    operClicked = true;
  }

  public void setClear(String value) {
    clearClicked = true;
  }

  public void setCurrentNumber(String value) {
    currentNumber = value;
  }

  public String getCurrentNumber() {
    calcNewNumbers();
    return currentNumber + newDigit;
  }

  public void setCurrentOperation(String value) {
    currentOperation = toOperValue(value);
  }

  public String getCurrentOperation() {
    return toOperName(currentOperation);
  }

  public void setPreviousNumber(String value) {
    previousNumber = value;
  }

  public String getPreviousNumber() {
    return previousNumber;
  }

  public void setReset(boolean value) {
    reset = value;
  }

  public boolean getReset() {
    return reset;
  }

  private void calcNewNumbers() {
    if (dotClicked) {
      if (currentNumber.length() == 0) {
        currentNumber = "0.";
      } else if (currentNumber.indexOf(".") == -1) {
        currentNumber += ".";
      }
      dotClicked = false;
      reset = false;
    } else if (operClicked) {
      if (previousNumber.length() > 0 && currentNumber.length() > 0) {
        BigDecimal firstNumber = null;
        if (previousNumber.indexOf(".") == -1) {
          // Make it decimal to get real division
          firstNumber = new BigDecimal(previousNumber + ".0");
        } else {
          firstNumber = new BigDecimal(previousNumber);
        }
        BigDecimal secondNumber = new BigDecimal(currentNumber);
        BigDecimal result = null;
        switch (currentOperation) {
        case ADD_OPER:
          result = firstNumber.add(secondNumber);
          break;
        case SUB_OPER:
          result = firstNumber.subtract(secondNumber);
          break;
        case DIV_OPER:
          result = firstNumber.divide(secondNumber, BigDecimal.ROUND_HALF_UP);
          break;
        case MULT_OPER:
          result = firstNumber.multiply(secondNumber);
          break;
        }
        if (result != null) {
          currentNumber = result.toString();
        }
      }
      previousNumber = currentNumber;
      currentOperation = newOper;
      reset = true;
    } else if (clearClicked) {
      currentNumber = "";
      previousNumber = currentNumber;
      currentOperation = NO_OPER;
    } else if (reset) {
      currentNumber = "";
      reset = false;
    }
  }

  private String toOperName(int value) {
    String name = "";
    switch (value) {
    case ADD_OPER:
      name = "+";
      break;
    case SUB_OPER:
      name = "-";
      break;
    case DIV_OPER:
      name = "/";
      break;
    case MULT_OPER:
      name = "*";
    }
    return name;
  }

  private int toOperValue(String name) {
    int value = NO_OPER;
    if ("+".equals(name)) {
      value = ADD_OPER;
    } else if ("-".equals(name)) {
      value = SUB_OPER;
    } else if ("/".equals(name)) {
      value = DIV_OPER;
    } else if ("*".equals(name)) {
      value = MULT_OPER;
    }
    return value;
  }
}
