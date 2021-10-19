import java.util.ArrayList;
import game.Board;
import game.Base;
import game.King;
import game.Queen;
import game.Rook;
import game.Bishop;
import game.Knight;
import game.Pawn;
import java.util.Scanner;

class Chess{
    public static void main(String[] args)
    {

        King blackKing = new King("black", 0, 4, "king");
        Queen blackQueen = new Queen("black", 0, 3, "queen");
        Bishop blackBishop1 = new Bishop("black", 0, 2, "bishop");
        Bishop blackBishop2 = new Bishop("black", 0, 5, "bishop");
        Knight blackKnight2 = new Knight("black", 0, 6, "knight");
        Knight blackKnight1 = new Knight("black", 0, 1, "knight");
        Rook blackRook1 = new Rook("black", 0 , 0, "rook");
        Rook blackRook2 = new Rook("black", 0, 7, "rook");
        Pawn blackPawn0 = new Pawn("black", 1, 0, "pawn", false);
        Pawn blackPawn1 = new Pawn("black", 1, 1, "pawn", false);
        Pawn blackPawn2 = new Pawn("black", 1, 2, "pawn", false);
        Pawn blackPawn3 = new Pawn("black", 1, 3, "pawn", false);
        Pawn blackPawn4 = new Pawn("black", 1, 4, "pawn", false);
        Pawn blackPawn5 = new Pawn("black", 1, 5, "pawn", false);
        Pawn blackPawn6 = new Pawn("black", 1, 6, "pawn", false);
        Pawn blackPawn7 = new Pawn("black", 1, 7, "pawn", false);

        King whiteKing = new King("white", 7, 4, "king");
        Queen whiteQueen = new Queen("white", 7, 3, "queen");
        Bishop whiteBishop1 = new Bishop("white", 7, 2, "bishop");
        Bishop whiteBishop2 = new Bishop("white", 7, 5, "bishop");
        Knight whiteKnight2 = new Knight("white", 7, 6, "knight");
        Knight whiteKnight1 = new Knight("white", 7, 1, "knight");
        Rook whiteRook1 = new Rook("white", 7 , 0, "rook");
        Rook whiteRook2 = new Rook("white", 7, 7, "rook");
        Pawn whitePawn0 = new Pawn("white", 6, 0, "pawn", false);
        Pawn whitePawn1 = new Pawn("white", 6, 1, "pawn", false);
        Pawn whitePawn2 = new Pawn("white", 6, 2, "pawn", false);
        Pawn whitePawn3 = new Pawn("white", 6, 3, "pawn", false);
        Pawn whitePawn4 = new Pawn("white", 6, 4, "pawn", false);
        Pawn whitePawn5 = new Pawn("white", 6, 5, "pawn", false);
        Pawn whitePawn6 = new Pawn("white", 6, 6, "pawn", false);
        Pawn whitePawn7 = new Pawn("white", 6, 7, "pawn", false);

        Base[][] matrix = {{blackRook1, blackKnight1, blackBishop1, blackQueen, blackKing, blackBishop2, blackKnight2, blackRook2}, 
                          {blackPawn0, blackPawn1, blackPawn2, blackPawn3, blackPawn4, blackPawn5, blackPawn6, blackPawn7}, 
                          {null, null, null, null, null, null, null, null}, 
                          {null, null, null, null, null, null, null, null},
                          {null, null, null, null, null, null, null, null},
                          {null, null, null, null, null, null, null, null},
                          {whitePawn0, whitePawn1, whitePawn2, whitePawn3, whitePawn4, whitePawn5, whitePawn6, whitePawn7},
                          {whiteRook1, whiteKnight1, whiteBishop1, whiteQueen, whiteKing, whiteBishop2, whiteKnight2, whiteRook2},
                                }; 

        ArrayList<Base[][]> prevBoards = new ArrayList<Base[][]>();
        Board board = new Board(matrix, prevBoards);

        Scanner input = new Scanner(System.in);
        int x, y, newX, newY;

        printt(board.matrix);
        while (true){
            System.out.println("Coordanites of piece you want to move: ");
            y = input.nextInt(); x = input.nextInt();
            System.out.println("\n Coordanites of place u want piece to move ");
            newY = input.nextInt(); newX = input.nextInt();

            if (board.matrix[y][x] == null) System.out.println("Selected piece of nothing");
            else if (board.matrix[y][x].validMove(board, newY, newX) && !board.matrix[y][x].inCheck(board.matrix, newY, newX))
            {
                board.matrix[y][x].x = newX;
                board.matrix[y][x].y = newY;

                board.matrix[newY][newX] = board.matrix[y][x];
                board.matrix[y][x] = null;
                if (board.matrix[newY][newX].enPessant){
                    if (board.matrix[newY][newX].color == "white"){
                        board.matrix[newY + 1][newX] = null;
                    }
                    else{
                        board.matrix[newY - 1][newX] = null;
                    }
                }


                Base[][] tempBoard = {{null, null, null, null, null, null, null, null},{null, null, null, null, null, null, null, null},{null, null, null, null, null, null, null, null},{null, null, null, null, null, null, null, null},{null, null, null, null, null, null, null, null},{null, null, null, null, null, null, null, null},{null, null, null, null, null, null, null, null}, {null, null, null, null, null, null, null, null}};
        
                for (int i = 0; i < board.matrix.length; i++){
                    for (int j = 0; j < board.matrix[i].length; j++){
                        tempBoard[i][j] = board.matrix[i][j]; 
                    }
                }
                board.prevBoards.add(tempBoard); // board.prevboards.add(new Base[][])

                printt(board.matrix);
            }
            else 
            {
                System.out.println("Not valid move");
            }
        }
    }


    static void printt(Base[][] matrix){
        System.out.print("   ");
        for (int i = 0; i < 8; i++){
            System.out.print("   " + i + "   ||");
        }
        System.out.println("\n-------------------------------------------------------------------------------");

        for (int y = 0; y < matrix.length; y++){
            System.out.print(y + "||");
            for (int x = 0; x < matrix.length; x++){

                if (matrix[y][x] == null) System.out.print(" nulll ||");
                else System.out.print(" " + matrix[y][x].color.charAt(0) + matrix[y][x].piece.substring(0, 4) + " ||");

            }
            System.out.println("\n-------------------------------------------------------------------------------");
        }

    }
}