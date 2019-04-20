package pl.allegro.PaczkomatFinder.Controller;

import org.json.simple.parser.ParseException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.allegro.PaczkomatFinder.Models.InPost.DispatchPoint;
import pl.allegro.PaczkomatFinder.Utility.CSVParser;
import pl.allegro.PaczkomatFinder.Utility.NearPointsFinder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class APIController {

    @GetMapping("/nearestPoints")
    public List<DispatchPoint> index(@RequestParam Map<String, String> choice) {
        NearPointsFinder nearPointsFinder = new NearPointsFinder(new CSVParser());
        try {
            return nearPointsFinder.getPoints(choice);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }
}
