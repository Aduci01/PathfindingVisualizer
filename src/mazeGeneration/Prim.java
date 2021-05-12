package mazeGeneration;

import core.Cell;
import core.Tools;
import javafx.util.Pair;

import java.awt.*;
import java.util.*;
import java.util.List;

public class Prim extends MazeGenerator {
    static Random rand = new Random();

    public Prim() {
        super("Prim");
    }

    @Override
    public void Start(Cell[][] cells) {
        List<Cell> list = new ArrayList<>();
        SetAllCells(Cell.CellType.EMPTY, cells);

        Cell startCell = cells[rand.nextInt(cells.length)][rand.nextInt(cells[0].length)];
        startCell.SetCellType(Cell.CellType.WALL, false);
        list.addAll(GetNeighbours(startCell, cells));

        while (!list.isEmpty()) {
            Cell currentCell = list.remove(rand.nextInt(list.size()));
            if (currentCell.GetCellType() == Cell.CellType.WALL) continue;

            currentCell.SetCellType(Cell.CellType.WALL, false);

            ArrayList<Cell> neighbours = GetNeighboursWithType(currentCell, cells, Cell.CellType.WALL);

            if (!neighbours.isEmpty()) {
                Tools.Delay();

                Cell c = neighbours.remove(rand.nextInt(neighbours.size()));
                c.SetCellType(Cell.CellType.WALL, false);
                cells[c.GetPosY() - (c.GetPosY() - currentCell.GetPosY()) / 2][c.GetPosX() - (c.GetPosX() - currentCell.GetPosX()) / 2].SetCellType(Cell.CellType.WALL, false);

                Tools.Delay();
            }

            ArrayList<Cell> emptyNeighbours = GetNeighboursWithType(currentCell, cells, Cell.CellType.EMPTY);
            list.addAll(emptyNeighbours);

            for (Cell c: emptyNeighbours) {
                c.SetColor(Color.CYAN, false);
            }

            Tools.Delay();
        }

        GenerationFinished();
    }
}
