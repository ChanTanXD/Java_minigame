package groupassignment;

public class Bullet
{
    double x, y, speed;
    boolean isFire;
    
    public Bullet(double x, double y, double speed)
    {
        this.x = x;
        this.y = y;
        this.speed = speed;
        isFire = false;
    }
}
