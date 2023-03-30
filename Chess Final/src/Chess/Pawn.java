package Chess;

final class Pawn extends Pieces{
    private static final int[][] squares = {
        {0,1,1,1,1,0},
        {0,1,0,0,1,0},
        {0,1,1,1,1,0},
        {0,1,0,0,0,0},
        {0,1,0,0,0,0},
        {0,1,0,0,0,0}
    };

    Pawn(Board board, boolean isWhite){super(board,isWhite,squares);}

    @Override
    boolean AllowedMove(Coord start, Coord end) {
        boolean Forward = board().getBoard(end) == null && end.x() == start.x();
        boolean OneBlock = end.y() == start.y() -1;
        var oneBelow = Coord.instance(start.x(),start.y()-1);
        boolean TwoStep = end.y() == start.y()-2 && board().getBoard(oneBelow) == null && !hasMoved();
        boolean ForwardAllowed = Forward && (OneBlock || TwoStep);
        boolean Capture = board().getBoard(end).isWhite() != isWhite() && end.y() == start.y()-1 && Math.abs(end.x()- start.x()) ==1;
        return (ForwardAllowed || Capture)&& NoKingInCheck(start,end);

    }
}
