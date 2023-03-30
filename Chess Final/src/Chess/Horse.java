package Chess;

public final class Horse extends Pieces{

    private static final int[][] squares = {
            {0,1,0,0,1,0},
            {0,1,0,0,1,0},
            {0,1,1,1,1,0},
            {0,1,1,1,1,0},
            {0,1,0,0,1,0},
            {0,1,0,0,1,0}
    };

    public Horse(Board board, boolean isWhite){super(board,isWhite,squares);}

    @Override
    boolean AllowedMove(Coord start, Coord end) {
        boolean HorseMove = (Math.abs(end.x()-start.x())==2 && Math.abs(end.y()-start.y())==1)
                || (Math.abs(end.x()-start.x())==1 &&Math.abs(end.y()-start.y())==2);
        return HorseMove&&AllowedLocation(end)&&NoKingInCheck(start,end);

    }
}