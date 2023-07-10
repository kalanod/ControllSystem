package Adapters;

import Database.Conn;
import Models.Gate;
import Models.Worker;

import java.sql.SQLException;
import java.util.Arrays;

public class DataHolder {
    Worker[] workers;
    Gate[] gates;
    String[] dataList = new String[]{"Работники", "КПП", "О программе"};



    public DataHolder() {
        updateData();
    }

    public void updateData() {
        try {
            Conn.connect();
            Conn.createDB();
            workers = Conn.readWorkers();
            System.out.println(Arrays.toString(workers));
            gates = Conn.readGates();
            Conn.closeDB();

        } catch (Exception e) {
            System.out.println("ошибка, невозможно загрузить данные ");
            System.out.println(e.getMessage());
        }

    }

    public Worker[] getWorkers() {
        return workers;
    }

    public Gate[] getGates() {
        return gates;
    }

    public String[] getMenu() {
        return dataList;
    }

    public void switchGate(int currentElem) {
        try {
            Conn.connect();
            Conn.createDB();
            gates[currentElem].switchGate();
            Conn.updateGate(gates[currentElem]);
            Conn.closeDB();
            updateData();
        } catch (Exception e) {
            System.out.println("ошибка, невозможно загрузить данные ");
            System.out.println(e.getMessage());
        }
    }

    public Gate getGate(int currentElem) {
        return gates[currentElem];
    }

    public Worker getWorker(int currentElem) {
        return workers[currentElem];
    }

    public void dellGate(int currentElem) {
        try {
            Conn.connect();
            Conn.createDB();
            Conn.delGate(gates[currentElem]);
            Conn.closeDB();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        updateData();
    }
    public void dellWorker(int currentElem) {
        try {
            Conn.connect();
            Conn.createDB();
            Conn.delWorker(workers[currentElem]);
            Conn.closeDB();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        updateData();
    }

    public String[] search(String res) {
        String[] data = res.split(" ");
        if (res.isBlank()) {
            updateData();
        }
        if (data.length == 2 && (data[0].equals("name") || data[0].equals("lastName") ||
                data[0].equals("position") || data[0].equals("phoneNumber"))) {
            try {
                Conn.connect();
                Conn.createDB();
                workers = Conn.readWorkers(data[0] + " like '" + data[1].replace('*', '%') + "'");
                System.out.println("найдено" + workers.length);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        if (data.length >= 2 && (data[0].equals("age") || data[0].equals("accessLevel"))) {
            try {
                Conn.connect();
                Conn.createDB();
                workers = Conn.readWorkers(res);
                System.out.println("найдено" + workers.length);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return Arrays.stream(workers).map(Worker::getName).toList().toArray(new String[0]);
    }

    public void editGate(Gate newGate) {
        try {
            Conn.connect();
            Conn.createDB();
            Conn.updateGate(newGate);
            Conn.closeDB();
            updateData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void editWorker(Worker worker) {
        try {
            Conn.connect();
            Conn.createDB();
            Conn.updateWorker(worker);
            Conn.closeDB();
            updateData();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void addGate(Gate gate) throws SQLException, ClassNotFoundException {
        Conn.connect();
        Conn.createDB();
        Conn.addNewGate(gate);
        Conn.closeDB();
        updateData();
    }

    public void addWorker(Worker worker) throws SQLException, ClassNotFoundException {
        Conn.connect();
        Conn.createDB();
        Conn.addNewWorker(worker);
        Conn.closeDB();
        updateData();
    }
}
