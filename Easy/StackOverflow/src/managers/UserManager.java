package Easy.StackOverflow.src.managers;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Easy.StackOverflow.src.domain.User;
import Easy.StackOverflow.src.exceptions.DuplicateResourceException;
import Easy.StackOverflow.src.exceptions.ResourceNotFoundException;

public class UserManager {

    private final Map<String, User> users;

    private static class SingletonExtracter {
        private static final UserManager INSTANCE = new UserManager();
    }

    private UserManager() {
        this.users = new ConcurrentHashMap<>();
    }

    public static UserManager getInstance() {
        return SingletonExtracter.INSTANCE;
    }

    public void registerUser(User user) throws DuplicateResourceException {
        if (users.containsKey(user.getEmail())) {
            throw new DuplicateResourceException("The user with given email already exists");
        }
        users.put(user.getEmail(), user);
    }

    public User removeUser(String email) throws ResourceNotFoundException {
        if (!users.containsKey(email)) {
            throw new ResourceNotFoundException(
                    "The user with given email: " + email + " not found");
        }
        return users.remove(email);
    }

    public User getUserByEmail(String email) throws ResourceNotFoundException {
        if (!users.containsKey(email)) {
            throw new ResourceNotFoundException(
                    "The user with given email: " + email + " not found");
        }
        return users.get(email);
    }

    public List<User> getAllUsers() {
        return List.copyOf(users.values());
    }

    public int getTotalUsers() {
        return users.size();
    }
}
