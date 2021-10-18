package game;
import java.util.ArrayList;


public class Board {

    public Base[][] matrix;
    public ArrayList<Base[][]> prevBoards;

    public Board(Base[][] matrix, ArrayList<Base[][]> prevBoards){
        this.matrix = matrix;
        this.prevBoards = prevBoards;
    }
    
}
