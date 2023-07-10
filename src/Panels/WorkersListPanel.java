package Panels;

import Models.Worker;

import javax.swing.*;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

public class WorkersListPanel extends JPanel {
    JList<String> listWorkers;
    DefaultListModel<String> modelWorkers;

    public WorkersListPanel(){
        init();
        listWorkers.setModel(modelWorkers);
        listWorkers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        add(new JScrollPane(listWorkers));
    }
    public void init(){
        listWorkers = new JList<>();
        modelWorkers = new DefaultListModel<>();
    }
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        //setVisible(true);

    }
    public void addListElement(Worker worker){
        modelWorkers.addElement(worker.getName());
    }

    public void addListSelectionListener(ListSelectionListener listener) {
        listWorkers.addListSelectionListener(listener);
    }

    public void clear() {
        modelWorkers.removeAllElements();
    }
}
