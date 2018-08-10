package marsrovers.business.service;

import marsrovers.exception.InvalidDirectionException;
import marsrovers.interfaces.service.PlatueService;
import marsrovers.model.Direction;
import marsrovers.model.Instruction;
import marsrovers.model.MarsRover;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import static marsrovers.model.Direction.*;

public class BasePlatueService implements PlatueService {

    private Logger logger = LoggerFactory.getLogger(BasePlatueService.class);

    @Override
    public MarsRover[] moveRovers(Integer maxX, Integer maxY, MarsRover... rovers) {

        for (MarsRover rover: rovers) {
            for(Instruction instruction : rover.getInstructions()){
                switch(instruction){
                    case TURN_LEFT:
                        turnLeft(rover);
                        break;
                    case TURN_RIGHT:
                        turnRight(rover);
                        break;
                    case MOVE_FORWARD:
                        moveFoward(rover);
                        break;
                    default:
                        //TODO exception here
                        logger.error("Invalid instruction given to rover : [{}]",instruction);
                }
            }

            logger.info(rover.toString());

        }
        return rovers;
    }

    public void turnLeft(MarsRover rover){
        switch (rover.getDirection()){
            case NORTH :
                rover.setDirection(WEST);
                break;
            case EAST :
                rover.setDirection(NORTH);
                break;
            case SOUTH :
                rover.setDirection(EAST);
                break;
            case WEST :
                rover.setDirection(SOUTH);
                break;
            default: throw new InvalidDirectionException();
        }

    }

    public void turnRight(MarsRover rover){

        switch (rover.getDirection()){
            case NORTH :
                rover.setDirection(EAST);
                break;
            case EAST :
                rover.setDirection(SOUTH);
                break;
            case SOUTH :
                rover.setDirection(WEST);
                break;
            case WEST :
                rover.setDirection(NORTH);
                break;
            default:  throw new InvalidDirectionException();
        }
    }

    public void moveFoward(MarsRover rover){
        switch (rover.getDirection()){
            case NORTH :
                rover.incrementYCoordinate();
                break;
            case EAST :
                rover.incrementXCoordinate();
                break;
            case SOUTH :
                rover.decrementYCoordinate();
                break;
            case WEST :
                rover.decrementXCoordinate();
                break;
            default:  throw new InvalidDirectionException();
        }
    }
}
