package Chess;

import java.util.Objects;

public class Coord {
    private static final Coord[][] Loc = new Coord[10][10];
    private static int x;
    private static int y;

    public Coord(int x, int y) {
        this.x = x;
        this.y = y;
    }




    public static Coord instance(int x, int y){
        if (Loc[y+1][x+1] == null){
            Loc[y+1][x+1] = new Coord(x,y);
        }
        return Loc[y+1][x+1];
    }
    public static int x(){return x;}
    public static int y(){return y;}
@Override
    public boolean equals(Object o){
        if(o==this){
            return true;
        }
        if(!(o instanceof Coord)){
            return false;
        }
        var coord = (Coord) o;
        return x == coord.x && y == coord.y;
}
@Override
    public int hashCode(){return Objects.hash(x,y);}
}




