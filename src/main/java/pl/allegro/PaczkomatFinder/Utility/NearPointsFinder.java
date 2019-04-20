package pl.allegro.PaczkomatFinder.Utility;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import pl.allegro.PaczkomatFinder.Models.InPost.Address;
import pl.allegro.PaczkomatFinder.Models.InPost.DispatchPoint;

import java.util.*;

import static java.util.Map.Entry.comparingByValue;
import static java.util.stream.Collectors.toMap;

@Service
public class NearPointsFinder {

    private final DataParserIteface dataParser;

    public NearPointsFinder(DataParserIteface dataParser) {
        this.dataParser = dataParser;
    }


    public List<DispatchPoint> getPoints(Map<String, String> choiceMap) throws ParseException {

        List<DispatchPoint> dispatchPointList = dataParser.getPointsFromDB();
        Address customerAddress = AddressParser.getAddressFromGoogle(choiceMap.get("address"));
        List<DispatchPoint> nearestDispatchPoints = getXNearestPoints(customerAddress, dispatchPointList, 5);

        for (DispatchPoint point : nearestDispatchPoints) {
            System.out.println(point);
        }
        nearestDispatchPoints.add(AddressParser.AddressToDipsachoint(customerAddress));
        return nearestDispatchPoints;
    }

    private List<DispatchPoint> getXNearestPoints(Address customerAddress, List<DispatchPoint> dispatchPointList, Integer count) {
        Map<Integer, Double> pointWithDistance = calculateDistance(dispatchPointList, customerAddress);
        Map<Integer, Double> sorted = sortMap(pointWithDistance);

        List<DispatchPoint> dispatchPoints = new ArrayList<>();
        int i = 0;
        final int numberOfPoints = 5;
        for (Map.Entry<Integer, Double> entry : sorted.entrySet()) {
            if (++i <= numberOfPoints) {
                dispatchPoints.add(dispatchPointList.get(entry.getKey()));
            } else {
                break;
            }
        }
        return dispatchPoints;
    }

    private Map calculateDistance(List<DispatchPoint> dispatchPointList, Address customerAddress) {
        Map<Integer, Double> pointWithDistance = new HashMap<>();
        float tmpLat;
        float tmpLng;

        for (int i = 0; i < dispatchPointList.size(); i++) {
            DispatchPoint currentPoint = dispatchPointList.get(i);
            tmpLat = currentPoint.getLat() - customerAddress.getLat();
            tmpLng = currentPoint.getLng() - customerAddress.getLng();

            pointWithDistance.put(i, Math.sqrt(Math.pow(tmpLat, 2) + Math.pow(tmpLng, 2)));
        }
        return pointWithDistance;
    }

    private Map sortMap(Map<Integer, Double> pointWithDistance) {
        return pointWithDistance
                .entrySet()
                .stream()
                .sorted(comparingByValue())
                .collect(
                        toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2,
                                LinkedHashMap::new));

    }
}
