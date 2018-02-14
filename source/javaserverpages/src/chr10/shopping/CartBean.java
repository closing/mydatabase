package chr10.shopping;

import java.io.Serializable;
import java.util.Vector;
import java.util.Iterator;

public class CartBean implements Serializable {
  private static final long serialVersionUID = -1L;
  private Vector cart = new Vector();

  public void setProduct(ProductBean product) {
    if (product != null && cart.indexOf(product) == -1) {
      cart.addElement(product);
    }
  }

  public ProductBean[] getProductList() {
    ProductBean[] products = null;
    synchronized (cart) {
      products = new ProductBean[cart.size()];
      cart.toArray(products);
    }
    return products;
  }

  public float getTotal() {
    float total = 0;
    Iterator prods = cart.iterator();
    while (prods.hasNext()) {
      ProductBean product = (ProductBean) prods.next();
      float price = product.getPrice();
      total += price;
    }
    return total;
  }
}
