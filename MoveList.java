public enum MoveList
{
    UP (0,1),
    DOWN(0,-1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    UP_LEFT(-1, 1),
    UP_RIGHT(1, 1),
    DOWN_LEFT(-1, -1),
    DOWN_RIGHT(1, -1),
    DOUBLE_DOWN(0, -2),
    DOUBLE_UP(0, 2);

    private int x;
    private int y;

    private void setX(int x){this.x = x;}
    public int getX(){return this.x;}

    private void setY(int y){this.y = y;}
    public int getY(){return this.y;}

    public boolean isEqual(MoveList m) {return ( this.x == m.getX() ) && ( this.y == m.getY() ); }
    public boolean isEqual(int x, int y) {return ( this.x == x ) && ( this.y == y ); }
    public boolean isEqual(int oldX, int oldY, int newX, int newY)
    {
        int xIn = newX - oldX;
        int yIn = newY - oldY;
        return this.isEqual(xIn, yIn);
    }

    private MoveList(int x, int y)
    {
        this.x = x;
        this.y = y;
    }
}
