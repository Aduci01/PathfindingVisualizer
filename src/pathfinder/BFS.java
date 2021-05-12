package pathfinder;

import core.Cell;
import core.Tools;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class BFS extends PathFinder {

    public BFS(String name) {
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

        LinkedList<Cell> queue = new LinkedList();
        queue.add(start);

        int currentDistance = 0;
        while (!queue.isEmpty()) {
            Cell c = queue.removeFirst();
            c.isVisited = true;

            if (c == target) break;

            ArrayList<Cell> neighbours = GetVisitedNeighbours(c, cells, false);

            if (neighbours.isEmpty()) continue;

            for (int i = 0; i < neighbours.size(); i++) {
                Cell neighbour = neighbours.get(i);

                if (neighbour.GetCellType() == Cell.CellType.WALL) continue;

                neighbour.isVisited = true;
                neighbour.parentCell = c;
                neighbour.helperNum = c.helperNum + 1; //helperNum measures the distance from start
                queue.addLast(neighbour);

                if (target != neighbour)
                    SetCellColorByDistance(neighbour, neighbour.helperNum);
            }

            if (currentDistance < c.helperNum){
                currentDistance++;
                Tools.Delay();
            }

        }

        HighlightPathWithParents(target, start);
        GenerationFinished();
    }

}
