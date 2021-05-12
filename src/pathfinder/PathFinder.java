package pathfinder;

import core.Application;
import core.Cell;
import core.Tools;
import core.GameView;

import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public abstract class PathFinder {
    private int distancePerColor = 25;
    private Color[] blendColors = {Color.pink, new Color(138, 43, 226), Color.cyan, Color.green, Color.yellow};

    protected String name;

    /**
     * Constructor
     * sets the name of the algorithm as a string
     *
     * @param name
     */
    public PathFinder(String name) {
        this.name = name;
    }

    /**
     * Returns the name of the algorithm
     *
     * @return
     */
    public String GetName() {
        return name;
    }


    /**
     * Sets the waitingForAlgorithm boolean flag to false
     */
    protected void GenerationFinished() {
        Application.waitingForAlgorithm = false;
    }


    public abstract void FindPath(Cell[][] cells, Cell start, Cell target);

    /**
     * Starts the algorithm after 10ms -> important because of Dropdown listener
     *
     * @param cells
     */
    public void DelayedStart(Cell[][] cells, Cell start, Cell target) {
        if (start == null)
            start = SetStart(cells);
        if (target == null)
            target = SetTarget(cells);

        Cell finalStart = start;
        Cell finalTarget = target;
        TimerTask task = new TimerTask() {
            public void run() {
                FindPath(cells, finalStart, finalTarget);
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 10;
        timer.schedule(task, delay);
    }

    protected void SetAllCellsVisited(boolean isVisited, Cell[][] cells) {
        for (Cell[] c : cells) {
            for (Cell c2 : c) {
                c2.isVisited = isVisited;
            }
        }
    }

    protected void SetAllCellsParent(Cell parent, Cell[][] cells) {
        for (Cell[] c : cells) {
            for (Cell c2 : c) {
                c2.parentCell = parent;
            }
        }
    }

    protected ArrayList<Cell> GetVisitedNeighbours(Cell c, Cell[][] cells, boolean isVisited) {
        ArrayList<Cell> neighbours = new ArrayList<>();
        int x = c.GetPosX(), y = c.GetPosY();

        if (x >= 1) {
            Cell cell = cells[y][x - 1];
            if (cell.isVisited == isVisited)
                neighbours.add(cell);
        }

        if (x < cells[0].length - 1) {
            Cell cell = cells[y][x + 1];
            if (cell.isVisited == isVisited)
                neighbours.add(cell);
        }

        if (y >= 1) {
            Cell cell = cells[y - 1][x];
            if (cell.isVisited == isVisited)
                neighbours.add(cell);
        }

        if (y < cells.length - 1) {
            Cell cell = cells[y + 1][x];
            if (cell.isVisited == isVisited)
                neighbours.add(cell);
        }

        return neighbours;

    }

    protected ArrayList<Cell> GetAllCellsOfType(Cell[][] cells, Cell.CellType type) {
        ArrayList<Cell> list = new ArrayList<>();

        for (Cell[] c : cells) {
            for (Cell c2 : c) {
                if (c2.GetCellType() == type)
                    list.add(c2);
            }
        }

        return list;
    }

    Cell SetStart(Cell[][] cells) {
        ArrayList<Cell> emptyCells = GetAllCellsOfType(cells, Cell.CellType.EMPTY);

        Cell c = emptyCells.get(new Random().nextInt(emptyCells.size()));
        Application._instance.GetGameView().gameBoard.SetStartCell(c);

        return c;
    }

    Cell SetTarget(Cell[][] cells) {
        ArrayList<Cell> emptyCells = GetAllCellsOfType(cells, Cell.CellType.EMPTY);

        Cell c = emptyCells.get(new Random().nextInt(emptyCells.size()));
        Application._instance.GetGameView().gameBoard.SetTargetCell(c);

        return c;
    }

    protected void SetCellColorByDistance(Cell c, int dist) {
        int idx = (dist / distancePerColor) % blendColors.length;
        int idx2 = (idx + 1) % blendColors.length;

        Color color = Tools.GetColorBlend((dist % distancePerColor) / (float) distancePerColor, blendColors[idx], blendColors[idx2]);
        c.SetColor(color, false);
    }

    protected void HighlightPathWithParents(Cell c, Cell start) {
        while (c.parentCell != null) {
            c = c.parentCell;

            if (c != start)
                c.SetColor(Color.orange, false);

            Tools.Delay();
        }
    }
}
