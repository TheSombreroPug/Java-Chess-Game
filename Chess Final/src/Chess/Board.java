package Chess;

import java.awt.*;
import java.util.*;
public class Board{

    static  final  int KINGX = (7)/2+1;
    private final Pieces[][] board = new Pieces[8][8];
    private final King whiteking = new  King(this, true);
    private final King blackking = new  King(this, true);
    private Coord whitekingloc;
    private Coord blackkingloc;
    private boolean iswhite = true;

    Board(){
        NonPawn(0,false);
        setpawns(1,false);
        setpawns(6,true);
        NonPawn(7,true);

    }

    private void NonPawn(int location, boolean colors){
        board[location][0] = new Rook(this,colors);
        board[location][7] = new Rook(this,colors);
        board[location][1] = new Horse(this,colors);
        board[location][6] = new Horse(this,colors);
        board[location][2] = new Bshop(this,colors);
        board[location][5] = new Bshop(this,colors);
        board[location][(7)/2] = new Queen(this,colors);

        if(colors){
            board[location][KINGX] = whiteking;
            whitekingloc = Coord.instance(KINGX, location);
        }
        else {
            board[location][KINGX] = blackking;
            blackkingloc = Coord.instance(KINGX, location);
        }
    }
    private void setpawns(int location, boolean colors){
        for(int i = 0; i<8;i++){
            board[location][i] = new Pawn(this, colors);
        }
    }
    Color[][] getImage(Coord coord){
        if(board[coord.y()][coord.x()] == null){
            return null;
        }
        return board[coord.y()][coord.x()].getpiecepixels();
    }

    void moveking(Coord coord){
        if(iswhite){
            whitekingloc = coord;
        }else {
            blackkingloc = coord;
        }
    }
    public King getAlliedKing(){
        return iswhite ? whiteking : blackking;
    }
    public Coord locateking(){
        return iswhite ? whitekingloc : blackkingloc;
    }
    Pieces getAlliedPiece(Coord coord){
        var Pieces = board[coord.y()][coord.x()];
        if (Pieces == null || Pieces.isWhite()   != iswhite){
            return null;
        }
        return Pieces;
    }
    public Pieces getBoard(Coord coord){
        return board[coord.y()][coord.x()];
    }

     public void setBoard(Coord coord, Pieces pieces){

        board[coord.y()][coord.x()] = pieces;
    }

    boolean isLightTile(Coord coord){
        return ((coord.x()+coord.y())%2 == 0^!iswhite);
    }

    boolean isAlly(Pieces pieces){
        return iswhite = pieces.isWhite();
    }


    Pieces[][] copy(){
        var copys = new Pieces[8][8];
        for(int i=0; i<8;i++){
            copys[i] = Arrays.copyOf(board[i],8);
        }
        return copys;
    }

    void flip(){
        iswhite = !iswhite;

        whitekingloc = Coord.instance(whitekingloc.x(),7-whitekingloc.y());
        blackkingloc = Coord.instance(blackkingloc.x(),7-blackkingloc.y());
        for(int i=0; i< 4; i++){
            var temp = board[7-i];
            board[7-i]=board[i];
            board[i] = temp;
        }

    }

    // add the way to promo pieces
    Pieces getpromopiece (AllowedPromo promo){
        switch (promo){
            case QUEEN:
                return new Queen(this,iswhite);
            case ROOK:
                return new Rook(this,iswhite);
            case BISHOP:
                return new Bshop(this,iswhite);
                case HORSE:
                    return new Horse(this,iswhite);

            default:throw new IllegalStateException("Invalid");
        }
    }
}







