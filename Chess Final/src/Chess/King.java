package Chess;

public final class King extends Pieces {

    private static final int[][] squares = {
            {0, 1, 0, 0, 1, 0},
            {0, 1, 0, 1, 0, 0},
            {0, 1, 1, 0, 0, 0},
            {0, 1, 0, 1, 0, 0},
            {0, 1, 0, 0, 1, 0},
            {0, 1, 0, 0, 0, 1}
    };

    public King(Board board, boolean isWhite) {
        super(board, isWhite, squares);
    }


    @Override
    boolean AllowedMove(Coord start, Coord end) {
        return AllowedLocation(end) && noAdjKing(end) && !checkKing(end)
                && Math.abs(end.x()-start.x()) <=1 && Math.abs(end.y()-start.y()) <=1
                && Math.abs(end.x() - start.x()) + Math.abs(end.y() - start.y()) != 0;
    }

    boolean checkKing(Coord coord){
        return CFS(coord,0,-1) || CFS (coord, 0,1)
                || CFS(coord,1,0) || CFS(coord,-1,0)
//ALL STRAIGHT LINE CHECKS
                ||CFD(coord,-1,-1) || CFD(coord,-1,1)
                ||CFD(coord, 1,-1) || CFD(coord, 1,1)
        //ALL DIAGONAL CHECKS
                ||PawnCheck(coord) || KnightCheck(coord);


    }
    private boolean InBounds(Coord coord){return CheckInBounds(coord.x(),coord.y());}
    private boolean CheckInBounds(int  x, int y){
        return x>=0 && y>= 0 && x<8 && y<8;
    }
    private boolean CFS(Coord coord, int xG, int yG){
        coord = Coord.instance(coord.x() +xG, coord.y() + yG);
        while (InBounds(coord)){
            var piece = board().getBoard(coord);
            var SMP = piece instanceof Rook ||piece instanceof Queen;
            if(SMP && piece.isWhite() != isWhite()){
                return true;
            }
            if(piece != null){
                return false;}
            coord= Coord.instance(coord.x() +xG, coord.y() + yG);
        }
        return false;

    }
    //EXACT SAME INSTANCING THE BSHOP INSTEAD
    private boolean CFD(Coord coord, int xG, int yG){
        coord = Coord.instance(coord.x() +xG, coord.y() + yG);
        while (InBounds(coord)){
            var piece = board().getBoard(coord);
            var SMP = piece instanceof Bshop ||piece instanceof Queen;
            if(SMP && piece.isWhite() != isWhite()){
                return true;
            }
            if(piece != null){
                return false;}
            coord= Coord.instance(coord.x() +xG, coord.y() + yG);
        }
        return false;

    }
    private boolean PawnCheck(Coord coord){
        if (!CheckInBounds(coord.x()-1,coord.y()-1)){
            return false;
        }
        if (!CheckInBounds(coord.x()+1,coord.y()-1)){
            return false;
        }
        var coord1 = Coord.instance(coord.x()-1,coord.y()-1);
        var coord2 = Coord.instance(coord.x()+1,coord.y()-1);
        var piece1 = board().getBoard(coord1);
        var piece2 = board().getBoard(coord2);
        return piece1 instanceof Pawn && piece1.isWhite() !=isWhite()
                || piece2 instanceof Pawn && piece2.isWhite() !=isWhite();

    }
    private boolean KnightCheck(Coord coord){
        return (HorseLocation(coord.x()-2,coord.y()-1))
                ||(HorseLocation(coord.x()-1,coord.y()-2))
                ||(HorseLocation(coord.x()-2,coord.y()+1))
                ||(HorseLocation(coord.x()-1,coord.y()+2))
                ||(HorseLocation(coord.x()+2,coord.y()-1))
                ||(HorseLocation(coord.x()+1,coord.y()-2))
                ||(HorseLocation(coord.x()+2,coord.y()+1))
                ||(HorseLocation(coord.x()+1,coord.y()+2));
    }

    private boolean HorseLocation(int x, int y){
        if (!CheckInBounds(x,y)){
            return false;
        }
        var coord1 = Coord.instance(x,y);

        var piece1 = board().getBoard(coord1);

        return piece1 instanceof Horse && piece1.isWhite() !=isWhite();

    }
    private boolean noAdjKing(Coord coord){
        return KingLocation(coord.x()-1, coord.y()-1)
                &&KingLocation(coord.x(), coord.y()-1)
                &&KingLocation(coord.x()+1, coord.y()-1)
                &&KingLocation(coord.x()-1, coord.y())
                &&KingLocation(coord.x()+1, coord.y())
                &&KingLocation(coord.x()-1, coord.y()+1)
                &&KingLocation(coord.x(), coord.y()+1)
                &&KingLocation(coord.x()+1, coord.y()+1);

    }


    private boolean KingLocation(int x, int y){
        if (x<0 || x>=8 || y<0  || y>=8 ){
            return true;
        }
        var coord1 = Coord.instance(x,y);

        var piece1 = board().getBoard(coord1);

        return !(piece1 instanceof King && piece1.isWhite() !=isWhite());
}}