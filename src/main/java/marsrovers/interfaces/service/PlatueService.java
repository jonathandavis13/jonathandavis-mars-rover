package marsrovers.interfaces.service;

import marsrovers.model.MarsRover;


public interface PlatueService {

    MarsRover[] moveRovers(Integer maxX, Integer maxY, MarsRover...rovers);


}
