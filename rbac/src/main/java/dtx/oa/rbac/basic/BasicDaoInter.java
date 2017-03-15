/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac.basic;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author datouxi
 */
public interface BasicDaoInter {
    //根据ID进行查找
    public Object findById(Class clazz,Serializable id);
    //进行查询
    public List executeQuery(String hql,Object[] args);
    //查询一个对象
    public Object uniqueQuery(String hql,Object[] args);
    //进行分页查询
    public List executeQuery(String hql,Object[] args,int pageNow,int pageSize);
    //添加对象
    public Serializable add(Object obj);
    //更新对象
    public boolean update(Object obj);
    //更新\删除对象
    public int update(String hql,Object[] args);
}
