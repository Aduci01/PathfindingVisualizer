package mazeGeneration;

import core.Cell;
import core.Constants;
import core.Tools;
import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.*;

public class Kruskal extends MazeGenerator {
    static Random rand = new Random();

    ArrayList<ArrayList<Cell>> sets;

    public Kruskal() {
        super("Kruskal");
    }

    @Override
    public void Start(Cell[][] cells) {
        SetAllCells(Cell.CellType.EMPTY, cells);

        List<Pair<Cell, Integer>> edges = new ArrayList<>();
        Hashtable<Integer, ArrayList<Cell>> sets = new Hashtable<>();

        int setNum = 0;
        for (int i = 1; i < cells.length - 1; i += 2) {
            for (int j = 1; j < cells[0].length - 1; j += 2) {
                Cell c2 = cells[i][j];

                edges.add(new Pair<>(c2, 0));
                edges.add(new Pair<>(c2, 1));


                c2.helperNum = setNum;

                ArrayList<Cell> list = new ArrayList<Cell>();
                list.add(c2);

                sets.put(setNum++, list);


            }
        }

        Collections.shuffle(edges);

        while (!edges.isEmpty()) {
            Cell c = edges.get(0).getKey();
            int orientation = edges.get(0).getValue();

            edges.remove(0);

            if (c.GetCellType() == Cell.CellType.WALL) {
                continue;
            }

            if (orientation == 0) { //vertical
                int x = c.GetPosX(), y = c.GetPosY();
                if (y >= 0 && y < cells.length - 2) {
                    Cell up = cells[y + 2][x];

                    if (up.helperNum == c.helperNum) continue;

                    UnifySets(c.helperNum, up.helperNum, sets);

                    up.SetCellType(Cell.CellType.WALL, false);
                    cells[y + 1][x].SetCellType(Cell.CellType.WALL, false);
                    cells[y][x].SetCellType(Cell.CellType.WALL, false);
                }
            } else { //horizontal
                int x = c.GetPosX(), y = c.GetPosY();
                if (x >= 0 && x < cells[0].length - 2) {
                    Cell right = cells[y][x + 2];

                    if (right.helperNum == c.helperNum) continue;

                    UnifySets(c.helperNum, right.helperNum, sets);

                    right.SetCellType(Cell.CellType.WALL, false);
                    cells[y][x + 1].SetCellType(Cell.CellType.WALL, false);
                    cells[y][x].SetCellType(Cell.CellType.WALL, false);
                }
            }

            Tools.Delay();
        }

        GenerationFinished();
    }

    void UnifySets(int s1Num, int s2Num, Hashtable<Integer, ArrayList<Cell>> set) {
        if (s1Num == s2Num) return;

        for (Cell c : set.get(s2Num)) {
            set.get(s1Num).add(c);
            c.helperNum = s1Num;
        }

        set.get(s2Num).clear();
    }

}

