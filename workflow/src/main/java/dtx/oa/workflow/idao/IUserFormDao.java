package dtx.oa.workflow.idao;

import dtx.oa.rbac.basic.IBasicDao;
import dtx.oa.workflow.model.DefaultUserForm;

public interface IUserFormDao extends IBasicDao{
    
    public DefaultUserForm getById(String formClassName,int id);
    
}
