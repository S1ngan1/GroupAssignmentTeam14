package Vehicle;

import Container.Container;

import java.util.ArrayList;
import java.util.Scanner;

public class Vehicle {
    //A vehicle has a name, id, and a carrier which hold a number of containers
    private String name;
    private String id;
    private ArrayList<Container> carrier;

    public Vehicle(String name, String id){
        //There is no parameter for carrier here. Every new vehicle has an empty carrier which is essentially an ArrayList
        this.name = name;
        this.id = id;
        this.carrier = new ArrayList<Container>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    //Add a new container to the vehicle's carrier
    public boolean loadContainer(){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Container type: ");
        String containerType = scanner.nextLine();
        System.out.print("Container ID: ");
        int containerID = scanner.nextInt();
        System.out.print("Container weight: ");
        float containerWeight = scanner.nextFloat();

        Container container = new Container(containerType, containerID, containerWeight);

        carrier.add(container);
        return true;
    }

    //Unload a container from the vehicle
    public void unloadContainer(){
        carrier.remove(carrier.size()-1);
    }
    //Display the general number of container
    public String displayGeneralNumberOfContainer(){
        return "Number of container: " + carrier.size();
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                " name = '" + name + '\'' +
                ", id = " + id +
                '}';
    }
}
