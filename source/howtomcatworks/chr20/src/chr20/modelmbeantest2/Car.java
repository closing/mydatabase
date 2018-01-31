package chr20.modelmbeantest2;

public class Car {
  private String color ="red";
  public Car() {
    System.out.println("Car constructor");
  }
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
