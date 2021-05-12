package mazeGeneration;

import core.Cell;
import core.Constants;
import core.Tools;

import java.io.Console;
import java.util.*;

public class RecursiveDivision extends MazeGenerator {
    static Random rand = new Random();

    public RecursiveDivision() {
        super("Recursive Division");
    }

    @Override
    public void Start(Cell[][] cells) {
        for (Cell[] c : cells) {
            for (Cell c2 : c) {
                c2.SetCellType(Cell.CellType.EMPTY, true);

                if (c2.GetPosX() == 0 || c2.GetPosY() == 0 || c2.GetPosX() == c.length - 1 || c2.GetPosY() == cells.length - 1)
                    c2.SetCellType(Cell.CellType.WALL, true);
            }
        }

        StartPathing(0, cells[0].length - 1, 0, cells.length - 1, cells);
    }

    void StartPathing(int lowerBoundaryX, int upperBoundaryX, int lowerBoundaryY, int upperBoundaryY, Cell[][] cells) {
        RecursivePathing(0, cells[0].length - 1, 0, cells.length - 1, cells);

        GenerationFinished();
    }

    void RecursivePathing(int lowerBoundaryX, int upperBoundaryX, int lowerBoundaryY, int upperBoundaryY, Cell[][] cells) {
        if (lowerBoundaryX >= upperBoundaryX - 2 || lowerBoundaryY >= upperBoundaryY - 2) return;

        Tools.Delay();
        if (rand.nextBoolean()) {
            if (!Vertical(lowerBoundaryX, upperBoundaryX, lowerBoundaryY, upperBoundaryY, cells)) {
                Horizontal(lowerBoundaryX, upperBoundaryX, lowerBoundaryY, upperBoundaryY, cells);
            }
        } else {
            if (!Horizontal(lowerBoundaryX, upperBoundaryX, lowerBoundaryY, upperBoundaryY, cells)) {
                Vertical(lowerBoundaryX, upperBoundaryX, lowerBoundaryY, upperBoundaryY, cells);
            }

        }
    }

    boolean Vertical(int lowerBoundaryX, int upperBoundaryX, int lowerBoundaryY, int upperBoundaryY, Cell[][] cells) {
        if (upperBoundaryX - lowerBoundaryX - 3 <= 0) {
            return false;
        } else {
            int idx = rand.nextInt(upperBoundaryX - lowerBoundaryX - 3) + lowerBoundaryX + 2;
            int wallSpaceidx = rand.nextInt(upperBoundaryY - lowerBoundaryY - 1) + lowerBoundaryY + 1;

            for (int i = lowerBoundaryY + 1; i < upperBoundaryY; i++) {
                cells[i][idx].SetCellType(Cell.CellType.WALL, false);
                Tools.Delay();
            }

            cells[wallSpaceidx][idx].SetCellType(Cell.CellType.EMPTY, false);

            RecursivePathing(lowerBoundaryX, idx, lowerBoundaryY, upperBoundaryY, cells);
            RecursivePathing(idx, upperBoundaryX, lowerBoundaryY, upperBoundaryY, cells);
        }

        return true;
    }

    boolean Horizontal(int lowerBoundaryX, int upperBoundaryX, int lowerBoundaryY, int upperBoundaryY, Cell[][] cells) {
        if (upperBoundaryY - lowerBoundaryY - 3 <= 0) return false;

        int idx = rand.nextInt(upperBoundaryY - lowerBoundaryY - 3) + lowerBoundaryY + 2;
        int wallSpaceidx = rand.nextInt(upperBoundaryX - lowerBoundaryX - 1) + lowerBoundaryX + 1;

        for (int i = lowerBoundaryX + 1; i < upperBoundaryX; i++) {
            cells[idx][i].SetCellType(Cell.CellType.WALL, false);
            Tools.Delay();
        }

        cells[idx][wallSpaceidx].SetCellType(Cell.CellType.EMPTY, false);

        RecursivePathing(lowerBoundaryX, upperBoundaryX, lowerBoundaryY, idx, cells);
        RecursivePathing(lowerBoundaryX, upperBoundaryX, idx, upperBoundaryY, cells);

        return true;
    }
}
