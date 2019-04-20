package pl.allegro.PaczkomatFinder.Models.InPost;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class Address {

    private String street;
    private String city;
    private String postCode;
    private float lat;
    private float lng;
}
