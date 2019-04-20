package pl.allegro.PaczkomatFinder.Utility;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pl.allegro.PaczkomatFinder.Models.InPost.Address;
import pl.allegro.PaczkomatFinder.Models.InPost.DispatchPoint;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AddressParser {


    public static Address getAddressFromGoogle(String requestAddress) throws ParseException {

        InputStream inputStream = getInputStreamFromGoogleAPI(requestAddress);
        String json = getStringFromGoogleApiInputStream(inputStream);


        //now parse
        JSONObject jb = (JSONObject) new JSONParser().parse(json);

        //now read
        String status = jb.get("status").toString();
        if (status.equals("OK")) {
            JSONArray jsonObject1 = (JSONArray) jb.get("results");
            JSONObject jsonObject2 = (JSONObject) jsonObject1.get(0);
            JSONObject jsonObject3 = (JSONObject) jsonObject2.get("geometry");
            JSONObject location = (JSONObject) jsonObject3.get("location");
            String formattedAddress = jsonObject2.get("formatted_address").toString();

            String[] address = formattedAddress.split(", ");

            Address customerAddress = new Address(
                    //street
                    address[0],
                    //city
                    address[1].split(" ")[1],
                    //postCode
                    address[1].split(" ")[0],
                    //lat
                    Float.parseFloat(location.get("lat").toString()),
                    //lng
                    Float.parseFloat(location.get("lng").toString())
            );
            System.out.println(customerAddress);

            return customerAddress;
        } else {
            return null;
        }

    }

    private static String getStringFromGoogleApiInputStream(InputStream inputStream) {
        String json = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "utf-8"), 8);
            StringBuilder sbuild = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sbuild.append(line);
            }
            inputStream.close();
            json = sbuild.toString();
        } catch (Exception e) {
        }
        return json;
    }

    private static InputStream getInputStreamFromGoogleAPI(String requestAddress) {
        InputStream inputStream = null;
        try {
            HttpPost post = new HttpPost("https://maps.googleapis.com/maps/api/geocode/json?address=" + requestAddress + "&key=AIzaSyCYRlvguq6hrutMRLY13FGAFbLr8sAdoO4");
            inputStream = new DefaultHttpClient().execute(post).getEntity().getContent();
        } catch (Exception e) {
        }
        return inputStream;
    }

    public static DispatchPoint AddressToDipsachoint(Address address){
        DispatchPoint dispatchPoint = new DispatchPoint();
        dispatchPoint.setAddress("Twój Adres");
        dispatchPoint.setLat(address.getLat());
        dispatchPoint.setLng(address.getLng());
        return dispatchPoint;
    }
}
