package oauth;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.DatatypeConverter;

import beans.Core;
import models.Profiles;
import models.Tw;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.RequestToken;
import twitter4j.conf.ConfigurationBuilder;
import util.HttpURL;
import util.MyFullContext;

@Named(value = "twlogin")
@RequestScoped
public class TWLogin implements Serializable {

    private static final long serialVersionUID = 1L;
    private static Logger logger = Logger.getLogger(TWLogin.class.getName());
    private ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

    @Inject
    private Core core;
    @Inject
    private MyFullContext myFullContext;

    private String oauthConsumerKey;
    private String AccessTokenSecret;
    private String AccessToken;

    private String oauthSignatureMethod = "HMAC-SHA1";
    private String oauthToken;
    private String oauthVerifier;
    private String oauthVersion = "1.0";

    private String oauthConsumerSecret;

    private String accessTokenURL;
    private Profiles pr;

    @PostConstruct
    public void init() {
        logger.info("init()");
        oauthConsumerKey = "HtQ5J0bU5bcuBDonAjq52Y6OQ";
        oauthConsumerSecret = "1eLWq3mr9PiSH11tHRebkNNemCNtnSVtRsxEDheIDOFaS2tWdN";
        accessTokenURL = "https://api.twitter.com/oauth/access_token";
    }

