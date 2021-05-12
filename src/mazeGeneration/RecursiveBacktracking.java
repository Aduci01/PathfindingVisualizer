package mazeGeneration;

import core.Application;
import core.Cell;
import core.Constants;
import core.Tools;

import java.util.*;

/**
 * Randomly traversing through the cells array and put all visited cells into a List
 * If all neighbours of the current cell are visited -> backtrack to the latest good cell
 */
public class RecursiveBacktracking extends MazeGenerator {

    public RecursiveBacktracking() {
        super("Recursive Backtracking");
    }

    @Override
    public void Start(Cell[][] cells) {
        SetAllCells(Cell.CellType.WALL, cells);

        Random rand = new Random();
        Cell startCell = cells[rand.nextInt(cells.length)][rand.nextInt(cells[0].length)];
        startCell.SetCellType(Cell.CellType.EMPTY, false);

        ArrayList<Cell> visitedCells = new ArrayList<>();
        visitedCells.add(startCell);

        StartGeneration(cells, visitedCells, startCell);
    }

    void StartGeneration(Cell[][] cells, ArrayList<Cell> visitedCells, Cell currentCell) {
        DoMaze(cells, visitedCells, currentCell);

        GenerationFinished();
    }

    void DoMaze(Cell[][] cells, ArrayList<Cell> visitedCells, Cell currentCell) {
        int x = currentCell.GetPosX();
        int y = currentCell.GetPosY();

        int[] a = {0, 1, 0, -1};
        int[] b = {1, 0, -1, 0};

        Integer[] intArray = {0, 1, 2, 3};
        List<Integer> intList = Arrays.asList(intArray);
        Collections.shuffle(intList);

        for (int i = 0; i < 4; i++) {
            int nx = a[intList.get(i)];
            int ny = b[intList.get(i)];

            if (nx + ny > 0) {
                if (y + ny * 2 < cells.length && x + nx * 2 < cells[0].length && !visitedCells.contains(cells[y + ny][x + nx]) && !visitedCells.contains(cells[y + ny * 2][x + nx * 2])) {
                    HandleCarving(cells[y + ny][x + nx], cells[y + ny * 2][x + nx * 2], visitedCells);

                    Tools.Delay();

                    DoMaze(cells, visitedCells, cells[y + ny * 2][x + nx * 2]);
                }
            } else {
                if (y + ny * 2 >= 0 && x + nx * 2 >= 0 && !visitedCells.contains(cells[y + ny][x + nx]) && !visitedCells.contains(cells[y + ny * 2][x + nx * 2])) {
                    HandleCarving(cells[y + ny][x + nx], cells[y + ny * 2][x + nx * 2], visitedCells);

                    Tools.Delay();

                    DoMaze(cells, visitedCells, cells[y + ny * 2][x + nx * 2]);
                }
            }
        }
    }

    public static void HandleCarving(Cell c1, Cell c2, ArrayList<Cell> visitedCells) {
        c1.SetCellType(Cell.CellType.EMPTY, false);
        c2.SetCellType(Cell.CellType.EMPTY, false);

        visitedCells.add(c1);
        visitedCells.add(c2);
    }
}
