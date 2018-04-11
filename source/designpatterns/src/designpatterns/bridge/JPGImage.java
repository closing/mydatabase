package designpatterns.bridge;

public class JPGImage extends Image {
	public void parseFile(String fileName) {
		Matrix m = new Matrix();
		imp.doPaint(m);
		System.out.println(fileName + ".JPG");
	}
}
