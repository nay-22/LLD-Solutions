package Hard.Splitwise;

import Hard.Splitwise.src.domain.Expense;
import Hard.Splitwise.src.domain.Group;
import Hard.Splitwise.src.domain.User;
import Hard.Splitwise.src.exception.InvalidSplitsCombinationException;
import Hard.Splitwise.src.exception.ResourceNotFoundException;
import Hard.Splitwise.src.manager.GroupManager;
import Hard.Splitwise.src.manager.TransactionManager;
import Hard.Splitwise.src.manager.UserManager;

public class Main {
    public static void main(String[] args) throws InvalidSplitsCombinationException, ResourceNotFoundException {
        TransactionManager transactionManager = TransactionManager.getInstance();
        GroupManager groupManager = GroupManager.getInstance();
        UserManager userManager = UserManager.getInstance();

        User luffy = User.builder()
                .fname("Luffy")
                .lname("Monkey D.")
                .email("luffy@onepiece.com")
                .build();
        User zoro = User.builder()
                .fname("Zoro")
                .lname("Roronoa")
                .email("zoro@onepiece.com")
                .build();
        User sanji = User.builder()
                .fname("Sanji")
                .lname("Vinsmoke")
                .email("sanji@onepiece.com")
                .build();
        User nami = User.builder()
                .fname("Nami")
                .email("nami@onepiece.com")
                .build();
        User ussop = User.builder()
                .fname("Ussop")
                .email("ussop@onepiece.com")
                .build();

        userManager.register(luffy);
        userManager.register(zoro);
        userManager.register(sanji);
        userManager.register(nami);
        userManager.register(ussop);

        Group strawHats = Group.builder()
                .name("Straw Hats")
                .groupId("straw-hats")
                .member(luffy)
                .member(zoro)
                .member(sanji)
                .member(nami)
                .build();

        groupManager.createGroup(strawHats);

        Expense eggHeadExpense = Expense.builder()
                .name("Egg Head Bouken")
                .expenseId("egg-head")
                .amount(3500)
                .description("Egg Head Island Bouken Cost")
                .participants(userManager.getAll())
                .paidBy(nami)
                .build();

        strawHats.addExpense(eggHeadExpense);

        System.out.println("\nOutstandings of all participants of Egg Head Expense:");
        eggHeadExpense.getParticipants().forEach(u -> u.getOutstandings().forEach(System.out::println));
        System.out.println();

        System.out.println("Luffy Settles!");
        eggHeadExpense.settle(luffy);

        System.out.println("\nOutstandings of all participants of Egg Head Expense:");
        eggHeadExpense.getParticipants().forEach(u -> u.getOutstandings().forEach(System.out::println));
        System.out.println();
        System.out.println("\nTransactions data of Luffy :");
        transactionManager.getAllTransactionsInvolving(luffy.getEmail()).forEach(System.out::println);
        System.out.println();

        System.out.println("Zoro Settles!");
        eggHeadExpense.settle(zoro);

        System.out.println("\nOutstandings of all participants of Egg Head Expense:");
        eggHeadExpense.getParticipants().forEach(u -> u.getOutstandings().forEach(System.out::println));
        System.out.println();
        System.out.println("\nTransactions data of Zoro :");
        transactionManager.getAllTransactionsInvolving(zoro.getEmail()).forEach(System.out::println);
        System.out.println();

        System.out.println("Sanji Settles!");
        eggHeadExpense.settle(sanji);

        System.out.println("\nOutstandings of all participants of Egg Head Expense:");
        eggHeadExpense.getParticipants().forEach(u -> u.getOutstandings().forEach(System.out::println));
        System.out.println();
        System.out.println("\nTransactions data of Sanji :");
        transactionManager.getAllTransactionsInvolving(sanji.getEmail()).forEach(System.out::println);
        System.out.println();

        System.out.println("\nTransactions data of Nami :");
        transactionManager.getAllTransactionsInvolving(nami.getEmail()).forEach(System.out::println);
        System.out.println();

        strawHats.addMember(ussop);

        eggHeadExpense.subscribe(ussop);
        System.out.println("\nOutstandings of all participants of Egg Head Expense:");
        eggHeadExpense.getParticipants().forEach(u -> u.getOutstandings().forEach(System.out::println));

    }
}