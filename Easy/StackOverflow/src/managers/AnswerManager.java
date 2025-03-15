package src.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import src.domain.Answer;
import src.domain.Question;
import src.exceptions.InvalidResourceException;
import src.utils.Util;

public class AnswerManager {

    private final Map<Question, Set<Answer>> answers;

    private static class SingletonExtractor {
        private static final AnswerManager INSTANCE = new AnswerManager();
    }

    private AnswerManager() {
        this.answers = new ConcurrentHashMap<>();
    }

    public static AnswerManager getInstance() {
        return SingletonExtractor.INSTANCE;
    }

    public List<Answer> getAnswers(Question question) {
        try {
            return List.copyOf(answers.get(question));
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    public boolean postAnswer(Question question, Answer answer) {
        try {
            Util.ensureResourceValidity(answer);
            answers.computeIfAbsent(question, _ -> ConcurrentHashMap.newKeySet()).add(answer);
            return true;
        } catch (InvalidResourceException e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public boolean removeAnswer(Question question, Answer answer) {
        try {
            Util.ensureResourceValidity(question);
            answers.computeIfPresent(question, (_, answerSet) -> {
                answerSet.remove(answer);
                return answerSet.isEmpty() ? null : answerSet;
            });
            return true;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return false;
        }
    }

    public List<Answer> getAllAnswers() {
        List<Answer> list = new ArrayList<>();
        answers.values().forEach(list::addAll);
        return list;
    }
}
