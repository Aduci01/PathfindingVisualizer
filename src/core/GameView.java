package core;

import mazeGeneration.*;
import pathfinder.AStar;
import pathfinder.BFS;
import pathfinder.DFS;
import pathfinder.PathFinder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * Controls the Game View and gameplay related things, extends JPanel
 */
public class GameView extends JPanel {
    /**
     * Holds the cells grid
     */
    public CellBoard gameBoard;

    /**
     * Holds all the GUI elements on the top
     */
    private JPanel topHolder;

    private JComboBox<String> mazeBox, pathfinderBox, boardSizeBox;
    private JSlider delaySlider;

    MazeGenerator[] mazeGenerators = {new BinaryTreeMaze(), new Prim(), new Kruskal(), new RandomWalls(), new RecursiveBacktracking(), new RecursiveDivision()};
    MazeGenerator currentMazeGenerator;

    PathFinder[] pathFinders = {new BFS("Breadth First Search"), new DFS("Depth First Search"), new AStar("A*")};

    /**
     * Constructor, initializes the GameBoard and GUIs
     */
    public GameView() {
        setSize(Constants.width, Constants.height);

        CreateBoard();

        InitUIComponents();
        setLayout(null);

        currentMazeGenerator = mazeGenerators[0];
    }

    /**
     * Initializing ui components
     */
    private void InitUIComponents() {
        topHolder = new JPanel();
        topHolder.setSize(Constants.width, 80);
        topHolder.setLocation(0, 0);
        topHolder.setBackground(new Color(0, 0.0f, 0.75f, 0));

        add(topHolder);

        //Creating maze generation dropdown
        CreateMazeBox();
        //Creating pathfinder dropdown box
        CreatePathingBox();

        //Creating the slider for delay settings
        CreateSlider();

        //Creating the board size setter dropdown
        CreateBoardSizeBox();

        SetDropdownTitle();
    }

    /**
     * Creating the maze generation dropdown
     */
    private void CreateMazeBox() {
        String[] mazeStrings = new String[mazeGenerators.length];
        for (int i = 0; i < mazeStrings.length; i++) {
            mazeStrings[i] = mazeGenerators[i].GetName();
        }

        mazeBox = new JComboBox<String>(mazeStrings);
        mazeBox.setFont(new Font("Arial", Font.BOLD, 18));

        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        mazeBox.setRenderer(listRenderer);

        mazeBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int idx = mazeBox.getSelectedIndex();

                if (idx >= 0) {
                    if (Application.waitingForAlgorithm) return;

                    Application.waitingForAlgorithm = true;

                    currentMazeGenerator = mazeGenerators[idx];
                    currentMazeGenerator.DelayedStart(gameBoard.cells);
                }
            }
        });

        topHolder.add(mazeBox);
    }

    /**
     * Creating the pathfinder dropdown
     */
    private void CreatePathingBox() {
        String[] pathStrings = new String[pathFinders.length];
        for (int i = 0; i < pathStrings.length; i++) {
            pathStrings[i] = pathFinders[i].GetName();
        }

        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items

        pathfinderBox = new JComboBox<String>(pathStrings);
        pathfinderBox.setToolTipText("Pathfinding");
        pathfinderBox.setFont(new Font("Arial", Font.BOLD, 18));
        pathfinderBox.setRenderer(listRenderer);

        pathfinderBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int idx = pathfinderBox.getSelectedIndex();

                if (idx >= 0) {
                    if (Application.waitingForAlgorithm) return;

                    Application.waitingForAlgorithm = true;

                    pathFinders[idx].DelayedStart(gameBoard.cells, gameBoard.startCell, gameBoard.targetCell);
                }
            }
        });

        topHolder.add(pathfinderBox);
    }

    /**
     * Creating the board size setter dropdown
     */
    private void CreateBoardSizeBox() {
        String[] sizeStrings = {"Small", "Medium", "Large"};

        boardSizeBox = new JComboBox<String>(sizeStrings);
        boardSizeBox.setFont(new Font("Arial", Font.BOLD, 18));
        boardSizeBox.setPreferredSize(new Dimension(200, 30));

        DefaultListCellRenderer listRenderer = new DefaultListCellRenderer();
        listRenderer.setHorizontalAlignment(DefaultListCellRenderer.CENTER); // center-aligned items
        boardSizeBox.setRenderer(listRenderer);

        boardSizeBox.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (Application.waitingForAlgorithm) return;

                int idx = boardSizeBox.getSelectedIndex();

                gameBoard.SetBoardSize(idx);
                CreateBoard();
            }
        });

        topHolder.add(boardSizeBox);
    }

    private void SetDropdownTitle() {
        mazeBox.setEditable(true);
        mazeBox.setSelectedItem("Maze Generation");
        mazeBox.setEditable(false);

        pathfinderBox.setEditable(true);
        pathfinderBox.setSelectedItem("Pathfinding");
        pathfinderBox.setEditable(false);

        boardSizeBox.setEditable(true);
        boardSizeBox.setSelectedItem("Board Size");
        boardSizeBox.setEditable(false);
    }

    private void CreateSlider() {
        Application.ms = 75;
        DefaultBoundedRangeModel model = new DefaultBoundedRangeModel(75, 0, 0, 500);
        delaySlider = new JSlider(model);
        //delaySlider.setPaintTrack(true);
        //delaySlider.setPaintTicks(true);
        delaySlider.setPaintLabels(true);

        delaySlider.setMajorTickSpacing(250);
        delaySlider.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent event) {
                int value = delaySlider.getValue();
                Application.ms = value;
            }
        });

        topHolder.add(delaySlider);
    }

    public void CreateBoard() {
        if (gameBoard != null)
            remove(gameBoard);

        gameBoard = new CellBoard();
        gameBoard.CreateBoard();

        revalidate();
        repaint();

        add(gameBoard);
    }
}
