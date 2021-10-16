import pieces.king;
import pieces.queen;

class chess{
    public static void main(String[] args)
    {

        int[] test = {0, 1};



        king king1 = new king("White", test, "king");
        queen queen1 = new queen("White", test, "queen");

        System.out.println(queen1.color + " " + queen1.position + " " + king1.color);




    }
}