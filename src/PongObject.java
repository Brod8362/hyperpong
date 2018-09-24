import java.awt.*;
import java.util.ArrayList;

abstract class PongObject {
    protected int xPos, yPos;
    protected boolean xLock;
    protected int width, height;
    protected String name = null;
    protected boolean solid = true;
    protected Color color = Color.WHITE;
    protected int hp = 100;
    protected Color hpcolor = Color.GREEN;
    protected boolean visible = true;
    protected int hpchange = 0;
    protected int maxhp = 100;
    protected boolean invincible = false;
    protected boolean invulnerable = false;
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

    public boolean isInvincible() {
        return invincible;
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

    protected void makeVulnerable() {
        invulnerable = false;
    }


    protected void damage(int d) {
        if (!invulnerable) {
            hpchange -= Math.abs(d);
            invulnerable = true;
        }
    }

    public void setInvincible(boolean invincible) {
        this.invincible = invincible;
    }

    public int getMaxHP() {
        return maxhp;
    }

    public void setMaxHP(int maxhp) {
        this.maxhp = maxhp;
    }

    protected void heal(int d) {
        hpchange += Math.abs(d);
    }

    protected void drawHP(Graphics window) {
        updateHP();
        int percent = (int)(((double)hp/maxhp)*100);
        int drawY = yPos-25;
        if (drawY < 10 ) {
            drawY = yPos+height+30 ;
        }
        //set bar color
        if (invincible) {
            hpcolor = Color.MAGENTA;
        } else if (hp <= maxhp * 0.25) {
            hpcolor = Color.RED;
        } else if (hp <= maxhp * 0.5) {
            hpcolor = Color.YELLOW;
        } else {
            hpcolor = Color.GREEN;
        }

        //draw HP bars
        int hpd = hp % maxhp;
        if (hpd == 0) {
            hpd = maxhp;
        }
        int tpd = percent;
        if (tpd > 100) {
            tpd = 100;
        }

        window.setColor(hpcolor); //set color
        window.drawRect(xPos + width / 2 - 50, drawY, 100, 10); //draw empty bar
        window.fillRect(xPos + width / 2 - 50, drawY, tpd, 10); //draw full bar


        if (hp >= (int)(maxhp*1.5)) {
            window.drawString("MAX", xPos - (50) + hpd - 12 + (width / 2), drawY-5); //draw max
        } else {
            window.drawString(percent+"%", xPos+(width/2)-(50)+tpd-2, drawY - 5); //draw number
        }

        Color ofcolor;
        if ( hpchange < 0) {
            ofcolor = Color.RED;
        } else if ( hpchange > 0 ) {
            ofcolor = Color.CYAN;
        } else {
            ofcolor = Color.WHITE;
        }


        if (hp > maxhp) {
            window.setColor(Color.CYAN);
            window.fillRect(xPos + (width / 2)-50, drawY, percent%100, 10); //draw overflow bar
            window.setColor(ofcolor);
            window.fillRect(xPos+(width/2)-50+(percent%100), drawY-2, 4, 14); //draw bar thing
        } else {
            window.setColor(ofcolor);
            window.fillRect(xPos+(width/2)-(50)+tpd-2, drawY-2, 4, 14); //draw bar thing
        }
        if (hp >= (int)(maxhp*1.5)) {
            return;
        }

        //this is responsible for the warnings/indicators
        int statusX = xPos+width/2+50-12;
        int statusY = drawY-5;

        if (invincible) {
            window.setColor(Color.MAGENTA);
            window.drawString("INV", statusX, statusY);
            return;
        }
        else if (hp <= maxhp*0.25) {
            window.setColor(Color.RED);
            window.drawString("!!!", statusX, statusY);
        } else if (hp > maxhp) {
            window.setColor(Color.CYAN);
            window.drawString("+", xPos-(50), drawY+7);
        }
    }

    protected void update() {
    }

    protected void update(Graphics window, ArrayList<PongObject> objects) {
    }

    protected void updateHP() {

        if (invincible) {
            hpchange = 0;
            return;
        }
        if (hpchange > 0) {
            if (hpchange > 30) { //this is for high speed hp changes, to save time
                hp += 2;
                hpchange -=2;
            }
            hp += 1;
            hpchange -= 1;
        } else if (hpchange < 0) {
            if (hpchange < -30) { //this is for high speed hp changes
                hpchange += 2;
                hp -=2;
            }
            hp -= 1;
            hpchange += 1;
        }

        if (hp < 0) { //remove object when it dies
            hp = 0;
            visible = false;
            solid = false;
        }

        if (hp > (int)(maxhp*1.5)) {
            hp = (int)(maxhp*1.5);
        }
    }


    protected void revive() {
        if (hp == 0 && !solid && !visible) {
            hp = 1;
            hpchange = (int)(maxhp*0.1);
            solid = true;
            visible = true;
        }
    }

    protected void kill() {
        hpchange = -9999;
    }

    protected void fullHeal() {
        if (hp < maxhp) {
            hpchange = maxhp-hp;
        }
    }

    @Override
    public String toString() {
        return String.format("%s: X: %d Y: %d Xb: %d Yb: %d HP: %s HPC: %s aHP: %s", name, xPos, yPos, xPos+width, xPos+height, hp, hpchange, hp+hpchange);
    }

    protected boolean isSolid() {
        return solid;
    }

    protected double calculateBounceAngle(PongObject object) {
        int sizeY = object.getYPos()-object.getHeight();

        int centerP = object.getYPos() + object.getHeight()/2;
        //dist from top
        int dfc = centerP - object.getYPos();
        //percent from top
        double pft = (double)dfc/(sizeY/2);
        //get angle of bounce
        int dir = 1;
        if (Math.random() < 0.5) {
            dir = -1;
        }
        return pft*50*dir;
    }


    protected void drawHitbox(Graphics window) {
        if (solid && visible) {
            window.setColor(color);
            window.drawRect(xPos, yPos, width, height);
        }
    }
}

