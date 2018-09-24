import java.awt.*;
import java.util.ArrayList;

public class AIPaddle extends Paddle {

    public AIPaddle(int _xPos, int _yPos) {
        xLock = true;
        xPos = _xPos;
        yPos = _yPos;
        width = 25;
        height = 85;
        name = "AIPADDLE";
    }

    public void update(Graphics window, ArrayList<PongObject> objects) {
        PongObject ball = objects.get(2);
        if ( hpchange <= -2 ) {
            changeYPos(window, 15);
        }
        if (yPos < ball.getYPos()) {
            changeYPos(window, 15);
        } else if (yPos > ball.getYPos()) {
            changeYPos(window, -15);
        }
    }





}
