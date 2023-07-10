package Panels;

import Adapters.DataHolder;
import Adapters.HintTextField;
import Adapters.IntVerifier;
import Adapters.TextVerifier;
import Database.Conn;
import Models.Gate;
import Models.Worker;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class AddWorkerDialog extends JDialog {
    JLabel label = new JLabel("NEW WORKER");
    HintTextField name = new HintTextField("имя");
    HintTextField lastName = new HintTextField("фамилия");
    HintTextField age = new HintTextField("возраст");
    HintTextField accessLevel = new HintTextField("уровень доступа");
    HintTextField position = new HintTextField("должность");
    HintTextField phoneNumber = new HintTextField("телефон");

    JLabel error = new JLabel("Не удалось сохранить данные");

    JButton cancelBtn = new JButton("отмена");
    JButton addBtn = new JButton("добавить");


    public AddWorkerDialog(JFrame container, DataHolder dataHolder) {
        super(container, "новый сотрудник", true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(label)
                        .addComponent(name)
                        .addComponent(lastName)
                        .addComponent(age)
                        .addComponent(accessLevel)
                        .addComponent(position)
                        .addComponent(phoneNumber)
                        .addComponent(error)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(cancelBtn)
                                .addComponent(addBtn))));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addComponent(label)
                .addComponent(name)
                .addComponent(lastName)
                .addComponent(age)
                .addComponent(accessLevel)
                .addComponent(position)
                .addComponent(phoneNumber)

                .addComponent(error)
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(cancelBtn)
                        .addComponent(addBtn)));
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
                    Worker worker = new Worker(name.getText(),
                            lastName.getText(),
                            Integer.parseInt(age.getText()),
                            Integer.parseInt(accessLevel.getText()),
                            position.getText(),
                            phoneNumber.getText());
                    try {
                        dataHolder.addWorker(worker);
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
        name.setInputVerifier(new TextVerifier());
        lastName.setInputVerifier(new TextVerifier());
        age.setInputVerifier(new IntVerifier());
        accessLevel.setInputVerifier(new IntVerifier());
        position.setInputVerifier(new TextVerifier());
        phoneNumber.setInputVerifier(new TextVerifier());

        error.setBackground(Color.red);
        error.setVisible(false);
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
        return !name.getText().isBlank() &&
                !lastName.getText().isBlank() &&
                !age.getText().isBlank() &&
                !accessLevel.getText().isBlank() &&
                !position.getText().isBlank() &&
                !phoneNumber.getText().isBlank();
    }
}
