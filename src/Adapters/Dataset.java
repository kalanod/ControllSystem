package Adapters;

import Database.Conn;
import Models.Gate;
import Models.Transaction;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;

import java.sql.SQLException;
import java.util.Arrays;

public class Dataset {
    public static final String[] SECTIONS = {"успешный",
            "неуспешный"};
    public static final double[] EXPENSES_05 = {7035.8, 26000};


    public static PieDataset createPieDataset(final double[] expenses) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        for (int i = 0; i < SECTIONS.length; i++)
            dataset.setValue(SECTIONS[i], new Double(expenses[i]));
        return dataset;
    }

    public static PieDataset createPieDataset(Gate gate) {
        DefaultPieDataset dataset = new DefaultPieDataset();
        try {
            Conn.connect();
            Conn.createDB();
            Transaction[] transactions = Conn.readTransactions(gate);
            dataset.setValue(SECTIONS[0], new Double(Arrays.stream(transactions).filter(t -> t.getResult() == 1).count()));
            dataset.setValue(SECTIONS[1], new Double(Arrays.stream(transactions).filter(t -> t.getResult() == 0).count()));
            Conn.closeDB();
            return dataset;
        } catch (ClassNotFoundException e) {

            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}