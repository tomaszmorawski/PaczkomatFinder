package pl.allegro.PaczkomatFinder.Utility;

import org.springframework.stereotype.Component;
import pl.allegro.PaczkomatFinder.Models.InPost.DispatchPoint;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Component
public class CSVParser implements DataParserIteface {

    public List<DispatchPoint> getPointsFromDB() {
        Path file = Paths.get("points.csv");
        List<DispatchPoint> dispatchPointList = new ArrayList<>();
        List<String> lines;

        {
            try {
                lines = Files.readAllLines(file);
                lines.remove(0);

                for (String l : lines) {
                    String[] line = l.split(";");

                    DispatchPoint dispatchPoint = new DispatchPoint(
                            //type
                            line[0],
                            //name
                            line[1],
                            //address
                            line[2],
                            //postcode
                            line[3],
                            //city
                            line[4],
                            //lat
                            Float.parseFloat(line[6]),
                            //lng
                            Float.parseFloat(line[5]));

                    dispatchPointList.add(dispatchPoint);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return dispatchPointList;
    }
}
