package sh.miles.megumi.menu;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.bukkit.entity.Player;

import lombok.Getter;
import lombok.NonNull;

public class MenuSession {

    private static final Map<UUID, MenuSession> sessions = new HashMap<>();

    @Getter
    private final Menu menu;

    private MenuSession(@NonNull final Menu menu) {
        this.menu = menu;
        menu.init();
    }

    private MenuSession(@NonNull final Menu menu, @NonNull final Player player) {
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

    public static void start(@NonNull final Menu menu, @NonNull final Player... players) {
        Arrays.stream(players).forEach(player -> {
            menu.setViewer(player);
            new MenuSession(menu, player);
        });
    }

    public static void end(@NonNull final Player player) {
        sessions.remove(player.getUniqueId());
    }

    public static Optional<MenuSession> get(@NonNull final Player player) {
        return Optional.ofNullable(sessions.get(player.getUniqueId()));
    }
}
