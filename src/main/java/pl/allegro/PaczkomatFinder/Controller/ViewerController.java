package pl.allegro.PaczkomatFinder.Controller;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import pl.allegro.PaczkomatFinder.Models.InPost.DispatchPoint;
import pl.allegro.PaczkomatFinder.Utility.CSVParser;
import pl.allegro.PaczkomatFinder.Utility.NearPointsFinder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ViewerController {

    @GetMapping("/")
    public String index(Model model) {
        List<DispatchPoint> points = new ArrayList<>();
        try {
            Map<String, String> map = new HashMap<>();
            map.put("address", "Torun,stawkipoludniowe26");
            points =new  NearPointsFinder(new CSVParser()).getPoints(map);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        model.addAttribute("points", points.toArray());
        return "map";
    }

    @GetMapping("/{$town}/{$street}/{$no}")
    public String getPointsFromAddress(@PathVariable("$town") String town, @PathVariable("$street") String street, @PathVariable("$no") Long number, Model model) {
        List<DispatchPoint> points = new ArrayList<>();
        try {
            Map<String, String> map = new HashMap<>();
            map.put("address", town + "+" + street + "+" + number);
            points =new  NearPointsFinder(new CSVParser()).getPoints(map);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        model.addAttribute("points", points.toArray());
        return "map";
    }
}
