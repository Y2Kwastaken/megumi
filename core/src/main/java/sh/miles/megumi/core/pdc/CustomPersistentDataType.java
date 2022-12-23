package sh.miles.megumi.core.pdc;

import java.util.UUID;

import org.bukkit.persistence.PersistentDataType;

import sh.miles.megumi.core.world.LightLocation;

/**
 * CustomPersistentDataType is a utility class that houses custom
 * PersistentDataTypes you can make use of your own CustomPersistentDataTypes
 * created using other classes in this package.
 */
public final class CustomPersistentDataType {

    public static final PersistentDataType<byte[], LightLocation> LIGHT_LOCATION = new CustomSerializedDataType<>(
            LightLocation.class);
    public static final PersistentDataType<String, UUID> UUID = new CustomStringDataType<>(UUID.class);

    private CustomPersistentDataType() {
        throw new IllegalStateException("Utility class");
    }
}
