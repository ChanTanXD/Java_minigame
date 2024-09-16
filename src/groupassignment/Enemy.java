package groupassignment;

import java.util.Random;

public class Enemy extends Object
{
    int Position;
    boolean isVisible, isReady, isDead;
    Random ran = new Random();
    int live;
    
    public Enemy(double x, double y, double speed)
    {
        super(x,y,speed);
        Position = ran.nextInt(8 + 1) + 1;
        isVisible = true;
        isReady = false;
        live = 10;
        isDead = false;
    }
}
