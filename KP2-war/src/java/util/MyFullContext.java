package util;

import java.io.Serializable;
import java.util.logging.Logger;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.context.FacesContext;
import javax.inject.Named;

@Named
@ApplicationScoped
public class MyFullContext implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final Logger logger = Logger.getLogger(MyFullContext.class.getName());
    private final String basedUrl;

    public MyFullContext() {
        logger.info("Constructor start " + this.toString());
        basedUrl = FacesContext.getCurrentInstance().getExternalContext().getRequestScheme()
                .concat("://")
                .concat(FacesContext.getCurrentInstance().getExternalContext().getRequestServerName())
                .concat((FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort() == 80) ? "" : ":".concat(new Integer(FacesContext.getCurrentInstance().getExternalContext().getRequestServerPort()).toString()))
                .concat(FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath());
    }

    public String getBasedUrl() {
        return basedUrl;
    }

}
