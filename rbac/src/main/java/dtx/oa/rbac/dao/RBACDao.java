/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.dao;

import dtx.oa.rbac.idao.IRBACDao;
import dtx.oa.rbac.idao.factory.IDaoFactory;
import dtx.oa.rbac.model.Node;
import dtx.oa.rbac.model.NodeTree;
import dtx.oa.rbac.model.RoleTree;
import dtx.oa.rbac.model.User;
import dtx.oa.util.StringUtil;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 *
 * @author datouxi
 */
public class RBACDao implements IRBACDao,Serializable {

    private User loginInfo;
    private NodeTree nodeTree;
    private RoleTree roleTree;
    
    @Override
    public boolean authenticate(String account, String password) {
        loginInfo=null;
        User user=IDaoFactory.iUserDao().getUserByAccount(account);
        if(user==null)
            return false;
        if(!user.getPassword().equals(StringUtil.getMD5String(password)))
            return false;
        loginInfo=user;
        nodeTree=new NodeTree(loginInfo, true);
        roleTree=new RoleTree(loginInfo, true);
        return true;
    }

    @Override
    public boolean accessDecision(String nodeId) {
        if(!isLogin())return false;
        if(IDaoFactory.iUserDao().isAdmin(loginInfo))return true;
        return nodeTree.find(nodeId) != null;
    }

    @Override
    public boolean accessDecision(Node node) {
        return accessDecision(node.getUuid());
    }

    @Override
    public NodeTree getAccessList() {
        return nodeTree;
    }

    @Override
    public boolean isLogin() {
        return loginInfo!=null;
    }

    @Override
    public User getLoginInfo() {
        return loginInfo;
    }

    @Override
    public void loginOut() {
        nodeTree=null;
        roleTree=null;
        loginInfo=null;
    }

    @Override
    public void updateLoginInfo(Timestamp loginTime, String loginIP) {
        if(!isLogin())return;
        loginInfo.setLoginTime(loginTime);
        loginInfo.setLoginIp(loginIP);
        IDaoFactory.iUserDao().updateLoginMessage(loginInfo);
    }

    @Override
    public RoleTree getRoleChilds() {
        return roleTree;
    }
    
}
