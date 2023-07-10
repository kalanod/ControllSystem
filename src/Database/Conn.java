package Database;

import Models.Gate;
import Models.Transaction;
import Models.Worker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


public class Conn {
    public static Connection conn;
    public static Statement statmt;
    public static ResultSet resSet;
    public String title;

    public Conn(String title) {

    }

    public static void connect() throws ClassNotFoundException, SQLException {
        Class.forName("org.sqlite.JDBC");
        conn = DriverManager.getConnection("jdbc:sqlite:src/Database/TEST1.db");

        //System.out.println("База Подключена!");
    }

    // --------Создание таблицы--------
    public static void createDB() throws ClassNotFoundException, SQLException {
        statmt = conn.createStatement();
        statmt.execute("CREATE TABLE if not exists 'workers' ('id' INTEGER PRIMARY KEY AUTOINCREMENT," +
                " 'UID' INT, 'name' text, 'lastName' text, 'age' INT, 'accessLevel' text, 'position' text, 'phoneNumber' text ," +
                "UNIQUE(UID), UNIQUE(phoneNumber));");
        statmt.execute("CREATE TABLE if not exists 'gates' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "'gateId' INT, 'title' text, 'zoneId' INT, 'accessLevel' INT, 'isActive' INT, UNIQUE(gateId));");
        statmt.execute("CREATE TABLE if not exists 'transactions' ('id' INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "'gateId' INT, 'UID' INT, 'result' INT);");

        //System.out.println("Таблица создана или уже существует.");
    }

    // --------Заполнение таблицы--------
    public static void addNewWorker(Worker worker) throws SQLException {
        statmt.execute(String.format("INSERT INTO 'workers' ('UID', 'name', 'lastName', 'age', 'accessLevel', 'position', 'phoneNumber')" +
                        " VALUES (%d, '%s', '%s', %d, %d, '%s', '%s'); ",
                worker.getUID(), worker.getName(), worker.getLastName(), worker.getAge(),
                worker.getAccessLevel(), worker.getPosition(), worker.getPhoneNumber()));

        //System.out.println("пользователь " + worker.getName() + " добавлен успешно");

    }

    public static void addNewGate(Gate gate) throws SQLException {
        statmt.execute(String.format("INSERT INTO 'gates' ('gateId', 'title', 'zoneId', 'accessLevel', 'isActive')" +
                        " VALUES (%d, '%s', %d, %d, %d); ",
                gate.getId(), gate.getTitle(), gate.getZoneId(), gate.getAccessLevel(), gate.isActive()));

        //System.out.println("проходная " + gate.getTitle() + " добавлена успешно");
    }

    public static boolean addNewTransaction(Gate gate, Worker worker) throws SQLException {
        statmt.execute(String.format("INSERT INTO 'transactions' ('gateId', 'UID', 'result')" +
                        " VALUES (%d, '%d', %d); ", gate.getId(), worker.getUID(),
                gate.getAccessLevel() <= worker.getAccessLevel() && gate.isActive() == 1 ? 1 : 0));
        //System.out.println("prohibited? sorry..(");
        return gate.getAccessLevel() <= worker.getAccessLevel() && gate.isActive() == 1;
    }

    public static Transaction[] readTransactions() throws SQLException {
        resSet = statmt.executeQuery("SELECT * FROM transactions");
        ArrayList<Transaction> transactions = new ArrayList<>();
        while (resSet.next()) {
            transactions.add(new Transaction(resSet.getInt("gateId"),
                    resSet.getInt("UID"),
                    resSet.getInt("result")
            ));
        }
        //System.out.println("Таблица получена " + transactions.size() + " строк");
        return transactions.toArray(new Transaction[0]);
    }

