package pathfinder;

import core.Cell;
import core.Tools;

import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class AStar extends PathFinder {

    public AStar(String name) {
        super(name);
    }

    @Override
    public void FindPath(Cell[][] cells, Cell start, Cell target) {
        for (Cell[] c : cells) {
            for (Cell c2 : c) {
                c2.parentCell = null;
                c2.isVisited = false;
                c2.helperNum = 99999; //Minimum distance from start
                c2.manhattanDistance = 2 * Math.abs(target.GetPosX() - c2.GetPosX()) + Math.abs(target.GetPosY() - c2.GetPosY());

                if (c2.GetCellType() == Cell.CellType.EMPTY)
                    c2.SetColor(Color.white, true);
            }
        }

        start.helperNum = 0;

        ArrayList<Cell> list = new ArrayList<>();
        list.add(start);

        while (!list.isEmpty()) {
            Cell c = GetCellWithSmallestF(list);
            c.isVisited = true;

            list.remove(c);

            if (c != start && c != target)
                SetCellColorByDistance(c, c.helperNum);

            if (c == target) break;

            ArrayList<Cell> neighbours = GetVisitedNeighbours(c, cells, false);
            for (int i = 0; i < neighbours.size(); i++) {
                Cell neighbour = neighbours.get(i);

                if (neighbour.GetCellType() == Cell.CellType.WALL) continue;

                neighbour.isVisited = true;
                neighbour.parentCell = c;
                neighbour.helperNum = c.helperNum + 1; //helperNum measures the distance from start
                list.add(neighbour);
            }

            Tools.Delay();
        }


        HighlightPathWithParents(target, start);
        GenerationFinished();
    }

    /**
     * Returns the cell with the lowest ManhattanDistance + DistanceFromStart
     *
     * @return
     */
    private Cell GetCellWithSmallestF(ArrayList<Cell> cells) {
        int min = 999999;
        Cell minCell = null;

        for (Cell c : cells) {
            if (c.helperNum + c.manhattanDistance < min) {
                min = c.helperNum + c.manhattanDistance;
                minCell = c;
            }
        }

        return minCell;
    }


}
