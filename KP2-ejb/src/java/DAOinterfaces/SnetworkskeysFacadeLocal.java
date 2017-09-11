/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOinterfaces;

import java.util.List;
import javax.ejb.Local;
import models.Snetworkskeys;

/**
 *
 * @author Григорий
 */

public interface SnetworkskeysFacadeLocal {

    void create(Snetworkskeys snetworkskeys);

    void edit(Snetworkskeys snetworkskeys);

    void remove(Snetworkskeys snetworkskeys);

    Snetworkskeys find(Object id);

    List<Snetworkskeys> findAll();

    List<Snetworkskeys> findRange(int[] range);

    int count();
    
}
