package designpatterns.prototype;

public class PPR implements OfficialDocument {
	public void Display() {
		System.out.println("PPR" + this.toString());
	}

	public OfficialDocument clone() {
		OfficialDocument ppr = null;
		try {
			ppr = (OfficialDocument) super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Not support clone");
		}
		return ppr;
	}
}
