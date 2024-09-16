package groupassignment;

public class Player extends Object
{
    boolean Up, Down, Left, Right, isInvincible;
    
    public Player(double x, double y, double speed)
    {
        super(x, y, speed);
        Up = false;
        Down = false;
        Left = false;
        Right = false;
        isInvincible = false;
    }
}
