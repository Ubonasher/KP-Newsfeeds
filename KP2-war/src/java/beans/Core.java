package beans;

import DAOinterfaces.ProfilesFacadeLocal;
import DAOinterfaces.SnetworkskeysFacadeLocal;
import DAOinterfaces.TwFacadeLocal;
import DAOinterfaces.VkFacadeLocal;
import UtilInterfaces.ITwMetods;
import UtilInterfaces.IVkMetods;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletException;
import models.Account;
import models.Group;
import models.NewsFeed;
import models.NewsFeedVK;
import models.Profiles;
import models.Tw;
import models.Vk;
import models.ollnfs;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;
import util.TwMetods;

@Named
@SessionScoped
public class Core implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(Core.class.getName());

    private Profiles profile;
    private String validatedId;
    private boolean loggedIn;
    private String firstName;
    private String lastName;
    private String urlAvatar;
    private String access_token;
    private String AccessToken;
    private String AccessTokenSecret;
    private List<Status> newsTw;
    private int tempindex;
    private int tempindex1 = 0;
    private int lastidTW;
    private int LastidVK;
    private ollnfs[] olns;

    private NewsFeed newsVk;

    @EJB
    ProfilesFacadeLocal PFS;
    @EJB
    TwFacadeLocal TwC;
    @EJB
    VkFacadeLocal VkC;
    @EJB
    SnetworkskeysFacadeLocal SNK;
    @EJB
    IVkMetods VkM;
    @EJB
    ITwMetods TwM;

    @SuppressWarnings("LoggerStringConcat")
    public String entryFromOpenID() {
        logger.info("entryFromOpenID validatedOpenId=" + validatedId);
        if (validatedId == null) {
            return "/index.xhtml";
        } else {
            loggedIn = true;
            if ("v".equals(validatedId.substring(7, 8))) {
                String IdVk = validatedId.substring(16);
                Profiles p = PFS.GetByIdVk(IdVk);
                if (p.getId() == -1) {
                    Vk vk = new Vk(Integer.valueOf(IdVk), access_token, firstName, lastName, urlAvatar);
                    VkC.create(vk);
                    PFS.create(new Profiles(null, vk));
                } else {
                    Vk vk = p.getIdVk();
                    vk.setAccessToken(access_token);
                    vk.setFirstName(firstName);
                    vk.setLastName(lastName);
                    vk.setPhoto200(urlAvatar);
                    VkC.edit(vk);
                }
                setProfile(p);
            } else {
                String IdTw = validatedId.substring(19);
                Profiles p = PFS.GetByIdTw(IdTw);
                if (p.getId() == -1) {
                    Tw tw = new Tw(Integer.valueOf(IdTw), AccessToken, firstName, lastName, urlAvatar, AccessTokenSecret);
                    TwC.create(tw);
                    PFS.create(new Profiles(null, tw));
                } else {
                    Tw tw = p.getIdTw();
                    tw.setAccessToken(AccessToken);
                    tw.setAccessTokenSecret(AccessTokenSecret);
                    tw.setName(firstName);
                    tw.setScreenName(lastName);
                    tw.setPhoto200(urlAvatar);
                    TwC.edit(tw);
                }
                setProfile(p);
                p.getIdTw().getAccessToken();
                p.getIdTw().getAccessTokenSecret();
            }
            return "/welcome.xhtml";
        }
    }

    public String addacc() {
        return "addacc";
    }

    public void updateBDtw(Profiles p) {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();

        if (TwC.find(p.getIdTw().getIdTw()) == null) {
            TwC.create(p.getIdTw());
        } else {
            PFS.remove(PFS.GetByIdTw(String.valueOf(p.getIdTw().getIdTw())));
            TwC.edit(p.getIdTw());
        }

        profile.setIdTw(p.getIdTw());
        PFS.edit(profile);
    }

    public void updateBDvk(Profiles p) {
        if (VkC.find(p.getIdVk().getIdVk()) == null) {
            VkC.create(p.getIdVk());
        } else {
            PFS.remove(PFS.GetByIdTw(String.valueOf(p.getIdVk().getIdVk())));
            VkC.edit(p.getIdVk());
        }
        profile.setIdVk(p.getIdVk());
        PFS.edit(profile);
    }

    public void out() throws ServletException, IOException {
        FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
        FacesContext.getCurrentInstance().getExternalContext().redirect("index.xhtml");
    }

    public void fiscal() throws ServletException, TwitterException {
        if (!loggedIn) {
            try {
                out();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "fiscal(): redirect error", e);
            }
        } else {
            if (profile.getIdTw() != null) {
                Tw tw = profile.getIdTw();
                String s1 = SNK.find(1).getTwOauthConsumerKey();
                String s2 = SNK.find(1).getTwOauthConsumerSecret();

                ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
                configurationBuilder.setDebugEnabled(true)
                        .setOAuthConsumerKey(s1)
                        .setOAuthConsumerSecret(s2)
                        .setOAuthAccessToken(tw.getAccessToken())
                        .setOAuthAccessTokenSecret(tw.getAccessTokenSecret());

                TwitterFactory tf = new TwitterFactory(configurationBuilder.build());
                Twitter twitter = tf.getInstance();
                newsTw = TwM.getNewsTw(twitter);
            }
            if (profile.getIdVk() != null) {
                newsVk = VkM.getNewsVk(profile.getIdVk());
            }

            if (profile.getIdTw() != null && profile.getIdVk() != null) {
                long l1 = newsTw.get((int) newsTw.stream().count() - 1).getCreatedAt().getTime();
                long l2 = Long.valueOf(newsVk.getNfslist()[newsVk.getNfslist().length - 1].getDate() + "000");
                if (l1 > l2) {
                    lastidTW = (int) newsTw.stream().count() - 1;
                    LastidVK = 0;
                    int iter = 0;
                    for (NewsFeedVK nvk : newsVk.getNfslist()) {
                        if (newsTw.get(lastidTW).getCreatedAt().getTime() < Long.valueOf(nvk.getDate() + "000")) {
                            LastidVK = iter;
                            iter++;
                        } else {
                            break;
                        }
                    }
                } else {
                    lastidTW = 0;//(int) newsTw.stream().count();
                    LastidVK = newsVk.getNfslist().length - 1;// 0;
                    int iter = 0;
                    for (Status ntw : newsTw) {
                        if (Long.valueOf(newsVk.getNfslist()[LastidVK].getDate() + "000") < ntw.getCreatedAt().getTime()) {
                            lastidTW = iter;
                            iter++;
                        } else {
                            break;
                        }
                    }
                }
                olns = new ollnfs[LastidVK + lastidTW];
                if (LastidVK > lastidTW) {
                    int NowidTW = 0;
                    int iter1 = 0;
                    for (int iter = 0; iter <= LastidVK; iter++) {
                        if (newsTw.get(NowidTW).getCreatedAt().getTime() < Long.valueOf(newsVk.getNfslist()[iter1].getDate() + "000")) {
                            olns[iter] = new ollnfs(iter, newsVk.getNfslist()[iter1]);
                            iter1++;
                        } else {
                            if (NowidTW < lastidTW) {
                                olns[iter] = new ollnfs(iter, newsTw.get(NowidTW));
                                NowidTW++;
                            } else {
                                olns[iter] = new ollnfs(iter, newsVk.getNfslist()[iter1]);
                                iter1++;
                            }
                        }
                    }
                } else {

                    int NowidVK = 0;
                    int iter1 = 0;
                    for (int iter = 0; iter <= lastidTW; iter++) {
                        if (Long.valueOf(newsVk.getNfslist()[NowidVK].getDate() + "000") < newsTw.get(iter1).getCreatedAt().getTime()) {
                            olns[iter] = new ollnfs(iter, newsTw.get(iter1));
                            iter1++;
                        } else {
                            if (NowidVK < LastidVK) {
                                olns[iter] = new ollnfs(iter, newsVk.getNfslist()[NowidVK]);
                                NowidVK++;
                            } else {
                                olns[iter] = new ollnfs(iter, newsTw.get(iter1));
                                iter1++;
                            }
                        }
                    }
                }
            }
            int i = 0;
        }
    }

    public String getValidatedId() {
        return validatedId;
    }

    public void setValidatedId(String validatedId) {
        this.validatedId = validatedId;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public String getAccessToken() {
        return AccessToken;
    }

    public void setAccessToken(String AccessToken) {
        this.AccessToken = AccessToken;
    }

    public String getAccessTokenSecret() {
        return AccessTokenSecret;
    }

    public void setAccessTokenSecret(String AccessTokenSecret) {
        this.AccessTokenSecret = AccessTokenSecret;
    }

    public List<Status> getNewsTw() {
        return newsTw;
    }

    public void setNewsTw(List<Status> newsTw) {
        this.newsTw = newsTw;
    }

    public Profiles getProfile() {
        return profile;
    }

    public void setProfile(Profiles profile) {
        this.profile = profile;
    }

    public String[] getTextStatus(String tx) {
        String[] split = tx.split("<br>");
        String[] split1 = new String[6];
        if (split.length > 5) {
            for (int i = 0; i < 5; i++) {
                split1[i] = split[i];
            }
            split1[5] = "nextn";
            return split1;
        }
        return split;
    }

    public String[] getExtendedTextStatus(String tx) {
        String[] split = tx.split("<br>");
        return split;
    }

    public String getFN(String tx) {

        String retr = "";
        try {
            int yid = Integer.valueOf(tx);
            if (yid < 0) {
                for (Group p : newsVk.getGroups()) {
                    if (p.getGid() == yid * -1) {
                        retr = p.getName();
                    }
                }
            } else {
                for (Account a : newsVk.getAccounts()) {
                    if (a.getUid() == yid) {
                        retr = a.getFirst_name();
                    }
                }
            }
        } catch (Exception e) {
        }
        return retr;
    }

    public String getLN(String tx) {
        String retr = "";
        try {
            int yid = Integer.valueOf(tx);
            if (yid < 0) {
                for (Group p : newsVk.getGroups()) {
                    if (p.getGid() == yid * -1) {
                        retr = p.getScreen_name();
                    }
                }
            } else {
                for (Account a : newsVk.getAccounts()) {
                    if (a.getUid() == yid) {
                        retr = a.getLast_name();
                    }
                }
            }
        } catch (Exception e) {
        }
        return retr;
    }

    public String getAvatar(String tx) {
        String retr = "";
        try {
            int yid = Integer.valueOf(tx);
            if (yid < 0) {
                for (Group p : newsVk.getGroups()) {
                    if (p.getGid() == yid * -1) {
                        retr = p.getPhoto();
                    }
                }
            } else {
                for (Account a : newsVk.getAccounts()) {
                    if (a.getUid() == yid) {
                        retr = a.getPhoto();
                    }
                }
            }
        } catch (Exception e) {
        }
        return retr;
    }

    /**
     * @return the tempindex
     */
    public int getTempindex() {
        return tempindex;
    }

    /**
     * @param tempindex the tempindex to set
     */
    public void setTempindex(int tempindex) {
        this.tempindex = tempindex + 1;
    }

    /**
     * @return the newsVk
     */
    public NewsFeed getNewsVk() {
        return newsVk;
    }

    /**
     * @param newsVk the newsVk to set
     */
    public void setNewsVk(NewsFeed newsVk) {
        this.newsVk = newsVk;
    }

    /**
     * @return the tempindex1
     */
    public int getTempindex1() {
        return tempindex1;
    }

    /**
     * @param tempindex1 the tempindex1 to set
     */
    public void setTempindex1(int tempindex1) {
        this.tempindex1 = tempindex1;
    }

    /**
     * @return the LastidVK
     */
    public int getLastidVK() {
        return LastidVK;
    }

    /**
     * @param LastidVK the LastidVK to set
     */
    public void setLastidVK(int LastidVK) {
        this.LastidVK = LastidVK;
    }

    /**
     * @return the lastidTW
     */
    public int getLastidTW() {
        return lastidTW;
    }

    /**
     * @param lastidTW the lastidTW to set
     */
    public void setLastidTW(int lastidTW) {
        this.lastidTW = lastidTW;
    }

    /**
     * @return the olns
     */
    public ollnfs[] getOlns() {
        return olns;
    }

    /**
     * @param olns the olns to set
     */
    public void setOlns(ollnfs[] olns) {
        this.olns = olns;
    }

}
