package Database;

import Models.Worker;

import java.sql.SQLException;

public class Db {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Conn.connect();
        Worker user;
        user = new Worker("вася", "васин", 15, 2, "раб", "898");
        Conn.createDB();

        for (Worker i: Conn.readWorkers()){
            i.setName("каллии");
            Conn.updateWorker(i);
            System.out.println(i.getName());
        }
        for (Worker i: Conn.readWorkers()){
            System.out.println(i.getName());
        }
        Conn.closeDB();
    }
}
