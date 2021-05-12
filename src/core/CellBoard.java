package core;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;

import javafx.util.Pair;
import mazeGeneration.*;

/**
 * Controls the board of Cells in the game extends JPanel
 */
public class CellBoard extends JPanel {
    public Cell[][] cells;

    boolean isMouseLeftDown;

    Cell startCell, targetCell;

    public static int cellSize = 32;
    public static int cellX = 37, cellY = 19;

    ArrayList<int[]> boardSizes;

    /**
     * Contructor, initializes the board
     */
    public CellBoard() {
        setBackground(new Color(0, 0, 0, 0));

        setSize(cellX * cellSize, cellY * cellSize);
        setLocation(Constants.width / 2 - (int) ((cellX + 0.5f) * cellSize / 2),
                Constants.height - (cellY + 1) * cellSize);

        boardSizes = new ArrayList<>();
        boardSizes.add(new int[]{37, 19, 32});
        boardSizes.add(new int[]{74, 38, 16});
        boardSizes.add(new int[]{148, 76, 8});
    }

    /**
     * Creates the board and generates the cells
     */
    public void CreateBoard() {
        cells = new Cell[cellY][cellX];

        setLayout(new GridLayout(cellY, cellX, Constants.cellGap, Constants.cellGap));

        GenerateCells();
    }

    /**
     * Generates the cells and attaches random weights if needed
     *
     */
    private void GenerateCells() {
        Random rand = new Random();

        for (int i = 0; i < cellY; i++) {
            for (int j = 0; j < cellX; j++) {
                Cell cell;
                cell = new Cell(0, j, i);

                add(cell);

                cell.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        isMouseLeftDown = true;
                        OnClickCell(cell, e);
                    }

                    @Override
                    public void mouseEntered(MouseEvent e) {
                        if (isMouseLeftDown)
                            OnLeftMouseButton(e, cell);

                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        isMouseLeftDown = false;
                    }
                });

                cells[i][j] = cell;
            }
        }
    }

    /**
     * Calls from the action listener of the buttons, handles the click event
     *
     * @param c - the clicked Cell
     */
    private void OnClickCell(Cell c, MouseEvent event) {
        if (event.getButton() == MouseEvent.BUTTON3) {
            if (event.isControlDown()) {
                SetTargetCell(c);
            } else SetStartCell(c);
        }

        if (event.getButton() == MouseEvent.BUTTON1) {
            OnLeftMouseButton(event, c);
        }
    }

    public void SetStartCell(Cell c) {
        if (startCell != null) startCell.SetCellType(Cell.CellType.EMPTY, false);

        startCell = c;

        if (c != null)
            c.SetCellType(Cell.CellType.START, false);
    }

    public void SetTargetCell(Cell c) {
        if (targetCell != null) targetCell.SetCellType(Cell.CellType.EMPTY, false);

        targetCell = c;

        if (c != null)
            c.SetCellType(Cell.CellType.TARGET, false);
    }

    /**
     * When clicking on a cell with Left Button
     *
     * @param event
     * @param c
     */
    private void OnLeftMouseButton(MouseEvent event, Cell c) {
        if (event.isControlDown())
            c.SetCellType(Cell.CellType.EMPTY, false);
        else
            c.SetCellType(Cell.CellType.WALL, false);
    }

    /**
     * Setting the board size
     */
    public void SetBoardSize(int idx) {
        idx = Math.max(0, idx);

        cellY = boardSizes.get(idx)[1];
        cellX = boardSizes.get(idx)[0];
        cellSize = boardSizes.get(idx)[2];
    }
}
