package sh.miles.megumi.core.nms;

import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;

import org.bukkit.Bukkit;

public final class NMSUtils {

        private NMSUtils() {
        }

        private static final String VERSION = Bukkit.getServer().getClass().getName().split("\\.")[3];
        private static final String PACKAGE = "sh.miles.megumi.nms." + VERSION + ".";

        public static <T> T getNMSClass(String name, Class<T> type) {
                try {
                        final Class<?> clazz = Class.forName(PACKAGE + name);
                        final Object instance = clazz.getConstructor(new Class[] {}).newInstance(new Object[] {});
                        if (instance == null) {
                                throw new ClassNotFoundException();
                        }
                        return type.cast(instance);
                } catch (IllegalAccessException ae) {
                        Bukkit.getLogger().log(Level.SEVERE,
                                        String.format("An Access Error Occurred when attempting to instanciate a class of type %s",
                                                        type.getName()),
                                        ae);
                } catch (ClassNotFoundException | NoSuchMethodException | IllegalArgumentException
                                | InstantiationException nfe) {
                        Bukkit.getLogger().log(Level.SEVERE,
                                        String.format("A Class Not Found Error Occurred when attempting to instanciate a class of type %s",
                                                        type.getName()),
                                        nfe);
                } catch (SecurityException se) {
                        Bukkit.getLogger().log(Level.SEVERE,
                                        String.format(
                                                        "A Security Error Occurred when attempting to instanciate a class of type %s (Are you making sure its accessible?)",
                                                        type.getName()),
                                        se);
                } catch (InvocationTargetException ite) {
                        Bukkit.getLogger().log(Level.SEVERE,
                                        String.format(
                                                        "An Invocation Target Error Occurred when attempting to instanciate a class of type %s",
                                                        type.getName()),
                                        ite);
                }
                return null;
        }

}
