package designpatterns.singleton;

public class SynchronizedSingleton {
	private static SynchronizedSingleton instance = null;

	private SynchronizedSingleton() {
	}

	public static synchronized SynchronizedSingleton getInstance() {
		if (instance == null) {
			instance = new SynchronizedSingleton();
		}
		return instance;
	}
}
