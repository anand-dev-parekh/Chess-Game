import pieces.Base;
import java.util.ArrayList;


public class Board {

    public Base[][] board;
    public ArrayList<int[]> prevMoves;

    public Board(Base[][] board, ArrayList<int[]> prevMoves){
        this.board = board;
        this.prevMoves = prevMoves;
    }

    
}
