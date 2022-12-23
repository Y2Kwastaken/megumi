package sh.miles.megumi.core;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import lombok.NonNull;

/**
 * Extremely general utility class.
 * <p>
 * This class is extremely volatile and may change at any time.
 * It is encouraged to not use this class in your code / implementation.
 */
public final class Utils {

    private Utils() {
        throw new IllegalStateException("Utility class");
    }

    @NonNull
    public static <T extends Serializable> byte[] serialize(T object) {
        // serialize object
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            oos.flush();
            return baos.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new byte[0];
    }

    @SuppressWarnings("unchecked")
    public static <T extends Serializable> T deserialize(@NonNull byte[] bytes) {
        // deserialize object
        try (ByteArrayInputStream bais = new ByteArrayInputStream(bytes)) {
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (T) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
