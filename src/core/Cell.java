package core;

import java.awt.Color;
import java.awt.Font;
import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

/**
 * Represents the button cells of Numbers on the board
 * extends JButton
 */
public class Cell extends JButton {
    public enum CellType {EMPTY, WALL, TARGET, START}

    CellType type = CellType.EMPTY;

    Color cellColor;
    Color previousColor = Color.WHITE;
    float colorChangeDt;

    /**
     * Used for some algorithms
     */
    public int helperNum;

    public int manhattanDistance;

    /**
     * Used for pathfinding algorithms. From which cell have we arrived here
     */
    public Cell parentCell;


    public boolean isVisited;

    private int weight;

    private int posX, posY; //Position in the grid (x - row, y - column)

    public Cell(int w, int x, int y) {
        SetWeight(w);
        posX = x;
        posY = y;

        setFont(new Font("Arial", Font.BOLD, 5));

        SetStyle();
    }

    private void SetStyle() {
        setFocusPainted(false);
        setBorder(new LineBorder(new Color(183, 209, 250, 255), 2));

        setBackground(Color.WHITE);
    }

    public void SetColor(Color color, boolean forceChange){
        cellColor = color;

        if (!forceChange)
            CellColorChangeAnimation();
        else{
            setBackground(cellColor);
            previousColor = cellColor;
        }
    }

    public void SetCellType(CellType type, boolean forceChange) {
        this.type = type;

        switch (type) {
            case EMPTY:
                cellColor = Color.WHITE;
                break;
            case START:
                cellColor = Color.GREEN;
                break;
            case TARGET:
                cellColor = Color.RED;
                break;
            case WALL:
                cellColor = new Color(64, 64, 64);
                break;
        }

        SetColor(cellColor, forceChange);
    }

    void CellColorChangeAnimation() {
        colorChangeDt = 0;

        Timer timer = new Timer("Timer");
        TimerTask task = new TimerTask() {
            public void run() {
                colorChangeDt += 0.006;
                if (colorChangeDt > 1) colorChangeDt = 1;

                setBackground(Tools.GetColorBlend(colorChangeDt, previousColor, cellColor));

                if (colorChangeDt >= 1) {
                    previousColor = cellColor;
                    timer.cancel();
                }
            }
        };

        long delay = 5;

        timer.schedule(task, 0, delay);
    }

    public int GetWeight() {
        return weight;
    }


    public void SetWeight(int w) {
        this.weight = w;

        if (w == 0) {
            setText("");
        } else {
            setText(w + "");
        }
    }

    /**
     * Returns the row position
     *
     * @return posX
     */
    public int GetPosX() {
        return posX;
    }

    /**
     * Returns the column position
     *
     * @return posY
     */
    public int GetPosY() {
        return posY;
    }

    public CellType GetCellType() {
        return type;
    }
}
