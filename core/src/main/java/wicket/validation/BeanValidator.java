package wicket.validation;

import org.apache.wicket.validation.IValidatable;
import org.apache.wicket.validation.ValidationError;
import org.apache.wicket.validation.validator.AbstractValidator;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;


/**
 * Validator that can be assigned to FormComponents.
 *
 * @param <T>
 * @param <V>
 */
public class BeanValidator<T, V> extends AbstractValidator<V> {
    private static final long serialVersionUID = 1L;
    private final String propertyName;
    private final Class<?>[] groups;
    private final Class<T> modelType;

    private static final Logger logger = Logger.getLogger(BeanValidator.class.getName());


    /**
     * @param modelType    the Bean.class that will be validated. Required when using the value being validated is a
     *                     property of the bean.
     * @param propertyName the property name to validate.
     * @param groups       optional groups
     */
    protected BeanValidator(final Class<T> modelType, final String propertyName, final Class<?>... groups) {
        super();
        this.modelType = modelType;
        this.propertyName = propertyName;
        this.groups = groups;

    }


    @Override
    protected void onValidate(IValidatable<V> validatable) {

        //do the validation
        Set<ConstraintViolation<?>> violations = validateConstraints(validatable.getValue());

        boolean errors = violations != null && violations.size() > 0;
        ValidationError validationError = new ValidationError();

        for (ConstraintViolation<?> violation : violations) {
            /*
            Wicket already has resource resolvers that will work (better) so we probably doen't need a
            MessageInterpolator implementation and should just pass the key.
             */
            validationError.addMessageKey(violation.getMessageTemplate());
            logger.fine(violation.getMessage());
        }
        if (errors) {
            validatable.error(validationError);
        }
    }

    /**
     * This will validate an entire bean. It will be called when no this.modelType or this.propertyName
     * are assigned.
     *
     * @param value the bean
     * @return
     */
    Set<ConstraintViolation<V>> validateBean(final V value) {
        assert getValidator() != null;
        return getValidator().validate(value, groups);
    }


    /**
     * This will validate the value using the validation defined in this.modelType on this.propertyName.
     *
     * @param value
     * @return
     */
    Set<ConstraintViolation<T>> validateBeanProperty(final V value) {
        return getValidator().validateValue(modelType, propertyName, value, groups);
    }


    /**
     * Will call either validateBean or validateBeanProperty depending on wether this.propertyName has been
     * specified.
     *
     * @param value
     * @return
     */
    protected Set<ConstraintViolation<?>> validateConstraints(V value) {
        Set<ConstraintViolation<?>> violations = new HashSet<ConstraintViolation<?>>();
        if (propertyName == null) {
            violations.addAll(validateBean(value));
        } else {
            violations.addAll(validateBeanProperty(value));
        }
        return violations;
    }

    /**
     * Returns the default validator configured for the application. Subclasses can override if needed.
     *
     * @return
     */
    protected Validator getValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    //-------------------------------------------------------------------------------------------------------
    // These utility methods hide the generics from the calling code without using wildcards everywhere...
    // Method names probably should be better.
    // e.g.
    // new TextField("someProperty").add(BeanValidator.create(SomeBean.class,"someProperty"))
    // is the  equivalent of calling
    // new TextField("someProperty").add(new BeanValidator<SomeBean,String>(SomeBean.class,"someProperty"))
    // or
    // new TextField("someProperty").add(new BeanValidator<SomeBean,SomeBean>())
    //-------------------------------------------------------------------------------------------------------

    /**
     * Will create a validator capeable of validating an entire bean (not just a property of that bean)
     *
     * @param groups optional var-args of Validation groups
     * @param <T>    the Type of the bean being validated. (and value being validated in this case)
     * @return
     */
    public static <T> BeanValidator<T, T> create(final Class<?>... groups) {
        return new BeanValidator<T, T>(null, null, groups);
    }

    /**
     * Same as previous version but accepts a Collection for the groups argument.
     *
     * @param groups
     * @param <T>
     * @return
     */
    public static <T> BeanValidator<T, T> create(final Collection<Class<?>> groups) {
        Class<?>[] groupArray = new Class<?>[groups.size()];
        return new BeanValidator<T, T>(null, null, groups.toArray(groupArray));
    }

    /**
     * Will create a validator which will validate a single property of a bean.
     *
     * @param modelType    Class type of the bean where the validation constraints are applied.
     * @param propertyName name of the property in modelType you are validating.
     * @param groups       validation groups
     * @param <T>          the type of bean being validated (e.g. modelType)
     * @param <V>          the value type (e.g. given an instance of T or modelType value will be modelType.propertyName)
     * @return
     */
    public static <T, V> BeanValidator<T, V> create(final Class<T> modelType, final String propertyName, final Class<?>... groups) {
        return new BeanValidator<T, V>(modelType, propertyName, groups);
    }


    /**
     * Same as previous but accepts a Collection for the validation groups.
     *
     * @param modelType
     * @param propertyName
     * @param groups
     * @param <T>
     * @param <V>
     * @return
     */
    public static <T, V> BeanValidator<T, V> create(Class<T> modelType, final String propertyName, final Collection<Class<?>> groups) {
        Class<?>[] groupArray = new Class<?>[groups.size()];
        return new BeanValidator<T, V>(modelType, propertyName, groups.toArray(groupArray));
    }
}
