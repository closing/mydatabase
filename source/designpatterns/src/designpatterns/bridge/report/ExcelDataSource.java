package designpatterns.bridge.report;

public class ExcelDataSource extends Excel implements DataSource {
	public String getSource() {
		return getExcel();
	}
}
