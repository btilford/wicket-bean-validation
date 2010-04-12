package wicket.validation;

import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentOnBeforeRenderListener;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.AbstractPropertyModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Will apply validators to all FormComponents using an AbstractPropertyModel.
 *
 * @todo filter needs to be on the model object
 * @todo allow validation groups
 */
public class PropertyModelValidationListener implements IComponentOnBeforeRenderListener {


    private static final Logger logger = LoggerFactory.getLogger(PropertyModelValidationListener.class.getName());

    private final Class<?>[] filter;

    /**
     * Vanilla constructor
     */
    public PropertyModelValidationListener() {
        filter = new Class<?>[0];
    }


    /**
     * @param filter
     * @todo
     */
    private PropertyModelValidationListener(Class<?>... filter) {
        this.filter = new Class<?>[0];
    }

    @Override
    public void onBeforeRender(final Component component) {
        logger.trace("PropertyModelValidationListener.onBeforeRender(" + component.getId() + ")");
        if (applyValidator(component)) {
            FormComponent formComponent = (FormComponent) component;
            AbstractPropertyModel propertyModel = (AbstractPropertyModel) formComponent.getModel();
            if (propertyModel.getChainedModel() != null && propertyModel.getChainedModel().getObject() != null) {
                Class<?> modelType = propertyModel.getChainedModel().getObject().getClass();
                logger.debug("Adding BeanValidator [modelType:" + modelType.getName() + ",propertyName:" + propertyModel.getPropertyExpression() + " to " + propertyModel.getPropertyExpression());
                formComponent.add(BeanValidator.create(modelType, propertyModel.getPropertyExpression()));
            }
        }
    }

    boolean applyValidator(Component component) {
        boolean apply = false;
        Class componentType = component.getClass();
        if (component instanceof FormComponent) {
            logger.trace(component.getId() + " is a FormComponent");
            FormComponent formComponent = (FormComponent) component;
            if (filter.length == 0) {
                logger.trace("Filtering is off.");
                apply = true;
            } else {
                logger.trace("Filtering is on.");
                for (Class type : filter) {
                    if (type.isAnnotation() && componentType.isAnnotationPresent(type)) {
                        apply = true;
                        break;
                    } else if (type.isAssignableFrom(componentType)) {
                        apply = true;
                        break;
                    }
                }
                logger.trace("Filter result ==" + apply);
            }
            apply = apply && (formComponent.getModel() instanceof AbstractPropertyModel);

        }
        logger.trace("PropertyModelValidationListener.applyValidator == " + apply);
        return apply;
    }
}
