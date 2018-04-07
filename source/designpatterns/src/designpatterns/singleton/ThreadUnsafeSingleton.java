package designpatterns.singleton;

public class ThreadUnsafeSingleton {
	private static ThreadUnsafeSingleton instance = null;

	private ThreadUnsafeSingleton() {
	}

	public static ThreadUnsafeSingleton getInstance() {
		if (instance == null) {
			synchronized (ThreadUnsafeSingleton.class) {
				if (instance == null) {
					instance = new ThreadUnsafeSingleton();
				}
			}
		}
		return instance;
	}
}
