/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

/**
 *
 * @author Григорий
 */
public class NewsFeed {

    private NewsFeedVK[] nfslist;
    private Group[] groups;
    private Account[] accounts;
    private String NewsFeedVKnew_from;

    /**
     * @return the groups
     */
    public Group[] getGroups() {
        return groups;
    }

    /**
     * @param groups the groups to set
     */
    public void setGroups(Group[] groups) {
        this.groups = groups;
    }

    /**
     * @return the accounts
     */
    public Account[] getAccounts() {
        return accounts;
    }

    /**
     * @param accounts the accounts to set
     */
    public void setAccounts(Account[] accounts) {
        this.accounts = accounts;
    }

    /**
     * @return the nfslist
     */
    public NewsFeedVK[] getNfslist() {
        return nfslist;
    }

    /**
     * @param nfslist the nfslist to set
     */
    public void setNfslist(NewsFeedVK[] nfslist) {
        this.nfslist = nfslist;
    }

    /**
     * @return the NewsFeedVKnew_from
     */
    public String getNewsFeedVKnew_from() {
        return NewsFeedVKnew_from;
    }

    /**
     * @param NewsFeedVKnew_from the NewsFeedVKnew_from to set
     */
    public void setNewsFeedVKnew_from(String NewsFeedVKnew_from) {
        this.NewsFeedVKnew_from = NewsFeedVKnew_from;
    }

}
