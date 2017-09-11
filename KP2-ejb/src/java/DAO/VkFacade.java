/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import DAOinterfaces.VkFacadeLocal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import models.Vk;

/**
 *
 * @author Григорий
 */
@Stateless
public class VkFacade extends AbstractFacade<Vk> implements VkFacadeLocal {

    @PersistenceContext(unitName = "KP2-ejbPU")
    private EntityManager em;
    

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public VkFacade() {
        super(Vk.class);
    }
    
}
