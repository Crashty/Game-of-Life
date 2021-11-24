import java.util.ArrayList;
import java.util.Collection;

public class Cell
{
    public static ArrayList<Cell> Cells = new ArrayList<Cell>();
    public boolean alive;
    public int x, y;

    public Cell(int x, int y, boolean alive)
    {
        this.alive = alive;
        this.x = x;
        this.y = y;

        Cells.add(this);

    }

    public static Cell getCell(int x, int y)
    {
        for(Cell c : Cells)
        {
            if(c.x == x && c.y == y)
            {
                return c;
            }
        }

        return null;
    }

    public ArrayList<Cell> getLiveNeighbours()
    {

        //Future me: Cover your face for what's to come

        ArrayList<Cell> aliveCells = new ArrayList<Cell>();
        Cell c = null;

        //Top
        c = getCell(x, y - 1);
        if(c != null && c.alive) aliveCells.add(c);

        //Top-right
        c = getCell(x + 1, y - 1);
        if(c != null && c.alive) aliveCells.add(c);

        //Right
        c = getCell(x + 1, y);
        if(c != null && c.alive) aliveCells.add(c);

        //Bottom-right
        c = getCell(x + 1, y + 1);
        if(c != null && c.alive) aliveCells.add(c);

        //Bottom
        c = getCell(x, y + 1);
        if(c != null && c.alive) aliveCells.add(c);

        //Bottom-left
        c = getCell(x - 1, y + 1);
        if(c != null && c.alive) aliveCells.add(c);

        //Left
        c = getCell(x - 1, y);
        if(c != null && c.alive) aliveCells.add(c);

        //Top-left
        c = getCell(x - 1, y - 1);
        if(c != null && c.alive) aliveCells.add(c);

        return aliveCells;

    }
}
