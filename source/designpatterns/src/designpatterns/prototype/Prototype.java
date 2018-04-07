package designpatterns.prototype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class ProtoType implements Cloneable, Serializable {

	private static final long serialVersionUID = 1L;
	private String str;
	private SerializableObject obj;

	public Object clone() throws CloneNotSupportedException {
		ProtoType proto = (ProtoType) super.clone();
		return proto;
	}

	public Object deepClone() throws IOException, ClassNotFoundException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream oout = new ObjectOutputStream(out);
		oout.writeObject(this);

		ByteArrayInputStream input = new ByteArrayInputStream(out.toByteArray());
		ObjectInputStream oinput = new ObjectInputStream(input);
		return oinput.readObject();

	}

	public String getStr() {
		return str;
	}

	public SerializableObject getObj() {
		return obj;
	}

	public void setObj(SerializableObject obj) {
		this.obj = obj;
	}

	public void setStr(String str) {
		this.str = str;
	}
}
