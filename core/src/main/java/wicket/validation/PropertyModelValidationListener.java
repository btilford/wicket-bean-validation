package wicket.validation;

import org.apache.wicket.Component;
import org.apache.wicket.application.IComponentOnBeforeRenderListener;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.AbstractPropertyModel;

import java.util.logging.Logger;

/**
 * Will apply validators to all FormComponents using an AbstractPropertyModel.
 *
 * @todo filter needs to be on the model object
 * @todo allow validation groups
 */
public class PropertyModelValidationListener implements IComponentOnBeforeRenderListener {


    private static final Logger logger = Logger.getLogger(PropertyModelValidationListener.class.getName());

    private final Class<?>[] filter;

    /**
     * Vanilla constructor
     */
    public PropertyModelValidationListener() {
        filter = null;
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
        if (applyValidator(component)) {
            FormComponent formComponent = (FormComponent) component;
            AbstractPropertyModel propertyModel = (AbstractPropertyModel) formComponent.getModel();
            if (propertyModel.getChainedModel() != null && propertyModel.getChainedModel().getObject() != null) {
                Class<?> modelType = propertyModel.getChainedModel().getObject().getClass();
                formComponent.add(BeanValidator.create(modelType, propertyModel.getPropertyExpression()));
                logger.finest("Adding BeanValidator to " + propertyModel.getPropertyExpression());
            }
        }
    }

    boolean applyValidator(Component component) {
        boolean apply = false;
        Class componentType = component.getClass();
        if (component instanceof FormComponent) {
            FormComponent formComponent = (FormComponent) component;
            if (filter.length == 0) {
                apply = true;
            } else {
                for (Class type : filter) {
                    if (type.isAnnotation() && componentType.isAnnotationPresent(type)) {
                        apply = true;
                        break;
                    } else if (type.isAssignableFrom(componentType)) {
                        apply = true;
                        break;
                    }
                }
            }
            apply = apply && (formComponent.getModel() instanceof AbstractPropertyModel);

        }
        return apply;
    }
}
