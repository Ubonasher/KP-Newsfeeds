/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAOinterfaces.ProfilesFacadeLocal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import models.Profiles;
import models.Vk;

/**
 *
 * @author Григорий
 */
@Stateless
public class ProfilesFacade extends AbstractFacade<Profiles> implements ProfilesFacadeLocal {

    @PostConstruct
    public void connect() {

    }

    @PreDestroy
    public void disconnect() {

    }
    @PersistenceContext(unitName = "KP2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProfilesFacade() {
        super(Profiles.class);
    }

    @Override
    public Profiles GetByIdVk(String vid) {
        int Temp = Integer.valueOf(vid);
        List<Profiles> p = (List<Profiles>) em.createNamedQuery("Profiles.findByIdVk").setParameter("idVk", Temp).getResultList();
        if (p.isEmpty()) {
            p.add(new Profiles(-1));
        }
        return p.get(0);
    }
    
     @Override
    public Profiles GetByIdTw(String tid) {
        int Temp = Integer.valueOf(tid);
        List<Profiles> p = (List<Profiles>) em.createNamedQuery("Profiles.findByIdTw").setParameter("idTw", Temp).getResultList();
        if (p.isEmpty()) {
            p.add(new Profiles(-1));
        }
        return p.get(0);
    }
}
