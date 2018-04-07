package designpatterns.prototype;

import java.util.Hashtable;

public class PrototypeManager {
	private Hashtable<String, OfficialDocument> ht = new Hashtable<>();
	private static PrototypeManager pm = new PrototypeManager();

	private PrototypeManager() {
		ht.put("far", new FAR());
		ht.put("srs", new SRS());
		ht.put("ppr", new PPR());
	}

	public void addOfficialDocument(String key, OfficialDocument doc) {
		ht.put(key, doc);
	}

	public OfficialDocument getOfficialDocument(String key) {
		return ((OfficialDocument) ht.get(key)).clone();
	}

	public static PrototypeManager getPrototypeManager() {
		return pm;
	}

}
