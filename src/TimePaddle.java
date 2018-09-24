import java.awt.*;
import java.util.ArrayList;

public class TimePaddle extends Paddle {

    private int velocity;

    public TimePaddle(int _xPos, int _yPos) {
        xLock = true;
        xPos = _xPos;
        yPos = _yPos;
        width = 25;
        height = 100;
        name = "TIMEPADDLE";
        maxhp = 3500;
        hp = 3500;
        velocity = 0;
        color = Color.ORANGE;
    }

    public void update(Graphics window, ArrayList<PongObject> objects) {
        changeYPos(window, velocity);
        hp -= 1;
    }

    public void heal(int a) {
    }

    public void damage(int a) {
    }

    public void updateHP() {

    }
    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }





}
