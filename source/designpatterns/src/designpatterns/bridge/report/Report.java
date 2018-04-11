package designpatterns.bridge.report;

public abstract class Report {
	protected DataSource ds;

	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}

	public abstract void DisplayReport();

}
