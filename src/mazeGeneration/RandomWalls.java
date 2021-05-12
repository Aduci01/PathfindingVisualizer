package mazeGeneration;

import core.Cell;
import core.Tools;

import java.util.*;

/**
 * Traversing through each cells of the array
 * and for each cell randomly decide whether it is a WALL or EMPTY
 */
public class RandomWalls  extends MazeGenerator{


    public RandomWalls() {
        super("Random Walls");
    }

    @Override
    public void Start(Cell[][] cells) {
        SetAllCells(Cell.CellType.EMPTY, cells);

        Random rand = new Random();

        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[0].length; j++) {
                if (rand.nextInt(100) > 62) {
                    cells[i][j].SetCellType(Cell.CellType.WALL, false);
                }
            }
        }

        GenerationFinished();
    }
}
