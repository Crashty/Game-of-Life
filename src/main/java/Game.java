import javax.swing.*;

public class Game extends JFrame
{

    public static final int SIZE = 800;
    private static Game game = null;
    private GameWindow window = null;

    public Game()
    {
        this.setTitle("Game of Life");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(SIZE, SIZE);
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        window = new GameWindow();
        this.add(window);
        this.setVisible(true);
    }

    public GameWindow getWindow()
    {
        return window;
    }

    public static void main(String[] args)
    {
        game = new Game();
        game.getWindow().start();
    }

    public static Game get()
    {
        return game;
    }
}
