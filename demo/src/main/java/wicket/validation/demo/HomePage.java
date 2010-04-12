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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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


    }
}
