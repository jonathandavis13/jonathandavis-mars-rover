package marsrovers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchService;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;

@Configuration
public class ReaderConfiguration {

    private Logger logger = LoggerFactory.getLogger(ReaderConfiguration.class);

    @Bean
    public WatchService watchService() throws IOException {
        return  FileSystems.getDefault().newWatchService();
    }

    @Bean
    public Path path(){
        Path path = Paths.get("/Users/jonathandavis/IdeaProjects/marsrovers/input" +
                "");
        try {
            path.register(watchService(), ENTRY_CREATE);
        } catch (IOException e) {
            logger.error(e.toString(),e);
        }
        return path;

    }
}
