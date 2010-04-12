package wicket.validation.demo;

import org.apache.wicket.PageParameters;
import org.apache.wicket.feedback.ContainerFeedbackMessageFilter;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wicket.validation.BeanFormValidator;
import wicket.validation.demo.beans.Address;
import wicket.validation.demo.beans.Person;

/**
 * Homepage
 */
public class HomePage extends WebPage {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(HomePage.class.getName());

    // TODO Add any page properties or variables here

    /**
     * Constructor that is invoked when page is invoked without a session.
     *
     * @param parameters Page parameters
     */
    public HomePage(final PageParameters parameters) {

        // Add the simplest type of label
        add(new Label("message", "If you see this message wicket is properly configured and running"));


        Person person = new Person();
        person.setAddress(new Address());


        Form<Person> personForm = new Form<Person>("person", new CompoundPropertyModel<Person>(person));
        personForm.add(new FeedbackPanel("feedback", new ContainerFeedbackMessageFilter(personForm)),
                new TextField<String>("name"),
                new TextField<String>("address.lineOne"),
                new TextField<String>("address.lineTwo"),
                new TextField<String>("address.city"),
                new TextField<String>("address.region"),
                new TextField<String>("address.country"),
                new TextField<String>("address.postalCode"),
                new Button("submit") {
                    @Override
                    public void onSubmit() {
                        super.onSubmit();
                        info("No Errors");
                    }
                });

        this.add(personForm);


        final Person person2 = new Person();
        person2.setAddress(new Address());
        final TextField<String> name = new TextField<String>("name", Model.of(person2.getName()));
        final TextField<String> addressLineOne = new TextField<String>("address.lineOne", Model.of(person2.getAddress().getLineOne()));
        final TextField<String> addressLineTwo = new TextField<String>("address.lineTwo", Model.of(person2.getAddress().getLineTwo()));
        final TextField<String> addressCity = new TextField<String>("address.city", Model.of(person2.getAddress().getCity()));
        final TextField<String> addressRegion = new TextField<String>("address.region", Model.of(person2.getAddress().getRegion()));
        final TextField<String> addressCountry = new TextField<String>("address.country", Model.of(person2.getAddress().getCountry()));
        final TextField<String> addressPostalCode = new TextField<String>("address.postalCode", Model.of(person2.getAddress().getPostalCode()));


        final Form<Person> formValidator = new Form<Person>("formValidator", Model.of(person2)) {


            @Override
            protected void onValidate() {
                this.getModelObject().setName(name.getValue());
                this.getModelObject().getAddress().setLineOne(addressLineOne.getValue());
                this.getModelObject().getAddress().setLineTwo(addressLineTwo.getValue());
                this.getModelObject().getAddress().setCity(addressCity.getValue());
                this.getModelObject().getAddress().setRegion(addressRegion.getValue());
                this.getModelObject().getAddress().setCountry(addressCountry.getValue());
                this.getModelObject().getAddress().setPostalCode(addressPostalCode.getValue());

                super.onValidate();    //To change body of overridden methods use File | Settings | File Templates.
            }

            @Override
            protected void onSubmit() {

                info("No Errors");
            }
        };
        formValidator.add(BeanFormValidator.create(Person.class));

        formValidator.add(new FeedbackPanel("feedback", new ContainerFeedbackMessageFilter(formValidator)));
        formValidator.add(
                name,
                addressLineOne,
                addressLineTwo,
                addressCity,
                addressRegion,
                addressCountry,
                addressPostalCode,
                new Button("submit"));

        this.add(formValidator);

    }
}
