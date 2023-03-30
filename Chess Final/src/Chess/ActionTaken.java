package Chess;

import java.util.Arrays;
import java.util.Objects;

final class ActionTaken {
    private final Pieces[][] board;
    private final Coord enpassant;

    private final int hash;

    ActionTaken(Board board, Coord enpassant){
        this.board = board.copy();
        this.enpassant = enpassant;
        hash = 31* Objects.hash(this.enpassant) + Arrays.deepHashCode(this.board);
    }

    @Override
    public boolean equals(Object f){
        if (f==this){
            return true;}

        if(!(f instanceof ActionTaken)){
            return false;
        }
        var history = (ActionTaken) f;
        for(int i=0;i<8;i++){
            if(!Arrays.equals(board[i], history.board[i])){
                return false;
            }

        }
        if (enpassant == null){
            return history.enpassant == null;
        }
        return enpassant.equals(history.enpassant);
    }

@Override
    public int hashCode(){return hash;}
}
