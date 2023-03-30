package Chess;

public final class Queen extends Pieces {

    private static final int[][] squares = {
            {0, 1, 1, 1, 1, 0},
            {0, 1, 0, 0, 1, 0},
            {0, 1, 1, 1, 1, 0},
            {0, 0, 0, 0, 1, 0},
            {0, 0, 0, 0, 1, 1},
            {0, 0, 0, 0, 1, 0}
    };

    public Queen(Board board, boolean isWhite) {
        super(board, isWhite, squares);
    }

    @Override
    boolean AllowedMove(Coord start, Coord end) {

        if(Math.abs(end.y()-start.y()) == Math.abs(end.x()-start.x())){
        return  AllowedDiagonalMove(start,end) && NoKingInCheck(start,end);}

        return  AllowedLINEMove(start,end) && NoKingInCheck(start,end);
    }

    boolean AllowedLINEMove(Coord start, Coord end) {
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
    }
    boolean AllowedDiagonalMove(Coord start, Coord end){

        for (int i = (Math.min(start.x(),end.x()))+1;i<Math.max(start.x(),end.x());i++){
            start = Coord.instance(start.x() + Integer.signum(end.x()-start.x()),start.y() + Integer.signum(end.y()-start.y()));
            if (board().getBoard(start) != null){
                return false;
            }

        }
        return AllowedLocation(end);
    }
}