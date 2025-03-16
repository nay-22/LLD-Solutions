package Easy.StackOverflow.src.managers;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import Easy.StackOverflow.src.domain.Question;
import Easy.StackOverflow.src.domain.Tag;
import Easy.StackOverflow.src.exceptions.InvalidResourceException;
import Easy.StackOverflow.src.utils.Util;

class TagManager {

    private final Map<Tag, Set<Question>> tags;

    private static class SingletonExtractor {
        private static final TagManager INSTANCE = new TagManager();
    }

    private TagManager() {
        this.tags = new ConcurrentHashMap<>();
    }

    public static TagManager getInstance() {
        return SingletonExtractor.INSTANCE;
    }

    public List<Question> getQuestions(List<Tag> tags) throws InvalidResourceException {
        Util.ensureResourceValidity(tags);
        Set<Question> set = new HashSet<>();
        tags.forEach(tag -> {
            Set<Question> questionSet = this.tags.get(tag);
            if (questionSet != null) {
                set.addAll(questionSet);
            }
        });
        return List.copyOf(set);
    }

    public void mapQuestion(Tag tag, Question question) throws InvalidResourceException {
        Util.ensureResourceValidity(tag, question);
        tags.computeIfAbsent(tag, _ -> ConcurrentHashMap.newKeySet()).add(question);
    }

    public void mapQuestion(List<Tag> tags, Question question) throws InvalidResourceException {
        Util.ensureResourceValidity(tags, question);
        if (tags.isEmpty()) {
            return;
        }
        for (Tag tag : tags) {
            mapQuestion(tag, question);
        }
    }

    public void unmapQuestion(Tag tag, Question question) throws InvalidResourceException {
        Util.ensureResourceValidity(tag, question);
        tags.computeIfPresent(tag, (_, questionSet) -> {
            questionSet.remove(question);
            return questionSet.isEmpty() ? null : questionSet;
        });
    }

    public void unmapQuestion(List<Tag> tags, Question question) throws InvalidResourceException {
        Util.ensureResourceValidity(tags, question);
        if (tags.isEmpty()) {
            return;
        }
        for (Tag tag : tags) {
            this.tags.computeIfPresent(tag, (_, questionSet) -> {
                questionSet.remove(question);
                return questionSet.isEmpty() ? null : questionSet;
            });
        }
    }

    public void print() {
        tags.forEach((tag, questionSet) -> System.out.println(tag + " => " + questionSet));
    }

}
