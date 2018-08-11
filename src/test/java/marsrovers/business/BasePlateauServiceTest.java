package marsrovers.business;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import marsrovers.exception.RoverCollisionException;
import marsrovers.exception.RoverOutOfBoundsException;
import marsrovers.model.Direction;
import marsrovers.model.Instruction;
import marsrovers.model.MarsRover;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;


class BasePlateauServiceTest {

    @InjectMocks
    private BasePlateauService plateauService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void moveRovers_happyPath() {
        Integer maxX = 5;
        Integer maxY = 5;
        List<MarsRover> actual = plateauService.moveRovers(maxX,maxY,getRoversForHappyPath());

        assertAll("rovers",
                () -> assertEquals(Direction.NORTH, actual.get(0).getDirection()),
                () -> assertEquals( 1, actual.get(0).getxCoordinate().intValue(), "X Coordinate"),
                () -> assertEquals( 3, actual.get(0).getyCoordinate().intValue(), "Y Coordinate"),
                () -> assertEquals(Direction.EAST,actual.get(1).getDirection()),
                () -> assertEquals( 5, actual.get(1).getxCoordinate().intValue(), "X Coordinate"),
                () -> assertEquals( 1, actual.get(1).getyCoordinate().intValue(), "Y Coordinate")
                );


    }

    @Test
    void roverCollision(){
        Integer maxX = 5;
        Integer maxY = 5;

        Throwable exception = assertThrows(RoverCollisionException.class,
                () -> {plateauService.moveRovers(maxX,maxY,getRoversForRoverCollision());});
        assertEquals("The Instructions for the Rover will cause a collision with another rover",exception.getMessage());
    }

    @Test
    void roverFallsOffPlatue(){
        Integer maxX = 5;
        Integer maxY = 5;

        Throwable exception = assertThrows(RoverOutOfBoundsException.class,
                () -> {plateauService.moveRovers(maxX,maxY,getRoversForRoverOffPlateau());});
        assertEquals("The Instructions for the Rover will cause it to fall off the Plateau",exception.getMessage());

    }

    private List<MarsRover> getRoversForHappyPath() {
        MarsRover rover1 = new MarsRover(1,2,"rover1", Direction.NORTH);
        rover1.addInstruction(Instruction.TURN_LEFT);
        rover1.addInstruction(Instruction.MOVE_FORWARD);
        rover1.addInstruction(Instruction.TURN_LEFT);
        rover1.addInstruction(Instruction.MOVE_FORWARD);
        rover1.addInstruction(Instruction.TURN_LEFT);
        rover1.addInstruction(Instruction.MOVE_FORWARD);
        rover1.addInstruction(Instruction.TURN_LEFT);
        rover1.addInstruction(Instruction.MOVE_FORWARD);
        rover1.addInstruction(Instruction.MOVE_FORWARD);
        MarsRover rover2 = new MarsRover(3,3,"rover2", Direction.EAST);
        rover2.addInstruction(Instruction.MOVE_FORWARD);
        rover2.addInstruction(Instruction.MOVE_FORWARD);
        rover2.addInstruction(Instruction.TURN_RIGHT);
        rover2.addInstruction(Instruction.MOVE_FORWARD);
        rover2.addInstruction(Instruction.MOVE_FORWARD);
        rover2.addInstruction(Instruction.TURN_RIGHT);
        rover2.addInstruction(Instruction.MOVE_FORWARD);
        rover2.addInstruction(Instruction.TURN_RIGHT);
        rover2.addInstruction(Instruction.TURN_RIGHT);
        rover2.addInstruction(Instruction.MOVE_FORWARD);
        List<MarsRover> rovers = new ArrayList<>();
        rovers.add(rover1);rovers.add(rover2);
        return rovers;
    }

    private List<MarsRover> getRoversForRoverCollision(){
        MarsRover rover1 = new MarsRover(1,1,"rover1", Direction.NORTH);
        rover1.addInstruction(Instruction.MOVE_FORWARD);
        rover1.addInstruction(Instruction.MOVE_FORWARD);
        rover1.addInstruction(Instruction.TURN_RIGHT);
        rover1.addInstruction(Instruction.MOVE_FORWARD);
        rover1.addInstruction(Instruction.MOVE_FORWARD);
        MarsRover rover2 = new MarsRover(3,3,"rover2", Direction.EAST);

        List<MarsRover> rovers = new ArrayList<>();
        rovers.add(rover1);rovers.add(rover2);
        return rovers;
    }

    private List<MarsRover> getRoversForRoverOffPlateau(){
        MarsRover rover1 = new MarsRover(4,4,"rover1", Direction.NORTH);
        rover1.addInstruction(Instruction.MOVE_FORWARD);
        rover1.addInstruction(Instruction.MOVE_FORWARD);
        rover1.addInstruction(Instruction.MOVE_FORWARD);
        List<MarsRover> rovers = new ArrayList<>();
        rovers.add(rover1);
        return rovers;
    }

}