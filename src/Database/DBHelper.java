package Database;

import Models.Gate;
import Models.Worker;

import java.sql.SQLException;

public class DBHelper {
    public static Worker[] getWorkers() throws SQLException, ClassNotFoundException {
        return new Worker[]{
                new Worker("вася", "васин", 15, 2, "раб", "898"),
                new Worker("петя", "петин", 12, 22, "раб", "88")};
    }

    public static Gate[] getGates() {
        return new Gate[]{
                new Gate("вход", 1, 2),
                new Gate( "оруужейная", 1, 2),
                new Gate("склад", 1, 2)};
    }
}
