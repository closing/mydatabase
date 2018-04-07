package designpatterns.prototype;

public interface OfficialDocument extends Cloneable {
	void Display();

	OfficialDocument clone();
}
