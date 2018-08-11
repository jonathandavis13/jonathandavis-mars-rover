package marsrovers.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

@Service
public class RoverFileWriter {

    private Logger logger = LoggerFactory.getLogger(RoverFileWriter.class);

    public void writeFile(String string){
        try {
            PrintWriter writer = new PrintWriter("mars-rover-final-location.txt","UTF-8");
            writer.print(string);
            writer.close();
        } catch (FileNotFoundException e) {
            logger.error(e.toString(),e);
        } catch (UnsupportedEncodingException e) {
            logger.error(e.toString(),e);
        }

    }
}
