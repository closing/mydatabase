package mypack;

public class ShoppingCartItem {
	private Object item;
	private int quantity;
	
	public ShoppingCartItem(Object anItem) {
		this.item = anItem;
		this.quantity = 1;
	}
	
	public void incrementQuantity() {
		this.quantity++;
	}
	public void decrementQuantity() {
		this.quantity--;
	}
	public Object getItem() {
		return this.item;
	}
	public int getQuantity() {
		return this.quantity;
	}
}
