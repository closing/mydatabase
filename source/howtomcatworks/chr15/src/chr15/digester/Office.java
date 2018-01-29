package chr15.digester;

public class Office {
  private Address address;
  private String description;
  public Office(){
    System.out.println("..Creating Office");
  }
  public Address getAddress() {
    return this.address;
  }
  public String getDescription()  {
    return this.description;
  }
  public void setAddress(Address address){
    System.out.println("..Setting office address: " + address);
    this.address=address;
  }
  public void setDescription(String description){    
    System.out.println("..Setting office description");
    this.description = description;
  }
}
