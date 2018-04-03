package designpatterns.client;

import java.io.IOException;

import designpatterns.prototype.Prototype;
import designpatterns.prototype.SerializableObject;

public class PrototypeTest {
	public static void main(String args[]) throws CloneNotSupportedException, ClassNotFoundException, IOException {
		Prototype source = new Prototype();
		source.setString("source");
		source.setObj(new SerializableObject());
		System.out.println("----------------------------------------");
		System.out.println("Class ID of source: " + source.toString());
		System.out.println("--String of source: " + source.getString());
		System.out.println("--String of obj: " + source.getObj().toString());
		System.out.println("----------------------------------------");

		Prototype target = (Prototype) source.clone();
		System.out.println("Clone:");
		System.out.println("Class ID of target: " + target.toString());
		System.out.println("--String of target: " + target.getString());
		System.out.println("--String of obj: " + target.getObj().toString());
		System.out.println("----------------------------------------");

		target = (Prototype) source.deepClone();
		System.out.println("Deep Clone:");
		System.out.println("Class ID of target: " + target.toString());
		System.out.println("--String of target: " + target.getString());
		System.out.println("--String of obj: " + target.getObj().toString());
		System.out.println("----------------------------------------");
	}

}
