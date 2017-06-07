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
public interface IBasicDao {
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
    public Serializable add(Object obj,boolean isRollback);
    //批量添加
    public void add(List objs);
    public void add(List objs,boolean isRollback);
    //更新对象
    public boolean update(Object obj);
    public boolean update(Object obj,boolean isRollback);
    //更新\删除对象
    public int update(String hql,Object[] args);
    public int update(String hql,Object[] args,boolean isRollback);
    //删除对象
    public boolean delete(Object obj);
    public boolean delete(Object obj,boolean isRollback);
}
