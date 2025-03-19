package Hard.Splitwise.src.manager;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import Hard.Splitwise.src.domain.Group;

public class GroupManager {
    private final Map<String, Group> groups;

    private static class Singleton {
        private static final GroupManager INSTANCE = new GroupManager();
    }

    private GroupManager() {
        groups = new ConcurrentHashMap<>();
    }

    public static GroupManager getInstance() {
        return Singleton.INSTANCE;
    }

    public void createGroup(Group group) {
        groups.put(group.getGroupId(), group);
    }

    public Group deleteGroup(String groupId) {
        return groups.remove(groupId);
    }

    public List<Group> getAll() {
        return List.copyOf(groups.values());
    }

    public Group getById(String groupId) {
        return groups.get(groupId);
    }

    @Override
    public String toString() {
        return "GroupManager [groups=" + groups + "]";
    }

}
