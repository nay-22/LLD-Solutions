package Easy.LoggingFramework.thirdPartyApp;

import java.util.Collections;
import java.util.Optional;
import java.util.HashMap;
import java.util.Map;

import Easy.LoggingFramework.thirdPartyApp.configurations.FileLoggerConfig;
import Easy.LoggingFramework.thirdPartyApp.configurations.DatabaseLoggerConfig;
import Easy.LoggingFramework.src.logger.LoggerFactory;
import Easy.LoggingFramework.src.logger.Logger;

public class UserManager {
    private final Map<String, User> users;
    private final Logger dbLogger;
    private final Logger fileLogger;

    public UserManager() {
        this.users = new HashMap<>();
        this.fileLogger = LoggerFactory.getInstance().getLogger(FileLoggerConfig.get());
        this.dbLogger = LoggerFactory.getInstance().getLogger(DatabaseLoggerConfig.get());
    }

    public boolean shutdownLoggers() {
        try {
            this.dbLogger.shutdown();
            this.fileLogger.shutdown();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            ;
            return false;
        }
    }

    public boolean registerUser(String email, String name) {
        if (email == null || email.isEmpty() || !email.contains("@")) {
            dbLogger.error("Invalid email: " + email);
            return false;
        }
        if (users.containsKey(email)) {
            dbLogger.warn("Attempt to register existing user: " + email);
            return false;
        }
        users.put(email, new User(email, name));
        fileLogger.info("User registered: " + email);
        return true;
    }

    public boolean deregisterUser(String email) {
        if (users.remove(email) != null) {
            fileLogger.info("User deregistered: " + email);
            return true;
        }
        dbLogger.warn("Attempt to deregister non-existing user: " + email);
        return false;
    }

    public Optional<User> getUserByEmail(String email) {
        return Optional.ofNullable(users.get(email));
    }

    public Map<String, User> getAllUsers() {
        return Collections.unmodifiableMap(users);
    }

    public void logAllUsers() {
        fileLogger.debug("Current Users: " + users);
    }
}
