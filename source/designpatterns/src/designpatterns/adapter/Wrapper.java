package designpatterns.adapter;

public class Wrapper implements Targetable {

	private Source source;

	public Wrapper(Source source) {
		this.source = source;
	}

	public void Method2() {
		System.out.println("this is the targetable method!");
	}

	public void Method1() {
		source.Method1();
	}
}
