package Hard.Splitwise.src.manager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Hard.Splitwise.src.domain.User;

public class UserManager {

    private final Map<String, User> users;

    private static class Singleton {
        private static final UserManager INSTANCE = new UserManager();
    }

    private UserManager() {
        users = new ConcurrentHashMap<>();
    }

    public static UserManager getInstance() {
        return Singleton.INSTANCE;
    }

    public void register(User user) {
        users.put(user.getEmail(), user);
    }

    public User deregister(String email) {
        return users.remove(email);
    }

    public List<User> getAll() {
        return List.copyOf(users.values());
    }

    public User getByEmail(String email) {
        return users.get(email);
    }

    @Override
    public String toString() {
        return "UserManager [users=" + users + "]";
    }

}
