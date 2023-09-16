//(weight * weight/km * km ) * numberOfContainer
package GroupAssignment.Container;

import java.util.HashMap;

public class Container {
    private String id;
    private String containerType;
    private double weight;
    private static HashMap<String, Double> ship_Container;
    private static HashMap<String, Double> truck_Container;

    public Container(String id, String containerType, double weight){
        this.id = id;
        this.containerType = containerType;
        this.weight = weight;

    }

    //get the appropriate container type
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
                " id = " + id +
                ", containerType = '" + containerType.toLowerCase() + '\'' +
                ", weight = " + weight +
                '}';
    }
}
