package Port;

import Container.Container;
import Vehicle.Vehicle;

import java.io.Serializable;
import java.util.ArrayList;

public class Port implements Serializable {
    private String port_ID;
    private String port_name;
    private double X;
    private double Y;
    private double storingCapacity;
    private boolean landingAbility;

    private ArrayList<Vehicle> vehicleHangar;

    private ArrayList<Container> containerHangar;

    //private ArrayList<Trip> tripRecord;

    public Port(String port_ID, String port_name, double X, double Y, double storingCapacity, boolean landingAbility){
        this.port_ID = port_ID;
        this.port_name = port_name;
        this.X = X;
        this.Y = Y;
        this.storingCapacity = storingCapacity;
        this.landingAbility = landingAbility;
    }

    public String getP_ID() {
        return port_ID;
    }

    public void setP_ID(int p_ID) {
        this.port_ID = port_ID;
    }

    public String getName() {
        return port_name;
    }

    public void setName(String name) {
        this.port_name = port_name;
    }

    public double getX() {
        return X;
    }

    public void setX(double x) {
        X = x;
    }

    public double getY() {
        return Y;
    }

    public void setY(double y) {
        Y = y;
    }

    public double getStoringCapacity() {
        return storingCapacity;
    }

    public void setStoringCapacity(double storingCapacity) {
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

    /*public ArrayList<Trip> getTripRecord() {
        return tripRecord;
    }

    public void setTripRecord(ArrayList<Trip> tripRecord) {
        this.tripRecord = tripRecord;
    }*/

    public boolean getLandingAbility() {
        return landingAbility;
    }

    public void setLandingAbility(boolean landingAbility) {
        this.landingAbility = landingAbility;
    }

    public boolean vehicleArrival(){

        return true;
    }
    public boolean vehicleDepart(){
        return true;
    }

    public boolean addContainer(){
        return true;
    }

    @Override
    public String toString() {
        return "Port{" +
                "port_ID = " + port_ID +
                ", port_name = '" + port_name + '\'' +
                ", X = " + X +
                ", Y = " + Y +
                ", storingCapacity = " + storingCapacity +
                ", landingAbility = " + landingAbility +
                '}';
    }
}
