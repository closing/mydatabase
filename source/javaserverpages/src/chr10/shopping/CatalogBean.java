package chr10.shopping;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;

public class CatalogBean implements Serializable {
  private static final long serialVersionUID = -1L;
  private Map catalog = new HashMap();

  public CatalogBean() {
    ProductBean prod = new ProductBean();
    prod.setId("1000");
    prod.setName("JavaServer Pages");
    prod.setDescr("Learn how to develop a JSP based web application.");
    prod.setPrice(32.95f);
    catalog.put("1000", prod);

    prod = new ProductBean();
    prod.setId("2000");
    prod.setName("Java Servlet Programming");
    prod.setDescr("Learn how to develop a servlet based web application.");
    prod.setPrice(32.95f);
    catalog.put("2000", prod);

    prod = new ProductBean();
    prod.setId("3000");
    prod.setName("Java In a Nutshell");
    prod.setDescr("Learn the Java programming language.");
    prod.setPrice(32.95f);
    catalog.put("3000", prod);
  }

  public ProductBean[] getProductList() {
    ProductBean[] products = new ProductBean[catalog.size()];
    catalog.values().toArray(products);
    return products;
  }

  public Map getProductsById() {
    return catalog;
  }
}
