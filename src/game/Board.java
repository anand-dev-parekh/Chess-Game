package game;
import java.util.ArrayList;


public class Board {

    public Base[][] matrix;
    public ArrayList<int[]> prevMoves;

    public Board(Base[][] matrix, ArrayList<int[]> prevMoves){
        this.matrix = matrix;
        this.prevMoves = prevMoves;
    }

    
}
