package marsrovers.data;


import marsrovers.interfaces.FileParseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.*;
import java.net.URI;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.OVERFLOW;

@Service
public class RoverFileReader {

    private Logger logger = LoggerFactory.getLogger(RoverFileReader.class);

    private final static String INPUT_FOLDER = "input/";

    @Autowired
    private Path path;

    @Autowired
    private WatchService watchService;

    @Autowired
    private FileParseService fileParseService;

    @Autowired
    private RoverFileWriter roverFileWriter;

    @PostConstruct
    private void start(){
        readExistingFiles();
        processFiles();
    }


    void processFiles() {
        logger.info("start process files");
        while(true){
            WatchKey key;
            try {
                key = watchService.take();
                logger.info(key.toString());
            } catch (InterruptedException e) {
                logger.error(e.toString(),e);
                return;
            }

            for (WatchEvent event : key.pollEvents()){
                WatchEvent.Kind kind = event.kind();

                if(kind == OVERFLOW){
                    continue;
                }

                WatchEvent watchEvent = event;
                Path filename = (Path) watchEvent.context();
                logger.info("filename : {}",filename.getFileName());
                File folder = new File(INPUT_FOLDER+filename);
                processFile(folder);

            }

            boolean valid = key.reset();
            if(!valid){
                break;
            }
        }
    }

    private void processFile(File file){
        try{
            logger.info("reading [{}]",file.getPath());
            String roverDestination = fileParseService.parseFile(file);
            logger.info("Rover Final Destination : [{}]",roverDestination);
            roverFileWriter.writeFile(roverDestination);
        }catch (Exception e){
            logger.error(e.toString(),e);
        }
    }


    public void readExistingFiles(){
        File folder = new File(INPUT_FOLDER);
        if (folder.isDirectory()){
            logger.info("[{}] is  a valid directory",folder.getPath());
        }
        for(File file : folder.listFiles()){
            processFile(file);

        }
    }

}
