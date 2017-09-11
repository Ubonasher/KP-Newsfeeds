package models;

import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import models.Profiles;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2017-09-11T03:34:23")
@StaticMetamodel(Vk.class)
public class Vk_ { 

    public static volatile SingularAttribute<Vk, Integer> idVk;
    public static volatile SingularAttribute<Vk, String> firstName;
    public static volatile SingularAttribute<Vk, String> lastName;
    public static volatile CollectionAttribute<Vk, Profiles> profilesCollection;
    public static volatile SingularAttribute<Vk, String> photo200;
    public static volatile SingularAttribute<Vk, String> accessToken;

}