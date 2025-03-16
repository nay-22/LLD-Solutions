package Easy.StackOverflow;

import java.util.Random;
import java.util.List;

import Easy.StackOverflow.src.exceptions.DuplicateResourceException;
import Easy.StackOverflow.src.exceptions.InvalidResourceException;
import Easy.StackOverflow.src.domain.interfaces.Votable;
import Easy.StackOverflow.src.managers.QuestionManager;
import Easy.StackOverflow.src.managers.AnswerManager;
import Easy.StackOverflow.src.managers.UserManager;
import Easy.StackOverflow.src.domain.Question;
import Easy.StackOverflow.src.domain.Answer;
import Easy.StackOverflow.src.domain.Tag;
import Easy.StackOverflow.src.domain.User;

public class Main {
    public static void main(String[] args) throws DuplicateResourceException, InvalidResourceException {
        init();

        QuestionManager questionManager = QuestionManager.getInstance();
        AnswerManager answerManager = AnswerManager.getInstance();
        UserManager userManager = UserManager.getInstance();

        data();

        List<User> users = userManager.getAllUsers();
        List<Question> questionsByLuffy = questionManager.extractQuestionsByUserProfiles(List.of(users.get(0)));
        List<Question> questionsByNayan = questionManager.extractQuestionsByUserProfiles(List.of(users.get(1)));
        // List<Question> questionsByZoro =
        // questionManager.extractQuestionsByUserProfiles(List.of(users.get(2)));

        upvote(questionsByLuffy.get(0), 13);
        downvote(questionsByLuffy.get(0), 4);
        downvote(questionsByLuffy.get(1), 4);

        upvote(questionsByNayan.get(0), 6);

        data();

        answerManager.getAllAnswers().forEach(a -> upvote(a, new Random().nextInt(2, 6)));

        data();

    }

    private static void upvote(Votable instance, int count) {
        for (int i = 0; i < count; i++) {
            instance.upvote();
        }
    }

    private static void downvote(Votable instance, int count) {
        for (int i = 0; i < count; i++) {
            instance.downvote();
        }
    }

    private static void data() {
        QuestionManager questionManager = QuestionManager.getInstance();
        AnswerManager answerManager = AnswerManager.getInstance();
        UserManager userManager = UserManager.getInstance();

        List<User> users = userManager.getAllUsers();
        users.forEach(System.out::println);
        List<Question> questionsByLuffy = questionManager.extractQuestionsByUserProfiles(List.of(users.get(0)));
        List<Question> questionsByNayan = questionManager.extractQuestionsByUserProfiles(List.of(users.get(1)));
        List<Question> questionsByZoro = questionManager.extractQuestionsByUserProfiles(List.of(users.get(2)));

        System.out.println("\nQuestions By Luffy:");
        questionsByLuffy.forEach(System.out::println);
        System.out.println("\nQuestions By Nayan:");
        questionsByNayan.forEach(System.out::println);
        System.out.println("\nQuestions By Zoro:");
        questionsByZoro.forEach(System.out::println);
        System.out.println();

        System.out.println("\nAnswers To Luffy's Questions:");
        questionsByLuffy.forEach(q -> {
            System.out.println("QUESTION[" + q.getVotes() + "]: " + q.getValue());
            answerManager.getAnswers(q)
                    .forEach(a -> System.out.println("\tANSWER[" + a.getVotes() + "]: " + a.getValue()));
        });
        System.out.println("\nAnswers To Nayan's Questions:");
        questionsByNayan.forEach(q -> {
            System.out.println("QUESTION[" + q.getVotes() + "]: " + q.getValue());
            answerManager.getAnswers(q)
                    .forEach(a -> System.out.println("\tANSWER[" + a.getVotes() + "]: " + a.getValue()));
        });
        System.out.println("\nAnswers To Zoro's Questions:");
        questionsByZoro.forEach(q -> {
            System.out.println("QUESTION[" + q.getVotes() + "]: " + q.getValue());
            answerManager.getAnswers(q)
                    .forEach(a -> System.out.println("\tANSWER[" + a.getVotes() + "]: " + a.getValue()));
        });

        System.out.println();
    }

    private static void init() throws DuplicateResourceException, InvalidResourceException {
        QuestionManager questionManager = QuestionManager.getInstance();
        AnswerManager answerManager = AnswerManager.getInstance();
        UserManager userManager = UserManager.getInstance();

        User nayan = User.builder()
                .fname("Nayan")
                .email("nayan92001@gmail.com")
                .username("genuinepotion")
                .phone("+91 8879898578")
                .build();
        User luffy = User.builder()
                .fname("Luffy")
                .email("luffy@onepiece.com")
                .username("pirateking")
                .build();
        User zoro = User.builder()
                .fname("Zoro")
                .email("zoro@onepiece.com")
                .username("pirateking")
                .build();

        userManager.registerUser(nayan);
        userManager.registerUser(luffy);
        userManager.registerUser(zoro);

        System.out.println("-".repeat(80));
        System.out.println("Users registered:");
        List<User> users = userManager.getAllUsers();
        users.forEach(System.out::println);
        System.out.println("-".repeat(80));

        Question question1 = new Question("How to implement mutex in java?",
                luffy.getEmail());
        Question question2 = new Question("How does ConcurrentHashMap works?",
                nayan.getEmail());
        Question question3 = new Question("What is Webpack?",
                luffy.getEmail());
        Tag java = new Tag("java");
        Tag concurrency = new Tag("concurrency");
        Tag multithreading = new Tag("multithreading");
        Tag frontend = new Tag("frontend");
        Tag webpack = new Tag("webpack");

        questionManager.postQuestion(List.of(question1, question2, question3));

        questionManager.attachTag(question1, java);
        questionManager.attachTag(question2, List.of(java, concurrency, multithreading));
        questionManager.attachTag(question3, List.of(frontend, webpack));

        System.out.println("-".repeat(80));
        System.out.println("Questions posted:");
        questionManager.getAllQuestions().forEach(System.out::println);
        System.out.println("-".repeat(80));

        Answer answer1 = new Answer("Make use of the Semaphore class from java.concurrent package",
                nayan.getEmail());
        Answer answer2 = new Answer("By using segments of buckets to manage locks",
                luffy.getEmail());
        Answer answer3 = new Answer("A build tool for frontend apps",
                zoro.getEmail());
        Answer answer4 = new Answer(
                "A build tool often used with React/Angular frontend projects to make the frameworks work properly on web",
                nayan.getEmail());

        answerManager.postAnswer(question1, answer1);
        answerManager.postAnswer(question2, answer2);
        answerManager.postAnswer(question3, answer3);
        answerManager.postAnswer(question3, answer4);

        System.out.println("-".repeat(80));
        System.out.println("Answers posted:");
        answerManager.getAllAnswers().forEach(System.out::println);
        System.out.println("-".repeat(80));
    }
}
