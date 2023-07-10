package Panels;

import Models.Gate;
import Models.Worker;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class GatesListPanel extends JPanel {
    JList<String> listGates;
    DefaultListModel<String> modelGates;

    public GatesListPanel(){
        //setSize(40, 40);
        //setBackground(Color.RED);
        init();
        listGates.setModel(modelGates);
        listGates.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(listGates));
    }
    public void init(){
        listGates = new JList<>();
        modelGates = new DefaultListModel<>();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //setVisible(true);

    }
    public void addListElement(Gate worker){
        modelGates.addElement(worker.getTitle());
    }

    public void addListSelectionListener(ListSelectionListener listener) {
        listGates.addListSelectionListener(listener);
    }

    public void clear() {
        modelGates.removeAllElements();
    }
}

