package designpatterns.bridge;

public class UnixImp implements ImageImp {
	public void doPaint(Matrix m) {
		System.out.println("Image in Unix");
	}
}
