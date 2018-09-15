import java.awt.*;
import java.util.ArrayList;

public class Ball extends PongObject {

    private int direction = -1;

    Ball(int x, int y) {
        xPos = x;
        yPos = y;
        width = 25;
        height = 25;
        name = "BALL";
        color = Color.RED;
    }

    @Override
    public void draw(Graphics window) {
        window.setColor(color);
        window.fillOval(xPos, yPos, 25, 25);
    }


    public void update(Graphics window, ArrayList<PongObject> objects) {
        if (touching(objects)) {
            direction *= -1; //
        }
        if (checkOOB(window)) {
            xPos = 1280/2;
        }
        xPos += 5*direction;
    }


    private boolean touching(ArrayList<PongObject> objects) {
        for (PongObject object : objects) {
            if (object != this && object.isSolid()) {
                int otherX = object.getXPos();
                int otherY = object.getYPos();
                if ( yPos > otherY && yPos < otherY+object.getHeight() && xPos > otherX && xPos < otherX+object.getWidth() ) {
                    return true;
                }
            }

        }
        return false;
    }

    private boolean touching(PongObject object) {
        if (object != this && object.isSolid()) {
                int otherX = object.getXPos();
                int otherY = object.getYPos();
            return yPos > otherY && yPos < otherY + object.getHeight() && xPos > otherX && xPos < otherX + object.getWidth();
            }
            return false;
        }


    private boolean checkOOB(Graphics window) {
        //int width = (int)window.getClipBounds().getWidth();
        int width = 1280;
        if ( xPos < 0 ) {
            System.err.println("right player scored");
            direction *= -1;
            return true;
        } else if ( xPos > width ) {
            System.err.println("left player scored");
            direction *= -1;
            return true;
        }
        return false;
    }


}
