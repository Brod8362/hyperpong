import javax.swing.*;
import java.awt.*;

public class Paddle extends PongObject {



    public Paddle(int _xPos, int _yPos, boolean _xLock) {
        xLock = _xLock;
        xPos = _xPos;
        yPos = _yPos;
        width = 25;
        height = 85;
        name = "PADDLE";
    }

    public Paddle(int _xPos, int _yPos) {
        xLock = true;
        xPos = _xPos;
        yPos = _yPos;
        width = 25;
        height = 125;
        name = "PADDLE";
    }

    @Override
    public void draw(Graphics window) {
        if (visible) {
            window.setColor(color);
            window.fillRoundRect(xPos, yPos, width, height, 20, 50);
            drawHP(window);
        }
    }




    // END SHARED METHODS


    public boolean isXLocked() {
        return xLock;
    }


}
