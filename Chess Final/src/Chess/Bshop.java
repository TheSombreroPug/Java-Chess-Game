package Chess;

public final class Bshop extends Pieces{

    private static final int[][] squares = {
            {0,1,0,0,0,0},
            {0,1,0,0,0,0},
            {0,1,1,1,1,0},
            {0,1,0,0,1,0},
            {0,1,0,0,1,0},
            {0,1,1,1,1,0}
    };

    public Bshop(Board board, boolean isWhite){super(board,isWhite,squares);}

    @Override
    boolean AllowedMove(Coord start, Coord end) {
        boolean Diagonal = Math.abs(end.y()-start.y()) == Math.abs(end.x()-start.x());
        return Diagonal && NoKingInCheck(start,end) && AllowedBishopMove(start,end);

    }

    boolean AllowedBishopMove(Coord start, Coord end){

        for (int i = (Math.min(start.x(),end.x()))+1;i<Math.max(start.x(),end.x());i++){
            start = Coord.instance(start.x() + Integer.signum(end.x()-start.x()),start.y() + Integer.signum(end.y()-start.y()));
            if (board().getBoard(start) != null){
                return false;
            }

        }
        return AllowedLocation(end);
    }
}
