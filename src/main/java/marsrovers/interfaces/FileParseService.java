package marsrovers.interfaces;

import marsrovers.model.Instruction;
import marsrovers.model.MarsRover;

import java.io.File;
import java.util.List;
import java.util.Map;

public interface FileParseService {

    void parseFile(File file);

    List<Instruction> parseRoverInstructions(String line, int lineNumber);

    MarsRover parseRoverLocation(String line, int lineNumber);

    Map<String, Integer> parsePlateauBoundaries(String line, int lineNumber);

    String outputText(List<MarsRover> rovers);


}
