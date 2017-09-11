/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAOinterfaces;

import java.util.List;
import models.Profiles;

/**
 *
 * @author Григорий
 */
public interface ProfilesFacadeLocal {

    void create(Profiles profiles);

    void edit(Profiles profiles);

    Profiles GetByIdVk(String vid);

    Profiles GetByIdTw(String tid);

    void remove(Profiles profiles);

    Profiles find(Object id);

    List<Profiles> findAll();

    List<Profiles> findRange(int[] range);

    int count();
}
