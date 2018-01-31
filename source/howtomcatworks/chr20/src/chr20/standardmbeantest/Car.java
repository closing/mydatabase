package chr20.standardmbeantest;

public class Car  implements CarMBean {
  private String color ="red";
  public String getColor() {
    return this.color;
  }
  public void setColor(String color) {
    this.color = color;
  }
  public void drive(){
    System.out.println("Baby you can dirve my car.");
  }
}
