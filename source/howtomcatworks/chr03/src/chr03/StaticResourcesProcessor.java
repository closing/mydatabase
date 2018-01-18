package chr03;

import java.io.IOException;
import chr03.connector.http.HttpRequest;
import chr03.connector.http.HttpResponse;

public class StaticResourcesProcessor {
	public void process(HttpRequest request, HttpResponse response) {
		try {
			response.sendStaticResource();
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}
} 
