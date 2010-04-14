package wicket.validation.demo;


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

    @NotNull(message = "Address line is required.")
    private String lineOne;

    private String lineTwo;

    @NotNull(message = "Postal Code is required.")
    @Pattern(regexp = "\\d")
    private String postalCode;
}
