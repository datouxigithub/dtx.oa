/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.idao;

import dtx.oa.rbac.basic.IBasicDao;
import org.hibernate.Session;

/**
 *
 * @author datouxi
 */
public interface ITestDao extends IBasicDao{
    public Session getById(String formClassName,int id);
}
