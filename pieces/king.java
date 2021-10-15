package pieces;

public class king extends base{

    public boolean inCheck;
    public king(String color, int[] position, boolean inCheck)
    {
        super(color, position);
        this.inCheck = inCheck;
    }
}
