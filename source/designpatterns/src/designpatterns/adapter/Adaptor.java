package designpatterns.adapter;

public class Adaptor extends Source implements Targetable {

	@Override
	public void Method2() {
		System.out.println("this is the targetable method");

	}

}
