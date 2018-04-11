package designpatterns.bridge.report;

public class DbDataSource implements DataSource {
	public String getSource() {
		return "DataBase";
	}
}
