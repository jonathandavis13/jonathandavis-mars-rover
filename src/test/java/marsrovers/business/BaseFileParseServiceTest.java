package marsrovers.business;

import marsrovers.exception.UnableToParseFileException;
import marsrovers.model.Direction;
import marsrovers.model.Instruction;
import marsrovers.model.MarsRover;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BaseFileParseServiceTest {

    @InjectMocks
    BaseFileParseService fileParseService;

    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void parseRoverInstructions() {
        String instructionString = "LMLMLMLMM";
        List<Instruction> actual = fileParseService.parseRoverInstructions(instructionString, 2);

        assertAll("instructions",
                () -> assertEquals(Instruction.TURN_LEFT, actual.get(0)),
                () -> assertEquals(Instruction.MOVE_FORWARD, actual.get(1)),
                () -> assertEquals(Instruction.TURN_LEFT, actual.get(2)),
                () -> assertEquals(Instruction.MOVE_FORWARD, actual.get(3)),
                () -> assertEquals(Instruction.TURN_LEFT, actual.get(4)),
                () -> assertEquals(Instruction.MOVE_FORWARD, actual.get(5)),
                () -> assertEquals(Instruction.TURN_LEFT, actual.get(6)),
                () -> assertEquals(Instruction.MOVE_FORWARD, actual.get(7)),
                () -> assertEquals(Instruction.MOVE_FORWARD, actual.get(8))
        );
    }

    @Test
    public void parseRoverInstructionsError(){
        String instructionString = "LMLM13LMLMM";
        assertThrows(UnableToParseFileException.class,
                () -> fileParseService.parseRoverInstructions(instructionString,2));

    }

    @Test
    public void parsePlateauBoundaries() {
        Map<String, Integer> actual = fileParseService.parsePlateauBoundaries("5 13", 0);
        assertAll("plateau boundaries",
                () -> assertEquals(5, actual.get("x").intValue()),
                () -> assertEquals(13, actual.get("y").intValue())
        );
    }

    @Test
    public void parsePlateauBoundariesError() {
        assertThrows(UnableToParseFileException.class,
                () -> fileParseService.parsePlateauBoundaries("y 13", 0));
    }

    @Test
    public void parseRoverLocation() {
        MarsRover actual = fileParseService.parseRoverLocation("5 1 E", 1);
        assertAll("create rover",
                () -> assertEquals(5, actual.getxCoordinate().intValue()),
                () -> assertEquals(1, actual.getyCoordinate().intValue()),
                () -> assertEquals(Direction.EAST, actual.getDirection())

        );
    }

    @Test
    public void parseRoverLocationError() {
        assertThrows(UnableToParseFileException.class,
                () -> fileParseService.parseRoverLocation("e 13", 1));
    }

    @Test
    public void parseRoverLocationErrorMissingParam() {
        assertThrows(UnableToParseFileException.class,
                () -> fileParseService.parseRoverLocation(" 13", 1));
    }

    @Test
    public void outputText(){
        String actual = fileParseService.outputText(getRoversForHappyPath());
        assertEquals("1 2 N\n3 3 E\n",actual);

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

}
