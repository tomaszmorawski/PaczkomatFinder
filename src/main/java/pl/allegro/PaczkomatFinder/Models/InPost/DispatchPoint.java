package pl.allegro.PaczkomatFinder.Models.InPost;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class DispatchPoint {
    private String type;
    private String name;
    private String address;
    private String postCode;
    private String city;
    private float lat;
    private float lng;
}
