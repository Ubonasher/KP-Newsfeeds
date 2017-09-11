/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAOinterfaces.TwFacadeLocal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import models.Tw;

/**
 *
 * @author Григорий
 */
@Stateless
public class TwFacade extends AbstractFacade<Tw> implements TwFacadeLocal {

    @PersistenceContext(unitName = "KP2-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public TwFacade() {
        super(Tw.class);
    }
    
}
