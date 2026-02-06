package service;

import model.Cell;

public class MiniGameService {

    public static final String IMG_QUESTION = "/img/question.png";
    public static final String IMG_PRIZE   = "/img/prize.png";
    public static final String IMG_WRONG   = "/img/wrong.png";

    private Cell[] cells;
    private boolean played;
    private int winningIndex;
    private boolean won;

    public MiniGameService() {
        resetGame();
    }

    public void resetGame() {
        cells = new Cell[5];
        winningIndex = (int)(Math.random() * 5);

        for (int i = 0; i < cells.length; i++) {
            cells[i] = new Cell(i == winningIndex, IMG_QUESTION);
        }

        played = false;
        won = false;
    }

    public int getCellCount() {
        return cells.length;
    }

    public Cell getCell(int index) {
        return cells[index];
    }

    public boolean selectCell(int index) {
        if (played) return false;

        played = true;

         won = cells[index].isWinning();

        if (won) {
            cells[index].setImagePath(IMG_PRIZE);
        } else {
            cells[index].setImagePath(IMG_WRONG);
            cells[winningIndex].setImagePath(IMG_PRIZE);
        }

        return won;
    }
    public boolean hasWon() { 
    	return won; 
    }

    public int getWinningIndex() {
        return winningIndex;
    }

    public boolean hasPlayed() {
        return played;
    }
}
