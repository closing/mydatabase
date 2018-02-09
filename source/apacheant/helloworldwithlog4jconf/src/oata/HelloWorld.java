package oata;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class HelloWorld {
	static Logger logger = LogManager.getLogger(HelloWorld.class);
	
	public static void main(String args[]) {
		logger.debug("Hello World!!");
		logger.info("Hello World!!");
		logger.error("Hello World!!");
	}
}
