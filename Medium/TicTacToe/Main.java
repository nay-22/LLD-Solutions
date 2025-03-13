import src.GameManager;
import src.Player;
import src.Symbol;
import src.exceptions.InvalidBoardSetException;

public class Main {
    public static void main(String[] args) throws InvalidBoardSetException {
        Player luffy = Player.creatPlayer().name("luffy").symbol(Symbol.O);
        Player zoro = Player.creatPlayer().name("zoro").symbol(Symbol.X);
        System.out.println(luffy);
        System.out.println(zoro);
        
        GameManager game = new GameManager(luffy, zoro);
        game.execute();
    }
}