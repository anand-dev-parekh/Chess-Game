package pieces;

public class king extends base{

    public king(String color, int[] position,  String piece)
    {
        super(color, position, piece);

    }
    

    public boolean inCheck(base[] boardinp)
    {
        return false;
    }
}
