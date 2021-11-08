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

class TerminalChess{
    public static void main(String[] args)
    {

        Base blackKing = new King("black", 0, 4, "king");
        Base blackQueen = new Queen("black", 0, 3, "queen");
        Base blackBishop1 = new Bishop("black", 0, 2, "bishop");
        Base blackBishop2 = new Bishop("black", 0, 5, "bishop");
        Base blackKnight2 = new Knight("black", 0, 6, "knight");
        Base blackKnight1 = new Knight("black", 0, 1, "knight");
        Base blackRook1 = new Rook("black", 0 , 0, "rook");
        Base blackRook2 = new Rook("black", 0, 7, "rook");
        Base blackPawn0 = new Pawn("black", 1, 0, "pawn");
        Base blackPawn1 = new Pawn("black", 1, 1, "pawn");
        Base blackPawn2 = new Pawn("black", 1, 2, "pawn");
        Base blackPawn3 = new Pawn("black", 1, 3, "pawn");
        Base blackPawn4 = new Pawn("black", 1, 4, "pawn");
        Base blackPawn5 = new Pawn("black", 1, 5, "pawn");
        Base blackPawn6 = new Pawn("black", 1, 6, "pawn");
        Base blackPawn7 = new Pawn("black", 1, 7, "pawn");
        
        Base whiteKing = new King("white", 7, 4, "king");
        Base whiteQueen = new Queen("white", 7, 3, "queen");
        Base whiteBishop1 = new Bishop("white", 7, 2, "bishop");
        Base whiteBishop2 = new Bishop("white", 7, 5, "bishop");
        Base whiteKnight2 = new Knight("white", 7, 6, "knight");
        Base whiteKnight1 = new Knight("white", 7, 1, "knight");
        Base whiteRook1 = new Rook("white", 7 , 0, "rook");
        Base whiteRook2 = new Rook("white", 7, 7, "rook");
        Base whitePawn0 = new Pawn("white", 6, 0, "pawn");
        Base whitePawn1 = new Pawn("white", 6, 1, "pawn");
        Base whitePawn2 = new Pawn("white", 6, 2, "pawn");
        Base whitePawn3 = new Pawn("white", 6, 3, "pawn");
        Base whitePawn4 = new Pawn("white", 6, 4, "pawn");
        Base whitePawn5 = new Pawn("white", 6, 5, "pawn");
        Base whitePawn6 = new Pawn("white", 6, 6, "pawn");
        Base whitePawn7 = new Pawn("white", 6, 7, "pawn");

        Base[][] matrix = {
                          {blackRook1, blackKnight1, blackBishop1, blackQueen, blackKing, blackBishop2, blackKnight2, blackRook2}, 
                          {blackPawn0, blackPawn1, blackPawn2, blackPawn3, blackPawn4, blackPawn5, blackPawn6, blackPawn7}, 
                          {null, null, null, null, null, null, null, null}, 
                          {null, null, null, null, null, null, null, null},
                          {null, null, null, null, null, null, null, null},
                          {null, null, null, null, null, null, null, null},
                          {whitePawn0, whitePawn1, whitePawn2, whitePawn3, whitePawn4, whitePawn5, whitePawn6, whitePawn7},
                          {whiteRook1, whiteKnight1, whiteBishop1, whiteQueen, whiteKing, whiteBishop2, whiteKnight2, whiteRook2},
                          }; 

        ArrayList<Base[][]> prevBoards = new ArrayList<Base[][]>();
        Board boardObject = new Board(matrix, prevBoards);

        Scanner input = new Scanner(System.in);
        int x, y, newX, newY;
        String turn = "white";

        printt(boardObject.matrix);
        while (true){
            
            
            if (boardObject.isGameOver(turn)) break;
            //if (board.isCheckmate(turn)) break;
            //if (board.isDraw(turn)) break;




            System.out.println("Coordanites of piece you want to move: ");
            y = input.nextInt(); x = input.nextInt();
            System.out.println("\n Coordanites of place u want piece to move ");
            newY = input.nextInt(); newX = input.nextInt();

            if (boardObject.matrix[y][x] == null || !boardObject.matrix[y][x].color.equals(turn)) System.out.println("Can't move that piece");
            else if (boardObject.matrix[y][x].validMove(boardObject, newY, newX) && !boardObject.matrix[y][x].inCheck(boardObject, newY, newX))
            {
                if (boardObject.matrix[newY][newX] == null) boardObject.fiftyMove++; //Checks if no taking was done
                else boardObject.fiftyMove = 0; // Resets fifty move rule since there was a taking
                
                
                if (boardObject.matrix[y][x].castle){
                    if (newX > x){
                        boardObject.matrix[y][x + 1] = boardObject.matrix[y][x + 3];
                        boardObject.matrix[y][x + 3] = null;
                        boardObject.matrix[y][x + 1].x = x + 1;
                    }
                    else{
                        boardObject.matrix[y][x - 1] = boardObject.matrix[y][x - 4];
                        boardObject.matrix[y][x - 4] = null; 
                        boardObject.matrix[y][x - 1].x = x - 1;
                    }
                }

                boardObject.matrix[y][x].x = newX;
                boardObject.matrix[y][x].y = newY; 

                boardObject.matrix[newY][newX] = boardObject.matrix[y][x];
                boardObject.matrix[y][x] = null;

                if (boardObject.matrix[newY][newX].enPessant){
                    if (boardObject.matrix[newY][newX].color == "white"){
                        boardObject.matrix[newY + 1][newX] = null;
                    }
                    else{
                    
                        boardObject.matrix[newY - 1][newX] = null;
                    }
                    boardObject.fiftyMove = 0; //Resets fifty move rule since en pessant
                    boardObject.matrix[newY][newX].enPessant = false; // Resets enpessant to false
                }
                if (boardObject.matrix[newY][newX].promotion){
                    System.out.println("Promotion Working");
                    boardObject.matrix[newY][newX].promotion = false;
                }

                boardObject.matrix[newY][newX].hasMoved = true;


                Base[][] tempBoardMatrix = new Base[8][8];
        
                for (int i = 0; i < boardObject.matrix.length; i++){
                    for (int j = 0; j < boardObject.matrix[i].length; j++){
                        tempBoardMatrix[i][j] = boardObject.matrix[i][j]; 
                    }
                }
                boardObject.prevBoards.add(tempBoardMatrix); 

                if (turn.equals("white")) turn = "black";
                else turn = "white";

                printt(boardObject.matrix);
            }
            else 
            {
                System.out.println("Not valid move");
                boardObject.matrix[y][x].castle = false;
                boardObject.matrix[y][x]. promotion = false;
            }
        }

        System.out.println("OUTTA DA LOOP");
        input.close();
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

                if (matrix[y][x] == null) System.out.print("       ||");
                else System.out.print(" " + matrix[y][x].color.charAt(0) + matrix[y][x].piece.substring(0, 4) + " ||");

            }
            System.out.println("\n-------------------------------------------------------------------------------");
        }
    }
}
