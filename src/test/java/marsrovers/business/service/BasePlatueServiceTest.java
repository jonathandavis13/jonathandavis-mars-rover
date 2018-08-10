package marsrovers.business.service;


import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import marsrovers.model.Direction;
import marsrovers.model.Instruction;
import marsrovers.model.MarsRover;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;


class BasePlatueServiceTest {

    @InjectMocks
    BasePlatueService platueService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void moveRovers() {
        Integer maxX = 5;
        Integer maxY = 5;
        final MarsRover[] actual = platueService.moveRovers(maxX,maxY,getRovers());

        assertAll("rovers",
                () -> assertEquals(Direction.NORTH, actual[0].getDirection()),
                () -> assertEquals( 1, actual[0].getxCoordinate().intValue(), "X Coordinate"),
                () -> assertEquals( 3, actual[0].getyCoordinate().intValue(), "Y Coordinate"),
                () -> assertEquals(Direction.EAST,actual[1].getDirection()),
                () -> assertEquals( 5, actual[1].getxCoordinate().intValue(), "X Coordinate"),
                () -> assertEquals( 1, actual[1].getyCoordinate().intValue(), "Y Coordinate")
                );


    }

    private MarsRover[] getRovers() {
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
        MarsRover[] rovers =  {rover1,rover2};
        return rovers;
    }


}