    private String computeSignature(String baseString, String keyString) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKey secretKey = null;
        byte[] keyBytes = keyString.getBytes();
        secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");
        Mac mac = Mac.getInstance("HmacSHA1");
        mac.init(secretKey);
        byte[] text = baseString.getBytes();
        return DatatypeConverter.printBase64Binary(mac.doFinal(text));
    }

    public String authorize() throws TwitterException {

        logger.info("authorize() - start");
        try {
            ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
            configurationBuilder.setDebugEnabled(true)
                    .setOAuthConsumerKey(oauthConsumerKey)
                    .setOAuthConsumerSecret(oauthConsumerSecret)
                    .setOAuthAccessToken(null)
                    .setOAuthAccessTokenSecret(null);
            TwitterFactory tf = new TwitterFactory(configurationBuilder.build());
            Twitter twitter = tf.getInstance();
            RequestToken requestToken = twitter.getOAuthRequestToken("http://localhost:8080/KP2-war/login/twLogin.xhtml");

            String ret = requestToken.toString();
            logger.info("authorize() ret=" + ret);

            oauthToken = ret.substring(ret.indexOf("token=") + 7, ret.indexOf("tokenSecret=") - 3);
            logger.info("authorize() oauthToken=" + oauthToken);

            // Отправляем пользователя на авторизацию
            ec.redirect("https://api.twitter.com/oauth/authenticate?oauth_token=" + oauthToken);
            return null;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "authorize() error: ", ex);
        }

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "phase2() redirect error: ", ex);
        }
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return null;
    }

    public String phase2() {
        logger.info("phase2() oauthToken=" + oauthToken + " oauthVerifier=" + oauthVerifier);
        try {
            // Получаем access token
            String timestamp = new Long(System.currentTimeMillis() / 1000).toString();
            String nonce = UUID.randomUUID().toString().replaceAll("-", "");
            logger.info("phase2() nonce=" + nonce);

            StringBuilder sb = new StringBuilder();
            sb.append("oauth_consumer_key=");
            sb.append(oauthConsumerKey);
            sb.append("&oauth_nonce=");
            sb.append(nonce);
            sb.append("&oauth_signature_method=");
            sb.append(oauthSignatureMethod);
            sb.append("&oauth_timestamp=");
            sb.append(timestamp);
            sb.append("&oauth_token=");
            sb.append(oauthToken);
            sb.append("&oauth_version=");
            sb.append(oauthVersion);

            String signature = percentEncode(computeSignature("POST&" + percentEncode(accessTokenURL) + "&" + percentEncode(sb.toString()), oauthConsumerSecret));
            logger.info("phase2() signature=" + signature);

            sb = new StringBuilder();
            sb.append("OAuth ");
            sb.append("oauth_consumer_key=\"");
            sb.append(oauthConsumerKey);
            sb.append("\",");
            sb.append("oauth_nonce=\"");
            sb.append(nonce);
            sb.append("\",");
            sb.append("oauth_signature=\"");
            sb.append(signature);
            sb.append("\",");
            sb.append("oauth_signature_method=\"");
            sb.append(oauthSignatureMethod);
            sb.append("\",");
            sb.append("oauth_timestamp=\"");
            sb.append(timestamp);
            sb.append("\",");
            sb.append("oauth_token=\"");
            sb.append(oauthToken);
            sb.append("\",");
            sb.append("oauth_version=\"");
            sb.append(oauthVersion + "\"");

            logger.info("authorize() Authorization=" + sb.toString());
            Properties reqProps = new Properties();
            reqProps.put("Authorization", sb.toString());
            Properties props = new Properties();
            props.put("oauth_verifier", oauthVerifier);
            String ret = HttpURL.httpsPost(accessTokenURL, props, reqProps, "UTF-8");
            Properties personData = parse(ret);

            setAccessToken(personData.getProperty("oauth_token"));
            setAccessTokenSecret(personData.getProperty("oauth_token_secret"));

            ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
            configurationBuilder.setDebugEnabled(true)
                    .setOAuthConsumerKey(oauthConsumerKey)
                    .setOAuthConsumerSecret(oauthConsumerSecret)
                    .setOAuthAccessToken(AccessToken)
                    .setOAuthAccessTokenSecret(AccessTokenSecret);
            TwitterFactory tf = new TwitterFactory(configurationBuilder.build());
            Twitter twitter = tf.getInstance();
            User user = twitter.verifyCredentials();

            core.setFirstName(user.getName());
            core.setLastName(user.getScreenName());
            core.setUrlAvatar(user.getProfileImageURLHttps().replace("_normal", "_400x400"));
            core.setValidatedId("http://twitter.com/" + user.getId());
            core.setAccessToken(AccessToken);
            core.setAccessTokenSecret(AccessTokenSecret);

            String retUrl = core.entryFromOpenID();
            ec.redirect(myFullContext.getBasedUrl().concat(retUrl));
            return null;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "phase2() error: ", ex);
        }

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "phase2() redirect error: ", ex);
        }
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return null;
    }

    public Properties parse(String str) {
        Properties props = new Properties();
        String[] arr1 = str.split("&");
        for (int i = 0; i < arr1.length; i++) {
            String[] arr2 = arr1[i].split("=");
            props.put(arr2[0], arr2[1]);
        }
        return props;
    }

    public static final String ENCODING = "UTF-8";

    public static String percentEncode(String s) {
        if (s == null) {
            return "";
        }
        try {
            return URLEncoder.encode(s, ENCODING)
                    .replace("+", "%20").replace("*", "%2A")
                    .replace("%7E", "~");
        } catch (UnsupportedEncodingException ex) {
            logger.log(Level.SEVERE, "percentEncode() error: ", ex);
        }
        return null;
    }

    public String hash_hmac_SHA1(String skey, String data) {
        SecretKey secretKey = null;
        byte[] keyBytes = skey.getBytes();
        secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA1");
            mac.init(secretKey);
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "hash_hmac_SHA1 error: ", ex);
        }

        byte[] text = data.getBytes();
        byte[] mdbytes = mac.doFinal(text);

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

    public String addacc(Profiles p) throws TwitterException {
        pr = p;
        logger.info("authorize() - start");
        try {
            ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
            configurationBuilder.setDebugEnabled(true)
                    .setOAuthConsumerKey(oauthConsumerKey)
                    .setOAuthConsumerSecret(oauthConsumerSecret)
                    .setOAuthAccessToken(null)
                    .setOAuthAccessTokenSecret(null);
            TwitterFactory tf = new TwitterFactory(configurationBuilder.build());
            Twitter twitter = tf.getInstance();
            RequestToken requestToken = twitter.getOAuthRequestToken("http://localhost:8080/KP2-war/login/twAdd.xhtml");

            String ret = requestToken.toString();
            logger.info("authorize() ret=" + ret);

            oauthToken = ret.substring(ret.indexOf("token=") + 7, ret.indexOf("tokenSecret=") - 3);
            logger.info("authorize() oauthToken=" + oauthToken);
            

            // Отправляем пользователя на авторизацию
            ec.redirect("https://api.twitter.com/oauth/authenticate?oauth_token=" + oauthToken);
            return null;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "authorize() error: ", ex);
        }

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "phase2() redirect error: ", ex);
        }
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return null;
    }

    public String phase2add() {
        logger.info("phase2() oauthToken=" + oauthToken + " oauthVerifier=" + oauthVerifier);
        try {
            // Получаем access token
            String timestamp = new Long(System.currentTimeMillis() / 1000).toString();
            String nonce = UUID.randomUUID().toString().replaceAll("-", "");
            logger.info("phase2() nonce=" + nonce);

            StringBuilder sb = new StringBuilder();
            sb.append("oauth_consumer_key=");
            sb.append(oauthConsumerKey);
            sb.append("&oauth_nonce=");
            sb.append(nonce);
            sb.append("&oauth_signature_method=");
            sb.append(oauthSignatureMethod);
            sb.append("&oauth_timestamp=");
            sb.append(timestamp);
            sb.append("&oauth_token=");
            sb.append(oauthToken);
            sb.append("&oauth_version=");
            sb.append(oauthVersion);

            String signature = percentEncode(computeSignature("POST&" + percentEncode(accessTokenURL) + "&" + percentEncode(sb.toString()), oauthConsumerSecret));
            logger.info("phase2() signature=" + signature);

            sb = new StringBuilder();
            sb.append("OAuth ");
            sb.append("oauth_consumer_key=\"");
            sb.append(oauthConsumerKey);
            sb.append("\",");
            sb.append("oauth_nonce=\"");
            sb.append(nonce);
            sb.append("\",");
            sb.append("oauth_signature=\"");
            sb.append(signature);
            sb.append("\",");
            sb.append("oauth_signature_method=\"");
            sb.append(oauthSignatureMethod);
            sb.append("\",");
            sb.append("oauth_timestamp=\"");
            sb.append(timestamp);
            sb.append("\",");
            sb.append("oauth_token=\"");
            sb.append(oauthToken);
            sb.append("\",");
            sb.append("oauth_version=\"");
            sb.append(oauthVersion + "\"");

            logger.info("authorize() Authorization=" + sb.toString());
            Properties reqProps = new Properties();
            reqProps.put("Authorization", sb.toString());
            Properties props = new Properties();
            props.put("oauth_verifier", oauthVerifier);
            String ret = HttpURL.httpsPost(accessTokenURL, props, reqProps, "UTF-8");
            Properties personData = parse(ret);

            setAccessToken(personData.getProperty("oauth_token"));
            setAccessTokenSecret(personData.getProperty("oauth_token_secret"));

            ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
            configurationBuilder.setDebugEnabled(true)
                    .setOAuthConsumerKey(oauthConsumerKey)
                    .setOAuthConsumerSecret(oauthConsumerSecret)
                    .setOAuthAccessToken(AccessToken)
                    .setOAuthAccessTokenSecret(AccessTokenSecret);
            TwitterFactory tf = new TwitterFactory(configurationBuilder.build());
            Twitter twitter = tf.getInstance();
            User user = twitter.verifyCredentials();
            Profiles p = new Profiles();// = core.getProfile();
            p.setIdTw(new Tw((int) user.getId(), AccessToken, user.getName(), user.getScreenName(), user.getProfileImageURLHttps().replace("_normal", "_400x400"), AccessTokenSecret));
            core.updateBDtw(p);
            

            ec.redirect(myFullContext.getBasedUrl().concat("/welcome.xhtml"));
            return null;
        } catch (Exception ex) {
            logger.log(Level.SEVERE, "phase2() error: ", ex);
        }

        try {
            FacesContext.getCurrentInstance().getExternalContext().redirect("/");
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "phase2() redirect error: ", ex);
        }
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        return null;
    }

    public String getOauthConsumerKey() {
        return oauthConsumerKey;
    }

    public void setOauthConsumerKey(String oauthConsumerKey) {
        this.oauthConsumerKey = oauthConsumerKey;
    }

    public String getOauthSignatureMethod() {
        return oauthSignatureMethod;
    }

    public String getOauthToken() {
        return oauthToken;
    }

    public void setOauthToken(String oauthToken) {
        this.oauthToken = oauthToken;
    }

    public String getOauthVersion() {
        return oauthVersion;
    }

    public String getOauthVerifier() {
        return oauthVerifier;
    }

    public void setOauthVerifier(String oauthVerifier) {
        this.oauthVerifier = oauthVerifier;
    }

    /**
     * @return the AccessTokenSecret
     */
    public String getAccessTokenSecret() {
        return AccessTokenSecret;
    }

    /**
     * @param AccessTokenSecret the AccessTokenSecret to set
     */
    public void setAccessTokenSecret(String AccessTokenSecret) {
        this.AccessTokenSecret = AccessTokenSecret;
    }

    /**
     * @return the AccessToken
     */
    public String getAccessToken() {
        return AccessToken;
    }

    /**
     * @param AccessToken the AccessToken to set
     */
    public void setAccessToken(String AccessToken) {
        this.AccessToken = AccessToken;
    }

    /**
     * @return the pr
     */
    public Profiles getPr() {
        return pr;
    }

    /**
     * @param pr the pr to set
     */
    public void setPr(Profiles pr) {
        this.pr = pr;
    }
}
