package designpatterns.bridge;

public abstract class Image {
	protected ImageImp imp;

	public void setImageImp(ImageImp imp) {
		this.imp = imp;
	}

	public abstract void parseFile(String fileName);
}
