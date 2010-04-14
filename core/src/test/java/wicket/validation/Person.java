package wicket.validation;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Created by IntelliJ IDEA.
 * User: btilford
 * Date: Apr 12, 2010
 * Time: 10:45:49 PM
 */
public class Person {

    @NotNull(message = "person.name.notNull")
    private String name;


    @Min(value = 1L, message = "person.areaCode.min")
    @Max(value = 3L, message = "person.areaCode.max")
    private Integer areaCode;

    @Pattern(regexp = "\\d\\d\\d-?\\d\\d\\d\\d", message = "person.phoneNumber.pattern")
    private String phoneNumber;

    @NotNull(groups = Online.class, message = "person.email.notNull")
    @Pattern.List({
            @Pattern(regexp = "\\w+@\\w+\\.\\w+", message = "person.email.pattern"),
            @Pattern(regexp = "\\w+@\\w+\\.\\w+", message = "person.email.pattern", groups = Online.class)
    })
    private String email;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(Integer areaCode) {
        this.areaCode = areaCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Person person = (Person) o;

        if (areaCode != null ? !areaCode.equals(person.areaCode) : person.areaCode != null) return false;
        if (email != null ? !email.equals(person.email) : person.email != null) return false;
        if (name != null ? !name.equals(person.name) : person.name != null) return false;
        if (phoneNumber != null ? !phoneNumber.equals(person.phoneNumber) : person.phoneNumber != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (areaCode != null ? areaCode.hashCode() : 0);
        result = 31 * result + (phoneNumber != null ? phoneNumber.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }

    public static interface Online {
    }


    public static class Builder {
        private Person person = new Person();

        public Builder name(String name) {
            person.setName(name);
            return this;
        }

        public Builder areaCode(Integer areaCode) {
            person.setAreaCode(areaCode);
            return this;
        }

        public Builder phoneNumber(String phoneNumber) {
            person.setPhoneNumber(phoneNumber);
            return this;
        }

        public Builder email(String email) {
            person.setEmail(email);
            return this;
        }

        public Person build() {
            return person;
        }
    }
}
