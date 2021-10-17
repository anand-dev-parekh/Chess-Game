package game;
import java.util.ArrayList;


public class Board {

    public Base[][] matrix;
    public ArrayList<Integer> prevMoves;

    public Board(Base[][] matrix, ArrayList<Integer> prevMoves){
        this.matrix = matrix;
        this.prevMoves = prevMoves;
    }

    
}
