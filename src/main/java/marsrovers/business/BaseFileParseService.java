package marsrovers.business;

import marsrovers.exception.UnableToParseFileException;
import marsrovers.interfaces.FileParseService;
import marsrovers.model.Direction;
import marsrovers.model.Instruction;
import marsrovers.model.MarsRover;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseFileParseService implements FileParseService{

    private Logger logger = LoggerFactory.getLogger(BaseFileParseService.class);

    @Override
    public void parseFile(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuffer buffer = new StringBuffer();

            Object[] lines = reader.lines().toArray();
            List<MarsRover> rovers = new ArrayList<>();
            for(int i = 0 ;i < lines.length; i++){
                if(i == 0){
                    parsePlateauBoundaries(lines[0].toString(), i);
                } else {
                    MarsRover rover =  parseRoverLocation(lines[i].toString(),i);
                    i++;
                    rover.setInstructions(parseRoverInstructions(lines[i].toString(),i));
                    rovers.add(rover);
                }
            }
        } catch (FileNotFoundException e) {
            logger.error(e.toString(),e);
        }
    }


    @Override
    public  List<Instruction> parseRoverInstructions(String line, int lineNumber) {
        String[] string = line.split("");
        List<Instruction> instructions = new ArrayList<>();
        for (int i  = 0; i < string.length; i++){
            String input = string[i];
            instructions.add(parseInstruction(input,lineNumber));
        }
        return instructions;
    }

    private Instruction parseInstruction(String input, int lineNumber){
        if(input.equalsIgnoreCase("M")){
            return Instruction.MOVE_FORWARD;
        } else if( input.equalsIgnoreCase("L")){
            return Instruction.TURN_LEFT;
        } else if (input.equalsIgnoreCase("R")){
            return Instruction.TURN_RIGHT;
        } else {
            logger.error("Invalid instruction on line number : {}",lineNumber);
            throw new UnableToParseFileException("Invalid instruction on line number : "+lineNumber);
        }
    }

    @Override
    public MarsRover parseRoverLocation(String line, int lineNumber) {
        String[] string = line.split("\\s+");
        if(string.length !=3){
            logger.error("Unable to parse file. Error parsing rover location at line number line number : {}", lineNumber);
            throw new UnableToParseFileException("Unable to parse file. Error parsing line number : "+lineNumber);
        }
        MarsRover rover = new MarsRover();
        rover.setxCoordinate(Integer.parseInt(string[0]));
        rover.setyCoordinate(Integer.parseInt(string[1]));
        rover.setDirection(parseDirection(string[2]));
        return rover;
    }

    private Direction parseDirection(String s) {
        if(s.equalsIgnoreCase("n")){
            return Direction.NORTH;
        } else if(s.equalsIgnoreCase("e")){
            return Direction.EAST;
        } else if(s.equalsIgnoreCase("s")){
            return Direction.SOUTH;
        } else if(s.equalsIgnoreCase("w")){
            return Direction.WEST;
        } else{
            logger.error("Unable to parse file. Expected N,E,S, or W from file but found [{}]",s);
            throw new UnableToParseFileException("Expected N,E,S, or W from file but found " + s);
        }
    }

    @Override
    public Map<String, Integer> parsePlateauBoundaries(String line, int lineNumber){
        String[] string = line.split("\\s+");
        if(string.length !=2){
            logger.error("Unable to parse file. Error parsing Plateau Boundaries at line number : {}", lineNumber);
            throw new UnableToParseFileException("Unable to parse file. Error parsing line number : "+lineNumber);
        }
        try{
            Map<String, Integer> plateauBoundaries = new HashMap<>();

            plateauBoundaries.put("x", Integer.parseInt(string[0]));
            plateauBoundaries.put("y", Integer.parseInt(string[1]));
            return plateauBoundaries;
        }catch (NumberFormatException e){
            logger.error("Unable to parse boundaries : {}",e.toString(),e);
            throw new UnableToParseFileException("Unable to parse boundaries");
        }

    }

    @Override
    public String outputText(List<MarsRover> rovers) {
        StringBuffer buffer = new StringBuffer();
        rovers.forEach(r -> {
            buffer.append(r.getxCoordinate()+ " ");
            buffer.append(r.getyCoordinate()+ " ");
            buffer.append(directionToText(r.getDirection()) + "\n");
        });
        return buffer.toString();
    }

    private String directionToText(Direction direction){
        switch (direction){
            case NORTH:
                return "N";
            case EAST:
                return "E";
            case SOUTH:
                return "S";
            case WEST:
                return "W";
            default:
                logger.error("Cannot move direction [{}] to text.",direction);
        }
        return null;
    }

}
