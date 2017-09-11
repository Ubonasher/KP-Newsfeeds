package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Profiles;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-09-11T03:34:23")
@StaticMetamodel(Tw.class)
public class Tw_ { 

    public static volatile CollectionAttribute<Tw, Profiles> profilesCollection;
    public static volatile SingularAttribute<Tw, String> name;
    public static volatile SingularAttribute<Tw, String> screenName;
    public static volatile SingularAttribute<Tw, String> photo200;
    public static volatile SingularAttribute<Tw, String> accessToken;
    public static volatile SingularAttribute<Tw, String> accessTokenSecret;
    public static volatile SingularAttribute<Tw, Integer> idTw;

}