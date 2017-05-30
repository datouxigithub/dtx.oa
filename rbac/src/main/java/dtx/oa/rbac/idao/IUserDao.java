/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.idao;

import dtx.oa.rbac.basic.IBasicDao;
import dtx.oa.rbac.model.User;
import java.util.List;

/**
 *
 * @author datouxi
 */
public interface IUserDao extends IBasicDao{
    public final static String ADMININAME="admin";
    
    public User getUserById(String userId);

    public User getUserByAccount(String account);

    public List<User> getUsersByStatus(boolean status);

    public String addUser(User user);

    public boolean deleteUser(User user);
    
    public boolean deleteUser(User user,boolean isDeleteCascade);

    public boolean updateUser(User user);

    public boolean updateLoginMessage(User user);

    public boolean isAdmin(User user);
}
