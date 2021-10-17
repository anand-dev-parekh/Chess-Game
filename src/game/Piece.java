package game;

public abstract class Piece {
    String color; //0 for white, 1 for black
    String piece;

    int col;
    int row;

    public abstract boolean validMove(int col, int row, Piece[][] grid);

    protected boolean inBounds(int col, int row){
        if(this.col >= 0 && this.col <= 7 && this.row >= 0 && this.row <= 7) return true;
        return false;
    }
}
