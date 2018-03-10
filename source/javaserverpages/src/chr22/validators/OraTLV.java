package chr22.validators;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.jsp.tagext.TagLibraryValidator;
import javax.servlet.jsp.tagext.ValidationMessage;
import javax.servlet.jsp.tagext.PageData;
import org.jdom.Element;
import org.jdom.Document;
import org.jdom.Namespace;
import org.jdom.input.SAXBuilder;

public class OraTLV extends TagLibraryValidator {
	private SAXBuilder builder = new SAXBuilder();
	private Namespace jspNamespace = Namespace.getNamespace("http://java.sun.com/JSP/Page");

	public ValidationMessage[] validate(String prefix, String uri, PageData pd) {

		ValidationMessage[] vms = null;
		ArrayList msgs = new ArrayList();
		Namespace taglibNamespace = Namespace.getNamespace(uri);
		try {
			Document doc = builder.build(pd.getInputStream());
			Element root = doc.getRootElement();
			validateElement(root, taglibNamespace, msgs);
		} catch (Exception e) {
			vms = new ValidationMessage[1];
			vms[0] = new ValidationMessage(null, e.getMessage());
		}

		if (msgs.size() != 0) {
			vms = new ValidationMessage[msgs.size()];
			msgs.toArray(vms);
		}
		return vms;
	}

	private void validateElement(Element e, Namespace ns, ArrayList msgs) {
		if (ns.equals(e.getNamespace())) {
			if (e.getName().equals("child")) {
				validateChild(e, ns, msgs);
			}
		}
		if (e.hasChildren()) {
			List kids = e.getChildren();
			Iterator i = kids.iterator();
			while (i.hasNext()) {
				validateElement((Element) i.next(), ns, msgs);
			}
		}
	}

	private void validateChild(Element e, Namespace ns, ArrayList msgs) {
		Element parent = findParent(e, ns, "parent");
		if (parent == null) {
			String id = e.getAttributeValue("id", jspNamespace);
			ValidationMessage vm = new ValidationMessage(id, e.getQualifiedName() + " must only be used with 'parent'");
			msgs.add(vm);
		}
	}

	private Element findParent(Element e, Namespace ns, String name) {
		if (e.getName().equals(name) && ns.equals(e.getNamespace())) {
			return e;
		}
		Element parent = e.getParent();
		if (parent != null) {
			return findParent(parent, ns, name);
		}
		return null;
	}
}
