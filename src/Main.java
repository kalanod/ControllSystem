import Adapters.DataHolder;
import Adapters.Dataset;
import Adapters.HintTextField;
import Models.Gate;
import Models.Worker;
import Panels.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.PieDataset;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import static javax.swing.GroupLayout.Alignment.BASELINE;
import static javax.swing.GroupLayout.Alignment.LEADING;

public class Main extends JFrame {
    int WORKERS_WINDOW = 0;
    int GATES_WINDOW = 1;
    int ABOUT_WINDOW = 2;
    int currentWindow = WORKERS_WINDOW;
    String IMG_AVATAR_PATH = "assets/avatar.png";
    String IMG_GATE_PATH = "assets/gate.png";

    private JList<String> listMenu;
    private JList<String> listWorkers;
    private DefaultListModel<String> modelMenu;
    private DefaultListModel<String> modelWorkers;
    private JScrollPane listMenuCard;
    private JScrollPane listWorkersCard;
    private JButton addBtn;
    private JButton editBtn;
    private JButton dellBtn;
    private JButton disableBtn;
    private PieDataset dataset;
    private JFreeChart chart;
    private ChartPanel chartPanel;
    private WorkerPanel workerPanel;
    private GroupLayout layout;

    private JLabel nameField;
    private JLabel lastNameField;
    private JLabel ageField;
    private JLabel accessLevelField;
    private JLabel positionField;
    private JLabel phoneNumberField;

//    Worker[] workers;
//    Gate[] gates;
//    String[] dataList;
    BufferedImage img;
    ImageIcon icon;
    JLabel imageView;
    JTextField search = new HintTextField("поиск");
    DataHolder dataHolder = new DataHolder();
    int currentElem;

    public Main(String title) {
        super(title);
        setMinimumSize(new Dimension(600, 450));

    }


    public void updateLists() {
        modelWorkers.removeAllElements();
        if (currentWindow == WORKERS_WINDOW)
            for (Worker s : dataHolder.getWorkers()) {
                modelWorkers.addElement(s.getLastName() + " " + s.getName());
            }
        else {
            for (Gate s : dataHolder.getGates()) {
                modelWorkers.addElement(s.getTitle());
            }
        }

    }

