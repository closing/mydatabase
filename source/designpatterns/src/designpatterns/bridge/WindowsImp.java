package designpatterns.bridge;

public class WindowsImp implements ImageImp {
	public void doPaint(Matrix m) {
		System.out.println("Image in Windows.");
	}
}
