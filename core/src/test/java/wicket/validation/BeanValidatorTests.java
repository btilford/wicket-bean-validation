package wicket.validation;

import org.junit.Test;

import javax.validation.ConstraintViolation;
import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * User: btilford
 * Date: Apr 12, 2010
 * Time: 10:54:50 PM
 */
public class BeanValidatorTests {


    @Test
    public void testValidateBean_Valid() {
        System.out.println("testValidateBean_Valid");
        BeanValidator validator = BeanValidator.create();
        Person person = new Person.Builder().name("Bob").build();
        assertTrue(validator.validateBean(person).isEmpty());
    }

    @Test
    public void testValidateBeanProperty_Valid() {
        System.out.println("testValidateBeanProperty_Valid");
        BeanValidator validator = BeanValidator.create(Person.class, "name");
        assertTrue(validator.validateBeanProperty(new Person.Builder().name("Joe").build()).isEmpty());
    }

    @Test
    public void testValidateBean_Invalid() {
        System.out.println("testValidateBean_Invalid");
        BeanValidator validator = BeanValidator.create();
        Person person = new Person.Builder().build();
        Collection<ConstraintViolation<?>> violations = validator.validateBean(person);
        System.out.println("Violations:");
        for (ConstraintViolation<?> violation : violations) {
            System.out.println(violation);
        }
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testValidateBeanProperty_Invalid() {
        System.out.println("testValidateBeanProperty_Invalid");
        BeanValidator validator = BeanValidator.create(Person.class, "phoneNumber");

        Collection<ConstraintViolation<?>> violations = validator.validateBeanProperty("123");
        System.out.println("Violations:");
        for (ConstraintViolation<?> violation : violations) {
            System.out.println(violation);
        }
        assertFalse(violations.isEmpty());
    }

    @Test
    public void testValidateBeanProperty_OnlineGroup_Valid() {
        System.out.println("testValidateBeanProperty_OnlineGroup_Valid");
        BeanValidator validator = BeanValidator.create(Person.class, "email", Person.Online.class);

        Collection<ConstraintViolation<?>> violations = validator.validateBeanProperty("email@email.com");
        System.out.println("Violations:");
        for (ConstraintViolation<?> violation : violations) {
            System.out.println(violation);
        }
        assertTrue(violations.isEmpty());

    }

    @Test
    public void testValidateBeanProperty_OnlineGroup_Invalid() {
        System.out.println("testValidateBeanProperty_OnlineGroup_Invalid");
        BeanValidator validator = BeanValidator.create(Person.class, "email", Person.Online.class);

        Collection<ConstraintViolation<?>> violations = validator.validateBeanProperty("email");
        System.out.println("Violations:");
        for (ConstraintViolation<?> violation : violations) {
            System.out.println(violation);
        }
        assertFalse(violations.isEmpty());

    }

    //@todo real wicket tests

}
