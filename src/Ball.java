import java.awt.*;
import java.util.ArrayList;

public class Ball extends PongObject {

    private int direction = -1;
    private double yVel = 0;


    Ball(int x, int y) {
        xPos = x;
        yPos = y;
        width = 25;
        height = 25;
        name = "BALL";
        color = Color.RED;
        maxhp = 1;
        hp = 999;
        invincible = true;
    }

    @Override
    public void draw(Graphics window) {
        if (visible) {
            window.setColor(color);
            window.fillOval(xPos, yPos, 25, 25);
        }
    }


    public void update(Graphics window, ArrayList<PongObject> objects) {
        if (touching(objects)) {
            direction *= -1; //
        }
        if (checkOOB(window, objects)) {
            xPos = 1280/2;
        }
        xPos += 5*direction;
        yPos += yVel;
    }

    @Override
    public boolean touching(ArrayList<PongObject> objects) {
        for (PongObject object : objects) {
            if (touching(object)) {
                yVel = calculateBounceAngle(object);
                object.damage((int)yVel/3);
                return true;
                }
            }
        return false;
    }


    private boolean checkOOB(Graphics window, ArrayList<PongObject> objects) {
        int height = 720;
        int width = 1280;
        PongObject player1 = objects.get(0);
        PongObject player2 = objects.get(1);

        if ( xPos < 0 ) {
            player1.damage(20);
            player2.heal(10);
            direction *= -1;
            yVel = 0;
            yPos = height/2;
            return true;
        } else if ( xPos > width ) {
            player2.damage(20);
            player1.heal(10);
            direction *= -1;
            yVel = 0;
            yPos = height/2;
            return true;
        }

        if ( yPos < 0 || yPos > height) {
            yVel *= -1;
            return false;
        }
        return false;
    }

    public double getyVel() {
        return yVel;
    }

    public void setyVel(double vel) {
        yVel = vel;
    }

    @Override
    public String toString() {
        return String.format("%s: X: %d Y: %d Xb: %d Yb: %d yVel: %s Damage: %s", name, xPos, yPos, xPos+width, xPos+height, yVel, Math.abs(yVel/3));
    }


}
