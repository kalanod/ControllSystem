package Models;

public class Transaction {
    int gateId;
    int UID;
    int result;

    public Transaction(int gateId, int UID, int result) {
        this.gateId = gateId;
        this.UID = UID;
        this.result = result;
    }

    public int getGateId() {
        return gateId;
    }

    public int getUID() {
        return UID;
    }

    public int getResult() {
        return result;
    }
}
