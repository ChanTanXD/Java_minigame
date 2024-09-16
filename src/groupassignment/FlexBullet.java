package groupassignment;

public class FlexBullet
{
    /*variable name with Mod modifies the value of corresponding variable
    e.g. if xMod = -2, then x will likely tilt towards negative value
    1 is default value for xMod and yMod, 0 for speedMod*/
    double x, y, speed, xMod, yMod, speedMod;
    //change pic every 100 millisec
    int frame;
    boolean enlarge;
    public FlexBullet(double x, double y, double xMod, double yMod,
            double speed, double speedMod, int frame)
    {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.xMod = xMod;
        this.yMod = yMod;
        this.speedMod = speedMod;
        this.frame = frame;
    }
}
