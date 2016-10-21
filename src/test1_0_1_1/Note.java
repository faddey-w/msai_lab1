/*
 * Decompiled with CFR 0_115.
 */
package test1_0_1_1;

import java.awt.FontMetrics;
import java.awt.Graphics2D;
import test1_0_1_1.Element;

public class Note
extends Element {
    private String s;
    private FontMetrics fm;

    public Note(int x, int y, int width, int height, String s) {
        super(x, y, width, height);
        this.s = s;
    }

    public void draw(Graphics2D g, int width, int height) {
        int x = this.getX(width);
        int y = this.getY(height);
        this.fm = g.getFontMetrics();
        g.drawString(this.s, x -= g.getFontMetrics().getWidths()[48] / 2, y += g.getFontMetrics().getAscent() / 2);
    }

    public boolean isSelects(int x, int y, int width, int height) {
        if (this.fm == null) {
            return false;
        }
        int x1 = this.getX(width);
        int y2 = this.getY(height);
        int y1 = (y2 += this.fm.getAscent() / 2) - this.fm.getAscent();
        int x2 = (x1 -= this.fm.getWidths()[48] / 2) + this.fm.stringWidth(this.s);
        return x1 <= x && x <= x2 && y1 <= y && y <= (y2 += this.fm.getDescent());
    }

    public String getText() {
        return this.s;
    }
}

