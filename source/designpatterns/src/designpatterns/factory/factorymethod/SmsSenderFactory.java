package designpatterns.factory.factorymethod;

import designpatterns.factory.Sender;
import designpatterns.factory.SmsSender;

public class SmsSenderFactory implements FactoryMethod {
	public Sender produce() {
		return new SmsSender();
	}

}
