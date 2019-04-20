package pl.allegro.PaczkomatFinder.Utility;

import pl.allegro.PaczkomatFinder.Models.InPost.DispatchPoint;

import java.util.List;

public interface DataParserIteface {
    public List<DispatchPoint> getPointsFromDB();
}
