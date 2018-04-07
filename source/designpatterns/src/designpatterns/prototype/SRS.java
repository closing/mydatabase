package designpatterns.prototype;

public class SRS implements OfficialDocument {
	public void Display() {
		System.out.println("SRS" + this.toString());
	}

	public OfficialDocument clone() {
		OfficialDocument srs = null;
		try {
			srs = (OfficialDocument) super.clone();
		} catch (CloneNotSupportedException e) {
			System.out.println("Not support clone");
		}
		return srs;
	}
}
