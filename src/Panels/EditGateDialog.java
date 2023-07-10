package Panels;

import Adapters.DataHolder;
import Adapters.HintTextField;
import Adapters.IntVerifier;
import Adapters.TextVerifier;
import Database.Conn;
import Database.Db;
import Models.Gate;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class EditGateDialog extends JDialog {
    JLabel label = new JLabel("EDIT GATE");
    JLabel error = new JLabel("Не удалось сохранить данные");
    JTextField title = new JTextField("название");
    JTextField zoneId = new JTextField("ID зоны за кпп");
    JTextField access = new JTextField("уровень доступа");
    JButton cancelBtn = new JButton("отмена");
    JButton addBtn = new JButton("сохранить");


    public EditGateDialog(JFrame container, Gate gate, DataHolder dataHolder) {
        super(container, "изменить КПП", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(label)
                        .addComponent(title)
                        .addComponent(zoneId)
                        .addComponent(access)
                        .addComponent(error)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(cancelBtn)
                                .addComponent(addBtn))
                ));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(label)
                .addComponent(title)
                .addComponent(zoneId)
                .addComponent(access)
                .addComponent(error)
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(cancelBtn)
                        .addComponent(addBtn))
        );
        error.setBackground(Color.red);
        error.setVisible(false);
        title.setText(gate.getTitle());
        zoneId.setText(Integer.toString(gate.getZoneId()));
        access.setText(Integer.toString(gate.getAccessLevel()));
        cancelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (validateFields()) {
                    Gate newGate = new Gate(
                            gate.getId(),
                            title.getText(),
                            Integer.parseInt(access.getText()),
                            Integer.parseInt(zoneId.getText()),
                            gate.isActive());
                    dataHolder.editGate(newGate);
                    setVisible(false);
                } else {
                    error.setText("Поля зоплнены неверно");
                    error.setVisible(true);
                    pack();
                }

            }
        });
        title.setInputVerifier(new TextVerifier());
        access.setInputVerifier(new IntVerifier());
        zoneId.setInputVerifier(new IntVerifier());

        pack();
        centreWindow();
    }
    public void centreWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }
    private boolean validateFields() {
        return !title.getText().isBlank() && !access.getText().isBlank() && !zoneId.getText().isBlank();
    }
}

