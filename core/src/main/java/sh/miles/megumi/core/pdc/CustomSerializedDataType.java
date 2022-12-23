package sh.miles.megumi.core.pdc;

import java.io.Serializable;

import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;

import sh.miles.megumi.core.Utils;

/**
 * Provides an easy way to create a PersistentDataType that serializes
 */
public class CustomSerializedDataType<T extends Serializable> implements PersistentDataType<byte[], T> {

    private final Class<T> type;

    public CustomSerializedDataType(Class<T> type) {
        this.type = type;
    }

    @Override
    public Class<byte[]> getPrimitiveType() {
        return byte[].class;
    }

    @Override
    public Class<T> getComplexType() {
        return type;
    }

    @Override
    public byte[] toPrimitive(T complex, PersistentDataAdapterContext context) {
        return Utils.serialize(complex);
    }

    @Override
    public T fromPrimitive(byte[] primitive, PersistentDataAdapterContext context) {
        return Utils.deserialize(primitive);
    }

}
