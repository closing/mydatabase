package chr10.shopping;

import java.io.Serializable;

public class ProductBean implements Serializable {
  private static final long serialVersionUID = -1L;
  private String id;
  private String name;
  private String descr;
  private float price;

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescr() {
    return descr;
  }

  public float getPrice() {
    return price;
  }

  void setId(String id) {
    this.id = id;
  }

  void setName(String name) {
    this.name = name;
  }

  void setDescr(String descr) {
    this.descr = descr;
  }

  void setPrice(float price) {
    this.price = price;
  }
}
