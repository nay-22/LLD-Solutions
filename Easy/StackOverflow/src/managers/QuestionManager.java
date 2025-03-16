package Easy.StackOverflow.src.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import Easy.StackOverflow.src.domain.Question;
import Easy.StackOverflow.src.domain.Tag;
import Easy.StackOverflow.src.domain.User;
import Easy.StackOverflow.src.exceptions.InvalidResourceException;
import Easy.StackOverflow.src.utils.Util;

public class QuestionManager {

    private final Map<String, Set<Question>> questions;
    private final TagManager tagManager;

    private static class SingletonExtracter {
        private static final QuestionManager INSTANCE = new QuestionManager();
    }

    private QuestionManager() {
        this.questions = new ConcurrentHashMap<>();
        this.tagManager = TagManager.getInstance();
    }

    public static QuestionManager getInstance() {
        return SingletonExtracter.INSTANCE;
    }

    public List<Question> getAllQuestions() {
        List<Question> res = new ArrayList<>();
        questions.values().forEach(res::addAll);
        return res;
    }

    public boolean postQuestion(List<Question> questions) {
        try {
            Util.ensureResourceValidity(questions);
            if (questions.isEmpty()) {
                return false;
            }
            questions.forEach(this::postQuestion);
            return true;
        } catch (InvalidResourceException e) {
            System.err.println(e.getMessage());
            return false;
        }

    }

    public boolean postQuestion(Question question) {
        try {
            Util.ensureResourceValidity(question);
            questions.computeIfAbsent(question.getOwnerId(), _ -> ConcurrentHashMap.newKeySet()).add(question);
            return true;
        } catch (InvalidResourceException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean postQuestion(Question question, Tag tag) {
        try {
            Util.ensureResourceValidity(question, tag);
            tagManager.mapQuestion(tag, question);
            questions.computeIfAbsent(question.getOwnerId(), _ -> ConcurrentHashMap.newKeySet()).add(question);
            return true;
        } catch (InvalidResourceException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean postQuestion(Question question, List<Tag> tags) {
        try {
            Util.ensureResourceValidity(question, tags);
            tagManager.mapQuestion(tags, question);
            questions.computeIfAbsent(question.getOwnerId(), _ -> ConcurrentHashMap.newKeySet()).add(question);
            return true;
        } catch (InvalidResourceException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean attachTag(Question question, List<Tag> tags) {
        try {
            Util.ensureResourceValidity(question, tags);
            tagManager.mapQuestion(tags, question);
            return true;
        } catch (InvalidResourceException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean attachTag(Question question, Tag tag) {
        try {
            Util.ensureResourceValidity(question, tag);
            tagManager.mapQuestion(tag, question);
            return true;
        } catch (InvalidResourceException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean detachTag(Question question, Tag tag) {
        try {
            Util.ensureResourceValidity(question, tag);
            tagManager.unmapQuestion(tag, question);
            return true;
        } catch (InvalidResourceException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean detachTag(Question question, List<Tag> tags) {
        try {
            Util.ensureResourceValidity(question, tags);
            tagManager.unmapQuestion(tags, question);
            return true;
        } catch (InvalidResourceException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean removeQuestion(Question question) {
        try {
            Util.ensureResourceValidity(question);
            questions.computeIfPresent(question.getOwnerId(), (_, questionSet) -> {
                questionSet.remove(question);
                return questionSet.isEmpty() ? null : questionSet;
            });
            return true;
        } catch (InvalidResourceException e) {
            return false;
        }
    }

    public List<Question> searchQuestions(String query) {
        return null;
    }

    public List<Question> extractQuestionsByTags(List<Tag> tags) {
        try {
            Util.ensureResourceValidity(tags);
            return tagManager.getQuestions(tags);
        } catch (InvalidResourceException e) {
            return new ArrayList<>();
        }
    }

    public List<Question> extractQuestionsByUserProfiles(List<User> users) {
        try {
            Util.ensureResourceValidity(users);
            if (users.isEmpty()) {
                return new ArrayList<>();
            }
            List<Question> res = new ArrayList<>();
            for (User user : users) {
                Util.ensureResourceValidity(user);
                Set<Question> questionSet = questions.get(user.getEmail());
                if (questionSet == null) {
                    return new ArrayList<>();
                }
                res.addAll(questions.get(user.getEmail()));
            }
            return res;
        } catch (InvalidResourceException e) {
            return new ArrayList<>();
        }
    }

}
