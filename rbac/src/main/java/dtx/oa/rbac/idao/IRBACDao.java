/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.idao;

import dtx.oa.rbac.model.Node;
import dtx.oa.rbac.model.NodeTree;
import dtx.oa.rbac.model.RoleTree;
import dtx.oa.rbac.model.User;
import java.sql.Timestamp;

/**
 *
 * @author datouxi
 */
public interface IRBACDao {
    //验证用户帐号密码，成功返回true,否则返回false
    public boolean authenticate(String account,String password);

    //验证是否有权限操作主键为nodeId的Node
    public boolean accessDecision(String nodeId);

    public boolean accessDecision(Node node);

    //根据登录信息返回权限列表
    public NodeTree getAccessList();

    //检查是否已经登录
    public boolean isLogin();

    public User getLoginInfo();

    //注销用户
    public void loginOut();

    public void updateLoginInfo(Timestamp loginTime,String loginIP);
        
    public RoleTree getRoleChilds();
}
