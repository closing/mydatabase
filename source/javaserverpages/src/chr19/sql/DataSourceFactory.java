package chr19.sql;

import java.beans.PropertyDescriptor;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.BeanInfo;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Enumeration;
import javax.naming.spi.ObjectFactory;
import javax.naming.RefAddr;
import javax.naming.NamingException;
import javax.naming.Reference;
import javax.naming.Context;
import javax.naming.Name;

public class DataSourceFactory implements ObjectFactory {

    public Object getObjectInstance(Object obj, Name name, Context nameCtx, Hashtable environment)
            throws NamingException {

        Reference ref = (Reference) obj;
        RefAddr ra = ref.get("dataSourceClassName");
        if (ra == null) {
            throw new NamingException("No class name specified");
        }

        String className = (String) ra.getContent();
        Object ds = null;
        try {
            ds = Class.forName(className).newInstance();
        } catch (Exception e) {
            throw new NamingException("Can't create DataSource: " + e.getMessage());
        }

        Enumeration addrs = ref.getAll();
        while (addrs.hasMoreElements()) {
            RefAddr addr = (RefAddr) addrs.nextElement();
            String prop = addr.getType();
            String value = (String) addr.getContent();
            if (!(prop.equals("dataSourceClassName") || prop.equals("scope") || prop.equals("auth")
                    || prop.equals("factory"))) {
                setProperty(prop, value, ds);
            }
        }
        return ds;
    }

    private void setProperty(String prop, String value, Object ds) {
        Method setter = getSetter(prop, ds);
        if (setter == null) {
            System.out.println("[DataSourceFactory] Can't find property: " + prop + ". Ignored");
            return;
        }
        Object[] args = buildArgs(value, setter);
        try {
            setter.invoke(ds, args);
        } catch (Exception e) {
            System.out.println("[DataSourceFactory] Can't set property: " + prop + "=" + value + "; " + e.getMessage()
                    + ". Ignored");
        }
    }

    private Method getSetter(String prop, Object ds) {
        BeanInfo bi = null;
        try {
            bi = Introspector.getBeanInfo(ds.getClass());
        } catch (IntrospectionException e) {
            return null;
        }

        PropertyDescriptor[] pds = bi.getPropertyDescriptors();
        Method setter = null;
        for (int i = 0; i < pds.length; i++) {
            if (pds[i].getName().equals(prop)) {
                setter = pds[i].getWriteMethod();
                break;
            }
        }
        return setter;
    }

    private Object[] buildArgs(String value, Method setter) {
        Class[] paramTypes = setter.getParameterTypes();
        // Only one parameter for a setter method
        Object[] args = new Object[1];
        args[0] = coerceValue(value, paramTypes[0]);
        return args;
    }

    private Object coerceValue(String value, Class type) {
        Object coercedValue = null;
        if (type.isAssignableFrom(String.class)) {
            coercedValue = value;
        } else if (type.isAssignableFrom(Integer.class) || type.isAssignableFrom(Integer.TYPE)) {
            try {
                coercedValue = Integer.valueOf(value);
            } catch (NumberFormatException e) {
                // Ignore. It's reported when trying to set the value
            }
        } else if (type.isAssignableFrom(Boolean.class) || type.isAssignableFrom(Boolean.TYPE)) {
            coercedValue = Boolean.valueOf(value);
        }
        return coercedValue;
    }
}
