package marsrovers.data;


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

    @Autowired
    private Path path;

    @Autowired
    private WatchService watchService;

    @PostConstruct
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


            }

            boolean valid = key.reset();
            if(!valid){
                break;
            }
        }
    }




    public String fileReader(){
        File file = new File("input/test_file.txt");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            logger.trace(reader.readLine().toString());
        } catch (FileNotFoundException e) {
            logger.error(e.toString(),e);
        } catch (IOException e) {
            logger.error(e.toString(),e);
        }

        return "";
    }

}
