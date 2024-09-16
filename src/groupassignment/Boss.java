package groupassignment;

import java.util.Random;

public class Boss extends Object
{
    int live; 
    String state, nextState;
    int timer;
    //used for visual effect
    float alpha;
    
    public Boss(double x, double y, double speed)
    {
        super(x,y,speed);
        alpha = 0;
        state = "stand_by";
    }
}
