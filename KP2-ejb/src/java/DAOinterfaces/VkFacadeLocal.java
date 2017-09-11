/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOinterfaces;

import java.util.List;
import javax.ejb.Local;
import models.Vk;

/**
 *
 * @author Григорий
 */
public interface VkFacadeLocal {

    void create(Vk vk);

    void edit(Vk vk);

    void remove(Vk vk);

    Vk find(Object id);

    List<Vk> findAll();

    List<Vk> findRange(int[] range);

    int count();
    
}
