package chr20.modelmbeantest;

public class Car {
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
