package designpatterns.factory.abstractfactory;

import designpatterns.factory.Reciever;
import designpatterns.factory.Sender;

public interface AbstractFactory {
	public Sender CreateSender();

	public Reciever CreateReciever();
}
