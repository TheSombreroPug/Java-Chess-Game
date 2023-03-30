package Chess;

public final class Rook extends Pieces{

    private static final int[][] squares = {
            {0,0,1,1,1,0},
            {0,1,0,0,1,0},
            {0,1,1,1,1,0},
            {0,1,0,1,0,0},
            {0,1,0,0,1,0},
            {0,1,0,0,0,1}
    };

    public Rook(Board board, boolean isWhite){super(board,isWhite,squares);}

    @Override
    boolean AllowedMove(Coord start, Coord end) {
        return  AllowedRookMove(start,end) && NoKingInCheck(start,end);
    }


    boolean AllowedRookMove(Coord start, Coord end) {
        if (start.equals(end) || (start.x() != end.x() && start.y() != end.y())) {
            return false;
        }
        if (start.x() == end.x()) {
//reusing the for loop from bshop
            for (int i = (Math.min(start.x(), end.x())) + 1; i < Math.max(start.x(), end.x()); i++) {
                start = Coord.instance(start.x(), i);
                if (board().getBoard(start) != null) {
                    return false;
                }
            }
        } else {
            for (int i = (Math.min(start.x(), end.x())) + 1; i < Math.max(start.x(), end.x()); i++) {
                start = Coord.instance(i, start.y());
                if (board().getBoard(start) != null) {
                    return false;
                }
            }
        }
        return AllowedLocation(end);
    }}
