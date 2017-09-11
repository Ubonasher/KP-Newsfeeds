/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOinterfaces;

import java.util.List;
import javax.ejb.Local;
import models.Tw;

/**
 *
 * @author Григорий
 */

public interface TwFacadeLocal {

    void create(Tw tw);

    void edit(Tw tw);

    void remove(Tw tw);

    Tw find(Object id);

    List<Tw> findAll();

    List<Tw> findRange(int[] range);

    int count();
    
}
