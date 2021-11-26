import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;


public class GameWindow extends JPanel implements KeyListener
{

    private final Color BACKGROUND_COLOR = Color.BLACK;
    private final Color CELL_COLOR = Color.WHITE;
    private final int CELL_COUNT = 50;
    private final int INDENT = 0;
    private final int SPACING = 0;
    private final float CELL_SIZE = (float) (Game.SIZE - (INDENT*2) - (CELL_COUNT*SPACING)) / CELL_COUNT;
    private final int ALIVE_CHANCE = 5;
    private final long UPDATE_DELAY = 100;
    public boolean running = false;
    private boolean updating = false;
    private int generation = 0;

    public GameWindow()
    {
        this.setBackground(BACKGROUND_COLOR);
        this.setFocusable(true);
        this.addKeyListener(this);

        Cell.setWindow(this);

        generateCellStates();

    }



    public void start()
    {
        running = true;

        TimerTask task = new TimerTask()
        {
            @Override
            public void run()
            {
                nextGeneration();
            }
        };

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, UPDATE_DELAY, UPDATE_DELAY);
    }

    private void nextGeneration()
    {
        if(!running || updating) return;

        generation++;
        updating = true;

        //Make a copy of the original cells
        ArrayList<Cell> updatedCells = new ArrayList<Cell>();




        for(int i = 0; i < CELL_COUNT; i++)
        {
            for(int j = 0; j < CELL_COUNT; j++)
            {
                if(!running) break;

                //Cell to be examined
                Cell c = Cell.getCellCopy(i+1, j+1);
                updatedCells.add(c);


                int neighbours = c.getLiveNeighbours().size();


                if(c.alive)
                {
                    //Overpopulation / Underpopulation
                    if (neighbours > 3 || neighbours < 2)
                    {
                        c.alive = false;
                    }
                }
                else
                {
                    //Reproduction
                    if(neighbours == 3)
                    {
                        c.alive = true;
                    }
                }


            }
        }

        Cell.Cells = updatedCells;

        updating = false;
    }

    private void restartGame()
    {
        running = false;
        updating = true;
        generation = 0;
        Cell.purge();
        generateCellStates();
        running = true;
        updating = false;

    }

    private void generateCellStates()
    {
        Random random = new Random();

        for(int i = 1; i <= CELL_COUNT; i++)
        {
            for(int j = 1; j <= CELL_COUNT; j++)
            {
                new Cell(i, j, random.nextInt(100) < ALIVE_CHANCE);
            }
        }
    }

    @Override
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);

        if(running)
        {


            // Draw the lines for the cells


            float x, y;

            for(int i = 0; i < CELL_COUNT; i++)
            {
                for(int j = 0; j < CELL_COUNT; j++)
                {
                    if(!running) break;

                    Cell cell = Cell.getCell(i+1, j+1);

                    g.setColor(cell.alive ? CELL_COLOR : BACKGROUND_COLOR);

                    x = INDENT + (CELL_SIZE * i) + (SPACING * i);
                    y = INDENT + (CELL_SIZE * j) + (SPACING * j);

                    g.fillRect((int) x,(int) y,(int) CELL_SIZE,(int) CELL_SIZE);

                    //Draw cell name
                    //g.drawString(String.format("Cell %d, %d", i+1, j+1), (int) x, (int) (y + CELL_SIZE / 2));
                }
            }


        }

        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e)
    {
    }

    @Override
    public void keyPressed(KeyEvent e)
    {
    }



    @Override
    public void keyReleased(KeyEvent e)
    {
        if(e.getKeyCode() == KeyEvent.VK_SPACE)
        {
            restartGame();
        }

        if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
        {
            System.exit(0);
        }
    }
}
