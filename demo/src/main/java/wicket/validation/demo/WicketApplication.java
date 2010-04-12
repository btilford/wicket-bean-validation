package wicket.validation.demo;

import org.apache.wicket.protocol.http.WebApplication;
import wicket.validation.PropertyModelValidationListener;

/**
 * Application object for your web application. If you want to run this application without deploying, run the Start class.
 *
 * @see wicket-bean-validation.Start#main(String[])
 */
public class WicketApplication extends WebApplication {
    /**
     * Constructor
     */
    public WicketApplication() {
    }

    @Override
    protected void init() {
        super.init();
        this.addPreComponentOnBeforeRenderListener(new PropertyModelValidationListener());
    }

    /**
     * @see org.apache.wicket.Application#getHomePage()
     */
    public Class<HomePage> getHomePage() {
        return HomePage.class;
    }

}
