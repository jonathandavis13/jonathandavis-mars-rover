package marsrovers.interfaces.service;

import marsrovers.model.MarsRover;

import java.util.List;


public interface PlatueService {

    List<MarsRover> moveRovers(Integer maxX, Integer maxY, List<MarsRover> rovers);


}
