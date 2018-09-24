import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Paddle extends PongObject {
    private int velocity;
    public Paddle() {}

    public Paddle(int _xPos, int _yPos, boolean _xLock) {
        xLock = _xLock;
        xPos = _xPos;
        yPos = _yPos;
        width = 25;
        height = 125;
        name = "PADDLE";
    }

    public Paddle(int _xPos, int _yPos) {
        xLock = true;
        xPos = _xPos;
        yPos = _yPos;
        width = 25;
        height = 85;
        name = "PADDLE";
        maxhp = 100;
        hp = 100;
    }

    @Override
    public void draw(Graphics window) {
        if (visible) {
            window.setColor(color);
            window.fillRoundRect(xPos, yPos, width, height, 20, 50);
            drawHP(window);
        }
    }


    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    protected void update(Graphics window, ArrayList<PongObject> objects) {
        changeYPos(window, velocity);
    }



    // END SHARED METHODS


    public boolean isXLocked() {
        return xLock;
    }


}
