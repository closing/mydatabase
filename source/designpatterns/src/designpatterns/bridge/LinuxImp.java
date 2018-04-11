package designpatterns.bridge;

public class LinuxImp implements ImageImp {
	public void doPaint(Matrix m) {
		System.out.println("Image in Linux");
	}
}
