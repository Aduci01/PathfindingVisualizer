package pathfinder;

import core.Cell;
import core.Tools;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class DFS extends PathFinder {

    public DFS(String name) {
        super(name);
    }

    @Override
    public void FindPath(Cell[][] cells, Cell start, Cell target) {
        for (Cell[] c : cells) {
            for (Cell c2 : c) {
                c2.parentCell = null;
                c2.isVisited = false;
                c2.helperNum = 0;

                if (c2.GetCellType() == Cell.CellType.EMPTY)
                    c2.SetColor(Color.white, true);
            }
        }

        RecursiveDFS(cells, start, target);

        HighlightPathWithParents(target, start);
        GenerationFinished();
    }

    boolean RecursiveDFS(Cell[][] cells, Cell current, Cell target){
        if (current == target) return true;
        current.isVisited = true;

        for (Cell c: GetVisitedNeighbours(current, cells, false)) {
            if (c.GetCellType() == Cell.CellType.WALL) continue;;

            c.parentCell = current;

            if (RecursiveDFS(cells, c, target)) return true;
        }

        return false;
    }

}
