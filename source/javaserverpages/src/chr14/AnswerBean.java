package chr14;

public class AnswerBean {
  static private String VALID_IDS = "1 2 3";
  private String answerId;

  public String getAnswerId() {
    return answerId;
  }

  public void setAnswerId(String answerId) {
    this.answerId = answerId;
  }

  public boolean isValid() {
    boolean isValid = false;
    if (answerId != null) {
      isValid = VALID_IDS.indexOf(answerId) != -1;
    }
    return isValid;
  }
}
