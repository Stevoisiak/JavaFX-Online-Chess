
public enum MoveList implements java.io.Serializable
{
    UP (0,1),
    DOWN(0,-1),
    LEFT(-1, 0),
    RIGHT(1, 0);

    private int x;
    private int y;

    private void setX(int x){this.x = x;}
    public int getX(){return this.x;}

    private void setY(int y){this.y = y;}
    public int getY(){return this.y;}

    public boolean isEqual(MoveList m) {return ( this.x == m.getX() ) && ( this.y == m.getY() ); }
    public boolean isEqual(int xIn, int yIn) {return ( this.x == xIn ) && ( this.y == yIn ); }
    public boolean isEqual(int xStart, int yStart, int xEnd, int yEnd)
    {
        int xIn = xEnd - xStart;
        int yIn = yEnd - yStart;
        return this.isEqual(xIn, yIn);
    }

    private MoveList(int xIn, int yIn)
    {
        x = xIn;
        y = yIn;
    }

}
