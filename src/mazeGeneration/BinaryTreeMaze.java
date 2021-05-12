package mazeGeneration;

import core.Cell;
import core.Constants;
import core.Tools;

import java.lang.reflect.Array;
import java.util.*;

public class BinaryTreeMaze extends MazeGenerator {

    public BinaryTreeMaze() {
        super("Binary Tree");
    }

    @Override
    public void Start(Cell[][] cells) {
        SetAllCells(Cell.CellType.WALL, cells);

        Random rand = new Random();

        for (int i = 0; i < cells.length; i += 2) {
            for (int j = 0; j < cells[0].length; j += 2) {
                Cell c = cells[i][j];
                c.SetCellType(Cell.CellType.EMPTY, false);

                ArrayList<Cell> emptyNeighbours = new ArrayList<>();

                if (i != 0 && cells[i - 2][j].GetCellType() == Cell.CellType.EMPTY)
                    emptyNeighbours.add(cells[i - 1][j]);

                if (j != 0 && cells[i][j - 2].GetCellType() == Cell.CellType.EMPTY)
                    emptyNeighbours.add(cells[i][j - 1]);

                if (!emptyNeighbours.isEmpty()) {
                    emptyNeighbours.get(rand.nextInt(emptyNeighbours.size())).SetCellType(Cell.CellType.EMPTY, false);
                }

                Tools.Delay();
            }
        }

        GenerationFinished();
    }
}
