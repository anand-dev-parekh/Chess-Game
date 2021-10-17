import pieces.Base;
import java.util.Stack;


public class Board {

    public Base[][] board;
    public Stack<int[]> prevMoves;

    public Board(Base[][] board, Stack<int[]> prevMoves){
        this.board = board;
        this.prevMoves = prevMoves;
    }

    
}
