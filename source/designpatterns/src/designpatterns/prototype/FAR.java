package designpatterns.prototype;

public class FAR implements OfficialDocument {
	public void Display() {
		System.out.println("FAR" + this.toString());
	}

	public OfficialDocument clone() {
		OfficialDocument far = null;
		try {
			far = (OfficialDocument) super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Not support clone");
		}
		return far;
	}
}
