package Panels;

import javax.swing.*;
import java.awt.*;

public class WorkerPanel extends JPanel {
    GridBagConstraints constraints;
    JButton button;
    public WorkerPanel(Container container) {
        setSize(409, 400);
        setBackground(Color.RED);
        init();

    }

    private void init() {
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //setVisible(true);

    }
}
