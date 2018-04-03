package designpatterns.factory;

public class MailReciever implements Reciever {
	@Override
	public void Recieve() {
		System.out.println("This is mail reciever");
	}
}
