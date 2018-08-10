package marsrovers.model;


import java.util.ArrayList;
import java.util.List;

public class MarsRover {

    private Integer xCoordinate;
    private Integer yCoordinate;
    private String name;
    private Direction direction;
    private List<Instruction> instructions = new ArrayList<Instruction>();

    public MarsRover(Integer xCoordinate, Integer yCoordinate, String name, Direction direction) {
        this.xCoordinate = xCoordinate;
        this.yCoordinate = yCoordinate;
        this.name = name;
        this.direction = direction;
    }

    public Integer getxCoordinate() {
        return xCoordinate;
    }

    public void setxCoordinate(Integer xCoordinate) {
        this.xCoordinate = xCoordinate;
    }

    public Integer getyCoordinate() {
        return yCoordinate;
    }

    public void setyCoordinate(Integer yCoordinate) {
        this.yCoordinate = yCoordinate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void addInstruction(Instruction instruction){
        instructions.add(instruction);
    }

    public void incrementYCoordinate(){
        yCoordinate++;
    }

    public void decrementYCoordinate(){
        yCoordinate--;
    }

    public void incrementXCoordinate(){
        xCoordinate++;
    }

    public void decrementXCoordinate(){
        xCoordinate--;
    }



    @Override
    public String toString() {
        return "MarsRover{" +
                "xCoordinate=" + xCoordinate +
                ", yCoordinate=" + yCoordinate +
                ", name='" + name + '\'' +
                ", direction=" + direction +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MarsRover marsRover = (MarsRover) o;

        if (getxCoordinate() != null ? !getxCoordinate().equals(marsRover.getxCoordinate()) : marsRover.getxCoordinate() != null)
            return false;
        if (getyCoordinate() != null ? !getyCoordinate().equals(marsRover.getyCoordinate()) : marsRover.getyCoordinate() != null)
            return false;
        if (getName() != null ? !getName().equals(marsRover.getName()) : marsRover.getName() != null) return false;
        return getDirection() == marsRover.getDirection();
    }

    @Override
    public int hashCode() {
        int result = getxCoordinate() != null ? getxCoordinate().hashCode() : 0;
        result = 31 * result + (getyCoordinate() != null ? getyCoordinate().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (getDirection() != null ? getDirection().hashCode() : 0);
        return result;
    }
}
