package chr14;

import java.lang.Math;

public class PollBean {
  private int total;
  private int answer1;
  private int answer2;
  private int answer3;

  public void setAnswer(AnswerBean answer) {
    total++;
    String answerId = answer.getAnswerId();
    if (answerId.equals("1")) {
      answer1++;
    } else if (answerId.equals("2")) {
      answer2++;
    } else if (answerId.equals("3")) {
      answer3++;
    }
  }

  public int getTotal() {
    return total;
  }

  public int getAnswer1() {
    return answer1;
  }

  public int getAnswer2() {
    return answer2;
  }

  public int getAnswer3() {
    return answer3;
  }

  public int getAnswer1Percent() {
    return getPercent(total, answer1);
  }

  public int getAnswer2Percent() {
    return getPercent(total, answer2);
  }

  public int getAnswer3Percent() {
    return getPercent(total, answer3);
  }

  private int getPercent(int total, int answer) {
    return (int) Math.round(((double) answer / (double) total) * 100);
  }
}
