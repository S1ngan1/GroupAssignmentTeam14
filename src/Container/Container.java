package Container;

import java.util.HashMap;

public class Container {
    private String containerType;
    private int id;
    private double weight;
    private static HashMap<String, Double> ship_Container;
    private static HashMap<String, Double> truck_Container;

    public Container(String containerType, int id, double weight){
        this.containerType = containerType;
        this.id = id;
        this.weight = weight;

    }

    //get the appropraite container type
    public String getContainerType() {
        if (setContainerType(this.containerType)){
            return containerType;
        }
        return "Enter the proper container type";
    }

    //Provide the available container types and force users to choose one
    public boolean setContainerType(String containerType) {
        String[] availableContainerType = {"Dry storage", "Open top", "Open side", "Refrigerated", "Liquid"};
        boolean matched = false;
        for (String type : availableContainerType){
            if (type.toLowerCase().equals(containerType.toLowerCase())){
                matched = true;
            }
        }
    //set the container type if it is valid
        if (matched){
            this.containerType = containerType;
            return true;
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    @Override
    public String toString() {
        if (!setContainerType(this.containerType)){
            return "No such container exist";
        }
        return "Container{" +
                "containerType = '" + containerType.toLowerCase() + '\'' +
                ", id = " + id +
                ", weight = " + weight +
                '}';
    }
}
