package Medium.TicTacToe.src;

import java.util.Scanner;

import javax.naming.directory.InvalidAttributesException;

public class GameManager {
    private Board board;
    private Player playerO;
    private Player playerX;
    private Symbol turn;
    private Scanner in;

    public GameManager(Player playerO, Player playerX) {
        this.in = new Scanner(System.in);
        this.board = new Board();
        this.playerO = playerO;
        this.playerX = playerX;
        this.turn = Symbol.O;
    }

    private Player resolvePlayer(Symbol symbol) {
        return symbol == Symbol.X ? playerX : playerO;
    }

    private void toggleTurn() {
        turn = turn == Symbol.X ? Symbol.O : Symbol.X;
    }

    public void execute() {
        while (true) {
            try {
                System.out.println(board);
                System.out.println("Turn: " + resolvePlayer(turn));
                String attr = in.next();
                if (attr.length() != 2) {
                    throw new InvalidAttributesException(
                            "Attributes must have two integers only, denoting row and col values");
                }
                String[] attrs = attr.split("");
                int row, col;
                try {
                    row = Integer.parseInt(attrs[0]);
                    col = Integer.parseInt(attrs[1]);
                    if (!(row >= 0 && row < 3 && col >= 0 && col < 3)) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    throw new InvalidAttributesException("The row and col must be valid integers between 0 and 2");
                }
                board.set(row, col, turn);
                System.out.println(board);
                if (board.isWinner(turn)) {
                    System.out.println(resolvePlayer(turn) + " WINS!!");
                    break;
                } else if (board.isFull()) {
                    System.out.println("Neither WINS. Its a DRAW!");
                    break;
                }
                toggleTurn();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        in.close();
    }
}
