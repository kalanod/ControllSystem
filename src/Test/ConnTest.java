package Test;

import Database.Conn;
import Models.Gate;
import Models.Transaction;
import Models.Worker;
import org.junit.Assert;
import org.junit.Test;

import java.sql.SQLException;
import java.util.Random;

public class ConnTest {

    @Test
    public void addNewWorker() throws SQLException, ClassNotFoundException {
        Conn.connect();
        Conn.createDB();
        Worker worker = new Worker("а","с",3,4,"3",Integer.toString(new Random().nextInt(999)));
        Conn.addNewWorker(worker);
        Worker[] workers = Conn.readWorkers();
        Assert.assertEquals(worker, workers[workers.length -1]);
        Conn.closeDB();
    }

    @Test
    public void addNewGate() throws SQLException, ClassNotFoundException {
        Conn.connect();
        Conn.createDB();
        Gate gate = new Gate("а",3, 3);
        Conn.addNewGate(gate);
        Gate[] gates = Conn.readGates();
        Assert.assertEquals(gate, gates[gates.length - 1]);
        Conn.closeDB();
    }

    @Test
    public void addNewTransaction() throws SQLException, ClassNotFoundException {
        Conn.connect();
        Conn.createDB();
        Worker worker = new Worker("а","с",3,4,"3",Integer.toString(new Random().nextInt(999)));
        Gate gate = new Gate("а",3, 3);
        Transaction expected = new Transaction(gate.getId(), worker.getUID(), 1);
        Assert.assertEquals(expected.getResult() == 1, Conn.addNewTransaction(gate, worker));
        Conn.closeDB();
    }


    @Test
    public void updateWorker() throws SQLException, ClassNotFoundException {
        Conn.connect();
        Conn.createDB();
        Worker[] workers = Conn.readWorkers();
        Worker worker = workers[workers.length-1];
        worker.setAge(worker.getAge()+1);
        Conn.updateWorker(worker);
        workers = Conn.readWorkers();
        Assert.assertEquals(worker, workers[workers.length-1]);
        Conn.closeDB();
    }
    @Test
    public void updateBadWorker() throws SQLException, ClassNotFoundException {
        Conn.connect();
        Conn.createDB();
        Worker[] workers = Conn.readWorkers();
        Worker worker = new Worker("а","с",3,4,"3",Integer.toString(new Random().nextInt(999)));
        worker.setAge(worker.getAge()+1);
        Conn.updateWorker(worker);
        Assert.assertEquals(workers.length, Conn.readWorkers().length);
        Conn.closeDB();
    }

    @Test
    public void updateBedGate() throws SQLException, ClassNotFoundException {
        Conn.connect();
        Conn.createDB();
        Gate[] gates = Conn.readGates();
        Gate gate = new Gate(new Random().nextInt(999), "а",3, 3, 1);
        gate.setZoneId(gate.getZoneId()+1);
        Conn.updateGate(gate);
        Assert.assertEquals(gates.length, Conn.readGates().length);
        Conn.closeDB();
    }
    @Test
    public void updateGate() throws SQLException, ClassNotFoundException {
        Conn.connect();
        Conn.createDB();
        Gate[] gates = Conn.readGates();
        Gate gate = gates[gates.length-1];
        gate.setZoneId(gate.getZoneId()+1);
        Conn.updateGate(gate);
        gates = Conn.readGates();
        Assert.assertEquals(gates[gates.length-1], gate);
        Conn.closeDB();
    }


    @Test
    public void delGate() throws SQLException, ClassNotFoundException {
        Conn.connect();
        Conn.createDB();
        Gate[] gates = Conn.readGates();
        Conn.delGate(gates[gates.length-1]);
        Assert.assertEquals(gates.length - 1, Conn.readGates().length);
        Conn.closeDB();
    }

    @Test
    public void delWorker() throws SQLException, ClassNotFoundException {
        Conn.connect();
        Conn.createDB();
        Worker[] workers = Conn.readWorkers();
        Conn.delWorker(workers[workers.length-1]);
        Assert.assertEquals(workers.length-1,Conn.readWorkers().length);
        Conn.closeDB();
    }
}