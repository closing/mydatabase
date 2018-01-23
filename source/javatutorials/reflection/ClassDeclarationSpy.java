import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;

import java.lang.reflect.Type;
import java.lang.reflect.Modifier;
import java.lang.reflect.TypeVariable;
import java.lang.annotation.Annotation;

import static java.lang.System.out;
public class ClassDeclarationSpy {
	public static void main(String args[]) {
		try {
			Class<?> c = Class.forName(args[0]);
			out.format("Class: %n %s%n%n", c.getCanonicalName());
			out.format("Modifiers: %n %s%n%n", Modifier.toString(c.getModifiers()));
			out.format("Type parameters: %n");
			TypeVariable[] tv = c.getTypeParameters();
			if (tv.length != 0) {
				out.format("  ");
				for (TypeVariable t : tv) {
					out.format("%s", t.getName());
				}
				out.format("%n%n");
			} else {
				out.format("  -- No Type Parameters --%n%n");
			}
			
			out.format("Implemented Interfaces:%n");
			Type[] infs = c.getGenericInterfaces();
			if (infs.length != 0) {
				for (Type t : infs) {
					out.format("  %s%n", t.toString());
				}
				out.format("%n");
			} else {
				out.format("  -- No Implemented Intefaces --%n%n");
			}
			
			out.format("Inheritance Path:%n");
			List<Class> l = new ArrayList<Class>();
			printAncestor(c, l);
			if (l.size() != 0) {
				for (Class<?> cl:l) {
					out.format("  %s%n", cl.getCanonicalName());
				}
				out.format("%n");
			} else {
				out.format("  -- No Super Classes --%n%n");
			}
			
			out.format("Annotations:%n");
			Annotation[] ann = c.getAnnotations();
			if (ann.length != 0) {
				for (Annotation a:ann) {
					out.format("  %s%n", a.toString());
				}
				out.format("%n");
			} else {
				out.format("-- No Annotations --%n%n");
			}
		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	private static void printAncestor(Class<?> c, List<Class> l) {
		Class<?> ancestor = c.getSuperclass();
		if (ancestor != null) {
			l.add(ancestor);
			printAncestor(ancestor, l);
		}
	}
}
