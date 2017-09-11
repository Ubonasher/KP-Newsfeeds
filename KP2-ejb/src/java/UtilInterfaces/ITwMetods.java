/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UtilInterfaces;

import java.util.List;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 *
 * @author Григорий
 */
public interface ITwMetods {
    public List<Status> getNewsTw(Twitter twitter) throws TwitterException;
}
