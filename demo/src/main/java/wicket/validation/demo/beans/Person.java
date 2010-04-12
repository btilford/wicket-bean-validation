package wicket.validation.demo.beans;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: btilford
 * Date: Apr 11, 2010
 * Time: 4:29:55 PM
 * To change this template use File | Settings | File Templates.
 */
public class Person implements Serializable {


    private String name;

    private Address address;

    @NotNull(message = "person.name.notNull")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Valid
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
