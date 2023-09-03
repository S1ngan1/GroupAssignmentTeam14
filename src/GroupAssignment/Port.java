package GroupAssignment;

import java.util.ArrayList;

public class Port {
    private int p_ID;
    private String name;
    private float X;
    private float Y;
    private float storingCapacity;
    private boolean landingAbility;

    private ArrayList<Vehicle> vehicleHangar;

    private ArrayList<Container> containerHangar;

    private ArrayList<Trip> tripRecord;

    public Port(int p_ID, String name, float X, float Y, float storingCapacity, boolean landingAbility){
        this.p_ID = p_ID;
        this.name = name;
        this.X = X;
        this.Y = Y;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
    }

    public int getP_ID() {
        return p_ID;
    }

    public void setP_ID(int p_ID) {
        this.p_ID = p_ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getX() {
        return X;
    }

    public void setX(float x) {
        X = x;
    }

    public float getY() {
        return Y;
    }

    public void setY(float y) {
        Y = y;
    }

    public float getStoringCapacity() {
        return storingCapacity;
    }

    public void setStoringCapacity(float storingCapacity) {
        if (storingCapacity > 0){
            this.storingCapacity = storingCapacity;
        }
    }

    public ArrayList<Vehicle> getVehicleHangar() {
        return vehicleHangar;
    }

    public void setVehicleHangar(ArrayList<Vehicle> vehicleHangar) {
        this.vehicleHangar = vehicleHangar;
    }

    public ArrayList<Container> getContainerHangar() {
        return containerHangar;
    }

    public void setContainerHangar(ArrayList<Container> containerHangar) {
        this.containerHangar = containerHangar;
    }

    public ArrayList<Trip> getTripRecord() {
        return tripRecord;
    }

    public void setTripRecord(ArrayList<Trip> tripRecord) {
        this.tripRecord = tripRecord;
    }

    public boolean isLandingAbility() {
        return landingAbility;
    }

    public void setLandingAbility(boolean landingAbility) {
        this.landingAbility = landingAbility;
    }

    @Override
    public String toString() {
        return "Port{" +
                "p_ID = " + p_ID +
                ", name = '" + name + '\'' +
                ", X = " + X +
                ", Y = " + Y +
                ", storingCapacity = " + storingCapacity +
                ", landingAbility = " + landingAbility +
                '}';
    }
}