    public static Transaction[] readTransactions(Gate gate) throws SQLException {
        resSet = statmt.executeQuery(String.format("SELECT * FROM transactions WHERE gateId = %d", gate.getId()));
        ArrayList<Transaction> transactions = new ArrayList<>();
        while (resSet.next()) {
            transactions.add(new Transaction(resSet.getInt("gateId"),
                    resSet.getInt("UID"),
                    resSet.getInt("result")
            ));
        }
        //System.out.println("Таблица получена " + transactions.size() + " строк");
        return transactions.toArray(new Transaction[0]);
    }
    public static Worker[] readWorkers() throws SQLException {
        resSet = statmt.executeQuery("SELECT * FROM workers");
        ArrayList<Worker> workers = new ArrayList<>();
        while (resSet.next()) {
            workers.add(new Worker(resSet.getInt("UID"),
                    resSet.getString("name"),
                    resSet.getString("lastName"),
                    resSet.getInt("age"),
                    resSet.getInt("accessLevel"),
                    resSet.getString("position"),
                    resSet.getString("phoneNumber")
            ));
        }

        //System.out.println("Таблица получена " + workers.size() + " строк");
        return workers.toArray(new Worker[0]);
    }

    public static void updateWorker(Worker worker) throws SQLException {
        statmt.execute(String.format("UPDATE workers SET " +
                        " name = '%s', lastName = '%s', age = %d," +
                        " accessLevel = %d, position = '%s', phoneNumber = '%s'" +
                        " WHERE UID = %d;",
                worker.getName(), worker.getLastName(), worker.getAge(),
                worker.getAccessLevel(), worker.getPosition(), worker.getPhoneNumber(), worker.getUID()));

        //System.out.println("пользователь " + worker.getName() + " обновлён успешно");
    }

    public static void updateGate(Gate gate) throws SQLException {
        statmt.execute(String.format("UPDATE gates SET " +
                        "title = '%s', zoneId = %d," +
                        " accessLevel = %d, isActive = %d" +
                        " WHERE gateId = %d;",
                gate.getTitle(), gate.getZoneId(), gate.getAccessLevel(), gate.isActive(), gate.getId()));

        //System.out.println("проходная " + gate.getTitle() + " обновлёна успешно");
    }

    public static Gate[] readGates() throws ClassNotFoundException, SQLException {
        resSet = statmt.executeQuery("SELECT * FROM gates");
        ArrayList<Gate> gates = new ArrayList<>();
        while (resSet.next()) {
            gates.add(new Gate(resSet.getInt("gateId"),
                    resSet.getString("title"),
                    resSet.getInt("zoneId"),
                    resSet.getInt("accessLevel"),
                    resSet.getInt("isActive")
            ));
        }

        //System.out.println("Таблица получена " + gates.size() + " строк");
        return gates.toArray(new Gate[0]);
    }

    public static void delGate(Gate gate) throws SQLException {
        statmt.execute(String.format("DELETE FROM gates WHERE gateId = %d;", gate.getId()));

        //System.out.println("проходная " + gate.getTitle() + " удалена успешно");
    }

    public static void delWorker(Worker worker) throws SQLException {
        statmt.execute(String.format("DELETE FROM workers WHERE UID = %d;", worker.getUID()));

        //System.out.println("работник " + worker.getName() + " удалён успешно");
    }

    // --------Закрытие--------
    public static void closeDB() throws ClassNotFoundException, SQLException {
        conn.close();
        statmt.close();
        //resSet.close();

        //System.out.println("Соединения закрыты");
    }

    public static Worker[] readWorkers(String s) throws SQLException {
        resSet = statmt.executeQuery(String.format("SELECT * FROM workers WHERE %s", s));
        ArrayList<Worker> workers = new ArrayList<>();
        while (resSet.next()) {
            workers.add(new Worker(resSet.getInt("UID"),
                    resSet.getString("name"),
                    resSet.getString("lastName"),
                    resSet.getInt("age"),
                    resSet.getInt("accessLevel"),
                    resSet.getString("position"),
                    resSet.getString("phoneNumber")
            ));
        }
        //System.out.println("Таблица получена " + workers.size() + " строк");
        return workers.toArray(new Worker[0]);
    }
}