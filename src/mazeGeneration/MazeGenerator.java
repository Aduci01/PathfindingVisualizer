package mazeGeneration;

import core.Application;
import core.Cell;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Abstract class for MazeGenearation algorithms
 */
public abstract class MazeGenerator {
    protected String name;

    /**
     * Constructor
     * sets the name of the algorithm as a string
     * @param name
     */
    public MazeGenerator(String name) {
        this.name = name;
    }

    /**
     * Starts the algorithm
     * @param cells
     */
    public abstract void Start(Cell[][] cells);

    /**
     * Starts the algorithm after 10ms -> important because of Dropdown listener
     * @param cells
     */
    public void DelayedStart(Cell[][] cells) {
        Application._instance.GetGameView().gameBoard.SetTargetCell(null);
        Application._instance.GetGameView().gameBoard.SetStartCell(null);

        TimerTask task = new TimerTask() {
            public void run() {
                Start(cells);
            }
        };
        Timer timer = new Timer("Timer");

        long delay = 10;
        timer.schedule(task, delay);
    }

    /**
     * Sets the waitingForAlgorithm boolean flag to false
     */
    protected void GenerationFinished() {
        Application.waitingForAlgorithm = false;
    }

    /**
     * Returns the name of the algorithm
     * @return
     */
    public String GetName() {
        return name;
    }

    /**
     * Sets all cells to the given type
     * @param type
     * @param cells
     */
    protected void SetAllCells(Cell.CellType type, Cell[][] cells) {
        for (Cell[] c : cells) {
            for (Cell c2 : c) {
                c2.SetCellType(type, true);
            }
        }
    }

    /**
     * Gets all neighbours of c Cell
     * @param c
     * @param cells
     * @return
     */
    protected ArrayList<Cell> GetNeighbours(Cell c, Cell[][] cells) {
        ArrayList<Cell> neighbours = new ArrayList<>();
        int x = c.GetPosX(), y = c.GetPosY();

        if (x >= 2) {
            neighbours.add(cells[y][x - 2]);
        }

        if (x < cells[0].length - 2) {
            neighbours.add(cells[y][x + 2]);
        }

        if (y >= 2) {
            neighbours.add(cells[y - 2][x]);
        }

        if (y < cells.length - 2) {
            neighbours.add(cells[y + 2][x]);
        }

        return neighbours;
    }

    /**
     * Gets all neighbour of c Cell which has "type" CellType
     * @param c
     * @param cells
     * @param type
     * @return
     */
    protected ArrayList<Cell> GetNeighboursWithType(Cell c, Cell[][] cells, Cell.CellType type) {
        ArrayList<Cell> neighbours = new ArrayList<>();
        int x = c.GetPosX(), y = c.GetPosY();

        if (x >= 2) {
            Cell cell = cells[y][x - 2];
            if (cell.GetCellType() == type)
                neighbours.add(cell);
        }

        if (x < cells[0].length - 2) {
            Cell cell = cells[y][x + 2];
            if (cell.GetCellType() == type)
                neighbours.add(cell);
        }

        if (y >= 2) {
            Cell cell = cells[y - 2][x];
            if (cell.GetCellType() == type)
                neighbours.add(cell);
        }

        if (y < cells.length - 2) {
            Cell cell = cells[y + 2][x];
            if (cell.GetCellType() == type)
                neighbours.add(cell);
        }

        return neighbours;
    }
}
