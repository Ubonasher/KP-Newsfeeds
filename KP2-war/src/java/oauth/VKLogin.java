package oauth;

import DAOinterfaces.SnetworkskeysFacadeLocal;
import beans.Core;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import java.util.regex.Pattern;
import java.util.regex.Matcher;
import models.Profiles;
import models.Vk;

import util.HttpURL;
import util.MyFullContext;
import util.XStreamFactory;

@Named(value = "vklogin")
@RequestScoped
public class VKLogin implements Serializable {

    private static final long serialVersionUID = 2L;
    private static Logger logger = Logger.getLogger(VKLogin.class.getName());
    private ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();
    
    @Inject
    private Core core;
    @Inject
    private MyFullContext myFullContext;

    private String code;
    private String access_token;
    private String expires_in;
    private String user_id;
    private String error;
    private String error_reason;
    private String error_description;
    private String parmClientId;
    private String parmScope;
    private String parmClientSecret;
    private String parmRedirectURL;
    private String getAccessTokenURL = "http://oauth.vk.com/authorize?client_id=6083311&scope=wall,friends,offline&response_type=token";

    

    @PostConstruct
    public void init() {
        parmClientId = "6083311";
        parmClientSecret = "M9s4aka9MuBafLHKSIUO";
        parmScope = "wall,friends,offline";
        parmRedirectURL = "http://localhost:8080/KP2-war/login/vkLogin.xhtml";
    }

    public void authorize() {
        try {
            access_token = access_token.replace("https://oauth.vk.com/blank.html#access_token=", "").substring(0, 85);

            String ret = HttpURL.httpsGet("https://api.vk.com/method/users.get?"
                    + "fields=uid,first_name,last_name,photo_200"
                    + "&access_token=" + access_token);

            class Info {

                String uid;
                String first_name;
                String last_name;
                String photo_200;
            }

            ret = ret.replace("{\"response\":[", "").replace("]}", "");
            Info inf = (Info) XStreamFactory.get(Info.class, ret);

            core.setFirstName(inf.first_name);
            core.setLastName(inf.last_name);
            core.setUrlAvatar(inf.photo_200);
            core.setValidatedId("http://vk.com/id".concat(inf.uid));
            core.setAccess_token(access_token);

            String retUrl = core.entryFromOpenID();

            ec.redirect(myFullContext.getBasedUrl().concat(retUrl));

        } catch (IOException e) {
            logger.severe("VK Login error:" + e);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(myFullContext.getBasedUrl().concat("/index.xhtml"));
            } catch (IOException ex) {
            }
        }
    }

    public void authorizeadd() {
        try {
            access_token = access_token.replace("https://oauth.vk.com/blank.html#access_token=", "").substring(0, 85);

            String ret = HttpURL.httpsGet("https://api.vk.com/method/users.get?"
                    + "fields=uid,first_name,last_name,photo_200"
                    + "&access_token=" + access_token);

            class Info {

                String uid;
                String first_name;
                String last_name;
                String photo_200;
            }

            ret = ret.replace("{\"response\":[", "").replace("]}", "");
            Info inf = (Info) XStreamFactory.get(Info.class, ret);

            Profiles profile = core.getProfile();
            profile.setIdVk(new Vk(Integer.valueOf(inf.uid), access_token, inf.first_name, inf.last_name, inf.photo_200));
            core.updateBDvk(profile);

            ec.redirect(myFullContext.getBasedUrl().concat("/welcome.xhtml"));

        } catch (IOException e) {
            logger.severe("VK Login error:" + e);
            try {
                FacesContext.getCurrentInstance().getExternalContext().redirect(myFullContext.getBasedUrl().concat("/index.xhtml"));
            } catch (IOException ex) {
            }
        }
    }

    public String getGetAccessTokenURL() {
        return getAccessTokenURL;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getError_description() {
        return error_description;
    }

    public void setError_description(String error_description) {
        this.error_description = error_description;
    }

    public String getError_reason() {
        return error_reason;
    }

    public void setError_reason(String error_reason) {
        this.error_reason = error_reason;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getExpires_in() {
        return expires_in;
    }

    public void setExpires_in(String expires_in) {
        this.expires_in = expires_in;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public boolean regex(String re, String s) {
        Pattern pattern = Pattern.compile(re);
        Matcher matcher = pattern.matcher(s);

        return matcher.matches();
    }

    public void setGetAccessTokenURL(String getAccessTokenURL) {
        this.getAccessTokenURL = getAccessTokenURL;
    }
}
