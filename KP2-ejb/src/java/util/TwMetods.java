package util;

import UtilInterfaces.ITwMetods;
import java.util.List;
import javax.ejb.Stateless;
import models.Tw;
import twitter4j.Paging;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author Григорий
 */
@Stateless
public class TwMetods implements ITwMetods{

    public List<Status> getNewsTw(Twitter twitter) throws TwitterException {

        Paging paging = new Paging(1,30);
        List<Status> Slist = twitter.getHomeTimeline(paging);

        return Slist;
    }

}