    public void updateWindow() {
        getContentPane().setLayout(layout);
        layout.setAutoCreateGaps(true);
        layout.setAutoCreateContainerGaps(true);
        //workersListPanel.setMaximumSize(new Dimension(listMenu.getMinimumSize().width, Integer.MAX_VALUE));
        layout.linkSize(SwingConstants.HORIZONTAL, listWorkersCard, addBtn, listMenuCard, search);
        layout.linkSize(SwingConstants.VERTICAL, addBtn, search);

        //layout.linkSize(SwingConstants.HORIZONTAL, listMenu, workersListPanel);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addComponent(listMenuCard)
                .addGroup(layout.createParallelGroup(LEADING)
                        .addComponent(addBtn)
                        .addComponent(search)
                        .addComponent(listWorkersCard))
                .addGroup(layout.createParallelGroup(LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(editBtn)
                                .addComponent(dellBtn)
                                .addComponent(disableBtn)
                        )
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(imageView)
                                .addGroup(layout.createParallelGroup(LEADING)
                                        .addComponent(nameField)
                                        .addComponent(lastNameField)
                                        .addComponent(ageField)
                                        .addComponent(accessLevelField)
                                        .addComponent(positionField)
                                        .addComponent(phoneNumberField)))
                        .addComponent(chartPanel)
                ));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(BASELINE)
                        .addComponent(listMenuCard)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(addBtn)
                                .addComponent(search)
                                .addComponent(listWorkersCard))
                        .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(BASELINE)
                                        .addComponent(editBtn)
                                        .addComponent(dellBtn)
                                        .addComponent(disableBtn))
                                .addGroup(layout.createParallelGroup(BASELINE)
                                        .addComponent(imageView)
                                        .addGroup(layout.createSequentialGroup()
                                                .addComponent(nameField)
                                                .addComponent(lastNameField)
                                                .addComponent(ageField)
                                                .addComponent(accessLevelField)
                                                .addComponent(positionField)
                                                .addComponent(phoneNumberField)))
                                .addComponent(chartPanel)
                        )));

        //listMenu.setSize(1000, 100);
        updateLists();
    }

    public void updateComponents() {
        //Container c = getContentPane();
        search.setVisible(true);
        listWorkers.setModel(modelWorkers);
        listWorkers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
///--------------------------------
        listMenu.setModel(modelMenu);
        listMenu.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        listMenu.setSelectedIndex(0);
        listMenu.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int id = ((JList<?>) e.getSource()).getSelectedIndex();
                if (id == WORKERS_WINDOW) {
                    currentWindow = WORKERS_WINDOW;
                    listWorkersCard.setVisible(true);
                    addBtn.setVisible(true);
                    hideViewCard();
                    search.setVisible(true);
                } else if (id == GATES_WINDOW) {
                    currentWindow = GATES_WINDOW;
                    listWorkersCard.setVisible(true);
                    addBtn.setVisible(true);
                    hideViewCard();
                    search.setVisible(false);
                } else if (id == ABOUT_WINDOW) {
                    currentWindow = ABOUT_WINDOW;
                    hideViewCard();
                    addBtn.setVisible(false);
                    listWorkersCard.setVisible(false);
                    showViewCard();
                    search.setVisible(false);
                }
                updateLists();
                repaint();
            }
        });
        for (String s : dataHolder.getMenu()) {
            modelMenu.addElement(s);
        }
        //add();
        disableBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataHolder.switchGate(currentElem);
                if (dataHolder.getGate(currentElem).isActive() == 1) {
                    accessLevelField.setText("Работает");
                    disableBtn.setText("отключить");
                } else {
                    accessLevelField.setText("Не работает");
                    disableBtn.setText("включить");
                }
                updateLists();
                repaint();
            }
        });
        editBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog;
                if (currentWindow == GATES_WINDOW) {
                    dialog = new EditGateDialog(Main.this, dataHolder.getGate(currentElem), dataHolder);
                    dialog.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowDeactivated(WindowEvent e) {
                            super.windowDeactivated(e);
                            repaint();
                            showViewCard(dataHolder.getGate(currentElem));
                            updateLists();
                        }
                    });
                } else {
                    dialog = new EditWorkerDialog(Main.this, dataHolder.getWorker(currentElem),dataHolder);
                    dialog.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowDeactivated(WindowEvent e) {
                            super.windowDeactivated(e);
                            repaint();
                            showViewCard(dataHolder.getWorker(currentElem));
                            updateLists();
                        }
                    });
                }
                dialog.setVisible(true);


            }

        });
        dellBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (currentWindow == GATES_WINDOW) {
                    dataHolder.dellGate(currentElem);
                }
                else {
                    dataHolder.dellWorker(currentElem);
                }
                repaint();
                hideViewCard();
                updateLists();
            }
        });
        listWorkers.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int id = ((JList<?>) e.getSource()).getSelectedIndex();
                try {
                    if (currentWindow == WORKERS_WINDOW) {
                        Worker worker = dataHolder.getWorker(id);
                        currentElem = id;
                        showViewCard(worker);
                    } else {
                        Gate gate = dataHolder.getGate(id);
                        currentElem = id;
                        showViewCard(gate);
                    }
                } catch (Exception ignored) {
                }

            }
        });
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog;
                if (currentWindow == GATES_WINDOW) {
                    dialog = new AddGateDialog(Main.this, dataHolder);
                } else {
                    dialog = new AddWorkerDialog(Main.this, dataHolder);
                }
                dialog.setVisible(true);
                dialog.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowDeactivated(WindowEvent e) {
                        super.windowDeactivated(e);

                        repaint();
                    }
                });
            }
        });

        chartPanel = new ChartPanel(chart);
        chartPanel.setVisible(false);
        chartPanel.setPreferredSize(new java.awt.Dimension(150, 100));
        search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dataHolder.search(search.getText());
                updateLists();
            }
        });
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setVisible(true);
    }

    private JFreeChart createChart(final PieDataset dataset) {
        chart = ChartFactory.createPieChart("Диаграмма использования",
                dataset, false, false, false);
        PiePlot plot = (PiePlot) chart.getPlot();
        plot.setExplodePercent(Dataset.SECTIONS[1], 0.20);
        return chart;
    }

    public void hideViewCard() {
        imageView.setVisible(false);
        nameField.setVisible(false);
        lastNameField.setVisible(false);
        ageField.setVisible(false);
        accessLevelField.setVisible(false);
        positionField.setVisible(false);
        phoneNumberField.setVisible(false);
        editBtn.setVisible(false);
        dellBtn.setVisible(false);
        disableBtn.setVisible(false);
        chartPanel.setVisible(false);
        updateWindow();
        //search.setVisible(false);
    }

    public void showViewCard(Worker worker) {
        nameField.setVisible(true);
        lastNameField.setVisible(true);
        ageField.setVisible(true);
        accessLevelField.setVisible(true);
        positionField.setVisible(true);
        phoneNumberField.setVisible(true);

        nameField.setText("имя:                         " + worker.getName());
        lastNameField.setText("фамилия:               " + worker.getLastName());
        ageField.setText("возраст:                   " + Integer.toString(worker.getAge()));
        accessLevelField.setText("уровень доступа:   " + Integer.toString(worker.getAccessLevel()));
        positionField.setText("должность:              " + worker.getPosition());
        phoneNumberField.setText("телефон:                 " + worker.getPhoneNumber());

        updateImg(IMG_AVATAR_PATH);
    }

    public void showViewCard(Gate gate) {
        nameField.setVisible(true);
        lastNameField.setVisible(true);
        ageField.setVisible(true);
        accessLevelField.setVisible(true);

        nameField.setText("название:              " + gate.getTitle());
        lastNameField.setText("ID зоны:                 " + Integer.toString(gate.getZoneId()));
        ageField.setText("уровень доступа:   " + Integer.toString(gate.getAccessLevel()));
        if (gate.isActive() == 1) {
            accessLevelField.setText("Работает");
            disableBtn.setText("отключить");
        } else {
            accessLevelField.setText("Не работает");
            disableBtn.setText("включить");
        }
        disableBtn.setVisible(true);
        updateImg(IMG_GATE_PATH);
        dataset = Dataset.createPieDataset(dataHolder.getGate(currentElem));
        chart = createChart(dataset);
        chartPanel.setChart(chart);
        chartPanel.setVisible(true);
    }

    private void showViewCard() {
        nameField.setText("Программа эмулятор терминала системы Контроля доступа CALANCO");
        lastNameField.setText("Автор Трофимов Андрей 2154");
        ageField.setText("В рамках производственной практики ГУАП 2023");
        nameField.setVisible(true);
        lastNameField.setVisible(true);
        ageField.setVisible(true);
    }

    private void updateImg(String img_gate_path) {

        editBtn.setVisible(true);
        dellBtn.setVisible(true);
        try {
            img = ImageIO.read(new File(getClass().getResource(img_gate_path).getPath()));
            icon = new ImageIcon(img);
            imageView.setIcon(icon);
        } catch (Exception e) {
            img = null;
            icon = null;
            imageView.setText("не удалось получить изображение");
            System.out.println("_--------------------------------------------------------------");
            e.printStackTrace();
        }
        imageView.setVisible(true);
        //updateWindow();
        //imageView.setPreferredSize(new Dimension(10, 10));

        repaint();
    }

    public void centreWindow() {
        Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (int) ((dimension.getWidth() - getWidth()) / 2);
        int y = (int) ((dimension.getHeight() - getHeight()) / 2);
        setLocation(x, y);
    }

    public void init() {

        //workersListPanel = new JScrollPane(listWorkers);
        addBtn = new JButton("Добавить");
        editBtn = new JButton("Изменить");
        dellBtn = new JButton("Удалить");

        workerPanel = new WorkerPanel(getContentPane());
        layout = new GroupLayout(getContentPane());

        nameField = new JLabel("s");
        lastNameField = new JLabel("s");
        ageField = new JLabel("s");
        accessLevelField = new JLabel("s");
        positionField = new JLabel("s");
        phoneNumberField = new JLabel("s");

        listMenu = new JList<>(dataHolder.getMenu());
        modelMenu = new DefaultListModel<>();
        listMenuCard = new JScrollPane(listMenu);

        listWorkers = new JList<>();
        modelWorkers = new DefaultListModel<>();
        listWorkersCard = new JScrollPane(listWorkers);
        icon = new ImageIcon();
        imageView = new JLabel();
        disableBtn = new JButton();

    }

    public static void main(String[] args) {
        System.out.println("a");
        Main mw = new Main("kalann");
        mw.init();
        mw.updateComponents();
        mw.updateWindow();
        mw.hideViewCard();
        mw.centreWindow();
        mw.pack();
        //mw.layout.linkSize(SwingConstants.VERTICAL, mw.listMenu, mw.cards1);

    }
}
