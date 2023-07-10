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

public class AddGateDialog extends JDialog {
    JLabel label = new JLabel("NEW GATE");
    JLabel error = new JLabel("Не удалось сохранить данные");
    JTextField title = new HintTextField("название");
    JTextField zoneId = new HintTextField("ID зоны за кпп");
    JTextField access = new HintTextField("уровень доступа");
    JButton cancelBtn = new JButton("отмена");
    JButton addBtn = new JButton("добавить");


    public AddGateDialog(JFrame container, DataHolder dataHolder) {
        super(container, "новый КПП", true);
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
                    Gate gate = new Gate(title.getText(),
                            Integer.parseInt(access.getText()),
                            Integer.parseInt(zoneId.getText()));
                    try {
                        dataHolder.addGate(gate);
                        setVisible(false);
                    } catch (ClassNotFoundException ex) {
                        error.setText("Не удалось сохранить данные");
                        error.setVisible(true);
                        pack();
                    } catch (SQLException ex) {
                        error.setText("Не удалось сохранить данные");
                        error.setVisible(true);
                        ex.printStackTrace();
                        pack();
                    }
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

