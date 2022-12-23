package sh.miles.megumi.core.pdc;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

/**
 * Provides an easy way to create a PersistentDataType that serializes into a
 * string
 * 
 * @implNote
 *           This class uses reflection to call the non-static method
 *           fromString(String) on the class being serialized
 *           if this method does not exist, or is not public, it will throw an
 *           illegal access exception
 */
public class CustomStringDataType<T> implements PersistentDataType<String, T> {

    private final Class<T> clazz;

    public CustomStringDataType(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public Class<String> getPrimitiveType() {
        return String.class;
    }

    @Override
    public Class<T> getComplexType() {
        return this.clazz;
    }

    @Override
    public String toPrimitive(T complex, PersistentDataAdapterContext context) {
        return complex.toString();
    }

    @Override
    @SuppressWarnings("unchecked")
    public T fromPrimitive(String primitive, PersistentDataAdapterContext context) {
        Method method;
        try {
            method = clazz.getMethod("fromString", String.class);
            return (T) method.invoke(null, primitive);
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
                | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

}
