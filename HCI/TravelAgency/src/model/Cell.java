package model;

public class Cell {

    private boolean winning;
    private String imagePath;

    public Cell(boolean winning, String imagePath) {
        this.winning = winning;
        this.imagePath = imagePath;
    }

    public boolean isWinning() {
        return winning;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String path) {
        this.imagePath = path;
    }
}
