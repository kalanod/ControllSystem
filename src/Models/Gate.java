package Models;

import java.util.Objects;
import java.util.Random;

public class Gate extends DBElem{
    private final int gateId;
    private String title;
    private int zoneId;
    private int accessLevel;
    private int isActive;

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAccessLevel() {
        return accessLevel;
    }

    public String getTitle() {
        return title;
    }

    public Gate(String title, int zoneId, int accessLevel) {
        isActive = 1;
        gateId = new Random().nextInt(0, 99999999);
        this.title = title;
        this.zoneId = zoneId;
        this.accessLevel = accessLevel;

    }
    public Gate(int gateId, String title, int zoneId, int accessLevel, int isActive) {
        this.gateId = gateId;
        this.title = title;
        this.zoneId = zoneId;
        this.accessLevel = accessLevel;
        this.isActive = isActive;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public int getId() {
        return gateId;
    }

    public int getZoneId() {
        return zoneId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Gate gate = (Gate) o;
        return gateId == gate.gateId && zoneId == gate.zoneId && accessLevel == gate.accessLevel && isActive == gate.isActive && Objects.equals(title, gate.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gateId, title, zoneId, accessLevel, isActive);
    }

    public int isActive() {
        return isActive;
    }
    public void switchGate() {
        isActive = (isActive + 1) % 2;
    }
}
