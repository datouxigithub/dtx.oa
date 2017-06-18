package dtx.oa.workflow.idao;

import dtx.oa.workflow.model.DefaultUserForm;
import java.io.Serializable;

public interface IUserFormDao{
    
    public DefaultUserForm getById(String formClassName,int id);
    
    public Serializable add(DefaultUserForm userForm);
    
}
