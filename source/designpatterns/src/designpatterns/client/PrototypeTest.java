
package designpatterns.client;

import java.io.IOException;

import designpatterns.prototype.OfficialDocument;
import designpatterns.prototype.ProtoType;
import designpatterns.prototype.PrototypeManager;
import designpatterns.prototype.SerializableObject;

public class PrototypeTest {
	public static void main(String args[]) throws CloneNotSupportedException, ClassNotFoundException, IOException {
		ProtoType source = new ProtoType();
		source.setStr("source");
		source.setObj(new SerializableObject());
		System.out.println("----------------------------------------");
		System.out.println("Class ID of source: " + source.toString());
		System.out.println("--String of source: " + source.getStr());
		System.out.println("--String of obj: " + source.getObj().toString());
		System.out.println("----------------------------------------");

		ProtoType target = (ProtoType) source.clone();
		System.out.println("Clone:");
		System.out.println("Class ID of target: " + target.toString());
		System.out.println("--String of target: " + target.getStr());
		System.out.println("--String of obj: " + target.getObj().toString());
		System.out.println("----------------------------------------");

		target = (ProtoType) source.deepClone();
		System.out.println("Deep Clone:");
		System.out.println("Class ID of target: " + target.toString());
		System.out.println("--String of target: " + target.getStr());
		System.out.println("--String of obj: " + target.getObj().toString());
		System.out.println("----------------------------------------");

		// OfficialDocumentManager
		PrototypeManager pm = PrototypeManager.getPrototypeManager();
		OfficialDocument doc1, doc2, doc3, doc4, doc5, doc6;
		doc1 = pm.getOfficialDocument("far");
		doc1.Display();
		doc2 = pm.getOfficialDocument("far");
		doc2.Display();
		System.out.println(doc1 == doc2);
		doc3 = pm.getOfficialDocument("srs");
		doc3.Display();
		doc4 = pm.getOfficialDocument("srs");
		doc4.Display();
		System.out.println(doc3 == doc4);
		doc5 = pm.getOfficialDocument("ppr");
		doc5.Display();
		doc6 = pm.getOfficialDocument("ppr");
		doc6.Display();
		System.out.println(doc5 == doc6);
	}

}
