package wicket.validation.demo.beans;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: btilford
 * Date: Apr 13, 2010
 * Time: 9:20:51 PM
 * To change this template use File | Settings | File Templates.
 */
public class Address implements Serializable {


    private String lineOne;

    private String lineTwo;


    private String city;


    private String region;

    private String country;


    private String postalCode;

    @NotNull(message = "address.lineOne.notNull")
    public String getLineOne() {
        return lineOne;
    }

    public void setLineOne(String lineOne) {
        this.lineOne = lineOne;
    }

    public String getLineTwo() {
        return lineTwo;
    }

    public void setLineTwo(String lineTwo) {
        this.lineTwo = lineTwo;
    }

    @NotNull(message = "address.city.notNull")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @NotNull(message = "address.region.notNull")
    public String getRegion() {
        return region;
    }


    public void setRegion(String region) {
        this.region = region;
    }

    @NotNull(message = "address.country.notNull")
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @NotNull(message = "address.postalCode.notNull")
    @Pattern(regexp = "(\\d{5}(-?\\d{4}?))", message = "address.postalCode.pattern")
    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
