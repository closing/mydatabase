package designpatterns.bridge;

public class PNGImage extends Image {
	public void parseFile(String fileName) {
		Matrix m = new Matrix();
		this.imp.doPaint(m);
		System.out.println(fileName + ".PNG");
	}
}
