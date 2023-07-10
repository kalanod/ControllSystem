package Test;

import Database.Conn;

import java.sql.SQLException;

public class tester {
    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        Conn.connect();
        Conn.createDB();
        Conn.addNewTransaction(Conn.readGates()[0], Conn.readWorkers()[0] );
        Conn.closeDB();
    }
}
