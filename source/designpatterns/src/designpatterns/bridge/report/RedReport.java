package designpatterns.bridge.report;

public class RedReport extends Report {
	public void DisplayReport() {
		System.out.println("Red:" + this.ds.getSource());
	}
}
