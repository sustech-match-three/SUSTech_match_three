package view;

import model.ChessboardPoint;

import javax.swing.*;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.border.Border;


/**
 * This is the equivalent of the Cell class,
 * but this class only cares how to draw Cells on ChessboardComponent
 */

public class CellComponent extends JPanel {
    private Color background;
    private Color blinkBackgroundColor;



    public CellComponent(Color background, Point location, int size) {
        setLayout(null);
        setLocation(location);
        setSize(size, size);
        this.background = background;
        blinkBackgroundColor = Color.YELLOW;
    }


    public void toggleBlinkAppearance() {
        // 切换到闪烁外观
        if (getBackground().equals(background)) {
            setBackground(blinkBackgroundColor);
        } else {
            // 恢复正常外观
            setBackground(background);
        }
    }

    public void restoreNormalAppearance() {
        // 恢复到正常外观
        setBackground(background);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponents(g);
        g.setColor(background);
        g.fillRect(1, 1, this.getWidth()-2, this.getHeight()-2);
    }
}
