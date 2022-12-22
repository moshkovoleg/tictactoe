package Logic;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

public class Map extends JPanel {
    private int linesCount = 3;
    private int dotsToWin = 3;
    private final int PANEL_SIZE = 500;
    private int CELL_SIZE;
    private boolean gameOver;
    private String gameOverMsg;
    Random rand = new Random();

    private int[][] field;
    private final int PLAYER1_DOT = 1;
    private final int PLAYER2_DOT = 2;
    private boolean humanVsHuman = false;
    private boolean human2ndTurn = false;

    public Map() {
        startNewGame(linesCount, dotsToWin);
        setBackground(Color.white);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                super.mouseReleased(e);
                int cmx, cmy;
                cmx = e.getX() / CELL_SIZE;
                cmy = e.getY() / CELL_SIZE;
                if (!gameOver) {
                    if (!human2ndTurn) {
                        if (setDot(cmx, cmy, PLAYER1_DOT)) {
                            updateMap(PLAYER1_DOT);
                            if (!humanVsHuman) aiTurn();
                            updateMap(PLAYER2_DOT);
                            if (humanVsHuman) human2ndTurn = true;
                        }
                    } else {
                        if (setDot(cmx, cmy, PLAYER2_DOT)) {
                            updateMap(PLAYER2_DOT);
                            human2ndTurn = false;
                        }
                    }
                }
            }
        });

    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    public void setHumanVsHuman(boolean humanVsHuman) {
        this.humanVsHuman = humanVsHuman;
    }

    public void aiTurn() {
        if (gameOver) return;
        int x, y, count;
        // Логика AI для случайной игры
        do {
            x = rand.nextInt(linesCount);
            y = rand.nextInt(linesCount);
        } while (!setDot(x, y, PLAYER2_DOT));
    }

    public void startNewGame(int linesCount, int dotsToWin) {
        this.linesCount = linesCount;
        this.dotsToWin = dotsToWin;
        CELL_SIZE = PANEL_SIZE / linesCount;
        gameOver = false;
        field = new int[linesCount][linesCount];
        repaint();
    }

    public void checkFieldFull() {
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (field[i][j] == 0) return;
            }
        }

        gameOver = true;
        gameOverMsg = "DRAW";

    }

    public boolean checkWin(int playerNumber, int dotsToWin) {
        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (checkLine(i, j, 1, 0, dotsToWin, playerNumber) || checkLine(i, j, 0, 1, dotsToWin, playerNumber) || checkLine(i, j, 1, 1, dotsToWin, playerNumber) || checkLine(i, j, 1, -1, dotsToWin, playerNumber)) {
                    gameOver = true;
                    if (PLAYER1_DOT == playerNumber) gameOverMsg = "PLAYER 1 WIN";
                    if (PLAYER2_DOT == playerNumber) gameOverMsg = "PLAYER 2 WIN";
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkLine(int cx, int cy, int vx, int vy, int l, int playerNumber) {
        if (cx + l * vx > linesCount || cy + l * vy > linesCount || cy + l * vy < -1) return false;
        for (int i = 0; i < l; i++) {
            if (field[cx + i * vx][cy + i * vy] != playerNumber) return false;
        }
        return true;
    }

    public boolean setDot(int x, int y, int dot) {
        if (field[x][y] == 0) {
            field[x][y] = dot;
            return true;
        }
        return false;
    }

    protected void updateMap(int player) {

        checkFieldFull();
        checkWin(player, this.dotsToWin);
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.black);
        for (int i = 0; i <= linesCount; i++) {
            g.drawLine(0, i * CELL_SIZE, PANEL_SIZE, i * CELL_SIZE);
            g.drawLine(i * CELL_SIZE, 0, i * CELL_SIZE, PANEL_SIZE);
        }


        for (int i = 0; i < linesCount; i++) {
            for (int j = 0; j < linesCount; j++) {
                if (field[i][j] > 0) {
                    if (field[i][j] == PLAYER1_DOT) g.setColor(Color.red);
                    if (field[i][j] == PLAYER2_DOT) g.setColor(Color.blue);
                    g.fillOval(i * CELL_SIZE + 2, j * CELL_SIZE + 2, CELL_SIZE - 4, CELL_SIZE - 4);
                }
            }
        }
        if (gameOver) {
            g.setFont(new Font("Ari", Font.BOLD, 64));
            g.setColor(Color.DARK_GRAY);
            g.fillRect(0, 220, 500, 70);
            if (gameOverMsg == "DRAW") {
                g.setColor(Color.black);
                g.drawString(gameOverMsg, 152, 277);
                g.setColor(Color.yellow);
                g.drawString(gameOverMsg, 150, 275);
            } else {
                g.setColor(Color.black);
                g.drawString(gameOverMsg, 12, 277);
                g.setColor(Color.yellow);
                g.drawString(gameOverMsg, 10, 275);
            }
        }
    }
}
