package marsrovers.business.service;

import marsrovers.exception.RoverCollisionException;
import marsrovers.exception.RoverOutOfBoundsException;
import marsrovers.interfaces.service.PlatueService;
import marsrovers.model.Instruction;
import marsrovers.model.MarsRover;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static marsrovers.model.Direction.*;

@Service
public class BasePlateauService implements PlatueService {

    private Logger logger = LoggerFactory.getLogger(BasePlateauService.class);

    @Override
    public List<MarsRover> moveRovers(Integer maxX, Integer maxY, List<MarsRover> rovers) {

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
                        sameLocation(rover,rovers);
                        offPlateau(maxX,maxY,rover);
                        break;
                    default:
                        logger.error("Invalid instruction [{}] given to rover ",instruction);
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
            default:
                logger.error("Invalid Direction [{}] for rover [{}]",rover.getDirection(),rover.toString());
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
            default:
                logger.error("Invalid Direction [{}] for rover [{}]",rover.getDirection(),rover.toString());
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
            default:
                logger.error("Invalid direction [{}] given to rover ",rover.getDirection());
        }
    }

    private void sameLocation(MarsRover currentRover, List<MarsRover> otherRovers){
        long numberOfRoversInSameLocation = otherRovers.stream().
                filter(r -> currentRover.getyCoordinate() == r.getyCoordinate()
                        && currentRover.getxCoordinate() == r.getxCoordinate()).count();
        if( numberOfRoversInSameLocation > 1 ){
            logger.error("The Instructions for the Rover [{}] will cause a collision with another rover", currentRover.toString());
            throw new RoverCollisionException();
        }
    }

    private void offPlateau(Integer maxX, Integer maxY, MarsRover rover){
        if( maxX < rover.getxCoordinate() || maxY < rover.getyCoordinate()
                || 0 > rover.getyCoordinate() || 0 > rover.getxCoordinate()){
            logger.error("The Instructions for the Rover will cause it to fall off the Plateau");
            throw new RoverOutOfBoundsException();
        }
    }
}
