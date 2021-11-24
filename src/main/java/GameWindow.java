import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.TimerTask;
import java.util.Timer;


public class GameWindow extends JPanel
{

    private final int CELL_COUNT = 50;
    private final int INDENT = 2;
    private final int SPACING = 1;
    private final float CELL_SIZE = (float) (Game.SIZE - (INDENT*2) - (CELL_COUNT*SPACING)) / CELL_COUNT;
    private final int ALIVE_CHANCE = 5;
    private final long UPDATE_DELAY = 10;
    private boolean running = false;
    private int generation = 0;

    public GameWindow()
    {
        this.setBackground(Color.LIGHT_GRAY);

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
        generation++;

        for(int i = 0; i < CELL_COUNT; i++)
        {
            for(int j = 0; j < CELL_COUNT; j++)
            {

                //Cell to be examined
                Cell c = Cell.getCell(i+1, j+1);


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
                    Cell cell = Cell.getCell(i+1, j+1);

                    g.setColor(cell.alive ? Color.GREEN : Color.WHITE);

                    x = INDENT + (CELL_SIZE * i) + (SPACING * i);
                    y = INDENT + (CELL_SIZE * j) + (SPACING * j);

                    g.fillRect((int) x,(int) y,(int) CELL_SIZE,(int) CELL_SIZE);

                    g.setColor(Color.black);

                    //Draw cell name
                    //g.drawString(String.format("Cell %d, %d", i+1, j+1), (int) x, (int) (y + CELL_SIZE / 2));
                }
            }


        }

        repaint();
    }

}
