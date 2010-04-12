package wicket.validation;

import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.AbstractFormValidator;
import org.apache.wicket.model.StringResourceModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: btilford
 * Date: Apr 11, 2010
 * Time: 7:00:46 PM
 * To change this template use File | Settings | File Templates.
 */
public class BeanFormValidator<T> extends AbstractFormValidator {
    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(BeanFormValidator.class.getName());
    private final Class<?>[] groups;
    private final Class<T> modelType;
    private final FormComponent<?>[] components;

    protected BeanFormValidator(final Class<T> modelType, final Class<?>[] groups) {
        this.groups = groups;
        this.modelType = modelType;
        /*
        No way to resolve between form components and bean properties that I know of.
         */
        this.components = new FormComponent<?>[0];
    }

    @Override
    public FormComponent<?>[] getDependentFormComponents() {
        return this.components;
    }

    @Override
    public void validate(Form<?> form) {
        //crazy wicket wildcard generics ... Form<?> should be Form<T>
        if (modelType.isAssignableFrom(form.getModelObject().getClass())) {
            Set<ConstraintViolation<Object>> violations = validateValue(form.getModelObject());
            for (ConstraintViolation<Object> violation : violations) {
                form.error(new StringResourceModel(violation.getMessageTemplate(), form, null).getString());
                logger.debug("validation error \"" + violation.getMessageTemplate() + "\"");
            }
        } else {
            //some exception should be thrown.
            logger.error(modelType.getName() + " is not assignable from " + form.getModelObject().getClass().getName());
        }

    }

    Set<ConstraintViolation<Object>> validateValue(Object value) {
        return getValidator().validate(value, groups);
    }

    /**
     * Returns the default validator configured for the application. Subclasses can override if needed.
     *
     * @return
     */
    protected Validator getValidator() {
        return Validation.buildDefaultValidatorFactory().getValidator();
    }

    public static <T> BeanFormValidator<T> create(final Class<T> modelType, final Class<?>... groups) {
        return new BeanFormValidator<T>(modelType, groups);
    }


}
