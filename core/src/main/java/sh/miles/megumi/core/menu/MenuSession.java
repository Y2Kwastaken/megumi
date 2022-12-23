package sh.miles.megumi.core.menu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.NonNull;

/**
 * MenuSession is a representation of a menu session
 * <p>
 * A menu session is a session of a menu that is opened by a player.
 * any time a player opens a menu a new session is created and stored in the
 * {@link #sessions} map.
 * <p>
 * These sessions should be used to handle the menu's actions and to close the
 * menu
 * <p>
 * You can get a session by using {@link #start(AbstractMenu, Player...)}
 * <p>
 * You can end a session by using {@link #end(Player)}
 * <p>
 */
public class MenuSession {

    private static final Map<UUID, MenuSession> sessions = new HashMap<>();

    @Getter
    private final AbstractMenu menu;

    // Internal method calls do make sense when starting a new session
    @SuppressWarnings("all")
    private MenuSession(@NonNull final AbstractMenu menu, @NonNull final Player player) {
        this.menu = menu;
        // init
        menu.onStart();
        menu.init();
        display(player);
        // put session
        sessions.put(player.getUniqueId(), this);
    }

    public void display(@NonNull final Player player) {
        player.openInventory(this.menu.getInventory());
    }

    public static void start(@NonNull final AbstractMenu menu, @NonNull final Player... players) {
        Arrays.stream(players).forEach((Player player) -> {
            menu.setViewer(player);
            new MenuSession(menu, player);
        });
    }

    public static void end(@NonNull final HumanEntity player) {
        sessions.remove(player.getUniqueId());
        player.closeInventory();

    }

    public static Optional<MenuSession> get(@NonNull final Player player) {
        return Optional.ofNullable(sessions.get(player.getUniqueId()));
    }
}
