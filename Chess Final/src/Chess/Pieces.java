package Chess;

import Chess.Board;
import Chess.Coord;
import Chess.*;

import java.awt.*;

public abstract class Pieces {
    private final Color[][] image = new Color[6][6]; //CHANGE THIS FOR MORE DETAIL IN PIECES
    private final Board board;
    private final boolean isWhite;
    private boolean hasMoved;


    Pieces(Board board, boolean isWhite, int[][]pixels){
        this.board = board;
        this.isWhite = isWhite;
        for (int a = 0; a<pixels.length ; a++){
            for (int b = 0; b<pixels.length ;b++){
                if(pixels[a][b] != 0){
                    image[a][b] = isWhite ? Color.RED : Color.BLUE;
                }

            }
        }
    }
    //
    abstract boolean AllowedMove(Coord start, Coord end);
    public final boolean isWhite(){return isWhite;}
    final boolean hasMoved(){return hasMoved;}
    final void setMove(){hasMoved = true;}
    final Color[][] getpiecepixels(){return image;}
    final Board board(){return board;}
    final boolean AllowedLocation(Coord coord){
        return board.getBoard(coord) == null || board.getBoard(coord).isWhite() != isWhite();
    }
//Ensures that your king isnt in check
    final boolean NoKingInCheck (Coord start, Coord end){
        var checks = board.getBoard(end);
        board.setBoard(end,this);
        board.setBoard(end,null);
        boolean isAllowed = !board.getAlliedKing().checkKing(board.locateking());
        board.setBoard(end,this);
        board.setBoard(end,checks);
        return isAllowed;
    }


}
