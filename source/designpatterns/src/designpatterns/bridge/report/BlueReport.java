package designpatterns.bridge.report;

public class BlueReport extends Report {
	public void DisplayReport() {
		System.out.println("Blue:" + this.ds.getSource());
	}
}
