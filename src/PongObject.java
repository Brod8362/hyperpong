import java.awt.*;
import java.util.ArrayList;

abstract class PongObject {
    protected int xPos, yPos;
    protected boolean xLock;
    protected int width, height;
    protected String name = null;
    protected boolean solid = true;
    protected Color color = Color.WHITE;



    protected int getXPos() {
        return xPos;
    }
    protected int getYPos() {
        return yPos;
    }

    protected int getWidth() {
        return width;
    }

    protected int getHeight() {
        return height;
    }

    protected void draw(Graphics window) {
        System.err.println("Object "+name+" has no draw method!");
    }

    protected void setXPos(Graphics window, int pos) {
        if (xLock) {
            throw new RuntimeException("Disallowed");
        }
        if (isOOB(window, pos, 1)) {
            return;
        }

        xPos = pos;
    }
    protected void setYPos(Graphics window, int pos) {
        if (isOOB(window, 1, pos) ) {
            return;
        }
            yPos = pos;
    }
    protected void setPos(Graphics window, int x, int y) {
        if (isOOB(window, x, y) ) {
            return;
        }
        xPos = x;
        yPos = y;
    }

    protected void changeXPos(Graphics window, int pos) {
        if (xLock) { throw new RuntimeException("Disallowed"); }
        if (isOOB(window, pos, 1)) {
            return;
        }
        xPos += pos;
    }


    protected void changeYPos(Graphics window, int pos) {
        if (isOOB(window, 1, pos)) {
            return;
        }
        yPos += pos;
    }

    protected boolean isOOB(Graphics window, int xPos, int yPos) {
        return false; //FIX THIS CODE LATER
        /*
        int frameHeight = (int)window.getClipBounds().height;
        System.err.print("why the fuck this code not run");
        int frameWidth = (int)window.getClipBounds().getWidth();
        if (yPos < 0 || yPos > frameHeight ) {
            System.err.print("Object tried to move out of bounds");
            return true;
        }
        else if (xPos < 0 || xPos > frameWidth ) {
            System.err.print("Object tried to move out of bounds");
            return true;
        } else {
            return false;
        }*/
    }

    protected boolean touching(ArrayList<PongObject> objects) {
        for (PongObject object : objects) {
            if (touching(object)) {
                return true;
            }
        }
        return false;
    }

    protected boolean touching(PongObject object) {
        if (object != this && object.isSolid()) {
            int otherX = object.getXPos();
            int otherY = object.getYPos();
            int otherWidth = object.getWidth();
            int otherHeight = object.getHeight();
            if ( yPos > otherY && yPos < otherY + otherHeight && xPos > otherX && xPos < otherX + otherWidth
                    || xPos+width < otherX+width && xPos+width > otherX && yPos+height > otherY && yPos+height < otherY+otherHeight ) {
                return true;
            }
        }
        return false;
    }




    protected void update() {
    }

    protected void update(Graphics window, ArrayList<PongObject> objects) {

    }
    @Override
    public String toString() {
        return String.format("%s: X: %d Y: %d Xb: %d Yb: %d ", name, xPos, yPos, xPos+width, xPos+height);
    }

    protected boolean isSolid() {
        return solid;
    }

    protected double calculateBounceAngle(PongObject object) {
        int sizeY = object.getYPos()-object.getHeight();
        int sizeX = object.getWidth()-object.getXPos(); //currently useless

        //dist from top
        int dft = yPos-object.getYPos();
        //percent from top
        double pft = (double)dft/sizeY;
        //get angle of bounce
        double angle = pft*180;
        return angle;

    }


    protected void drawHitbox(Graphics window) {
        window.setColor(color);
        window.drawRect(xPos, yPos, width, height);
    }
}

