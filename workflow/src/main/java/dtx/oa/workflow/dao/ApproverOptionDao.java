package dtx.oa.workflow.dao;

import dtx.oa.rbac.basic.BasicDao;
import dtx.oa.workflow.idao.IApproverOptionDao;
import dtx.oa.workflow.model.ApproverOptionModel;
import java.util.List;

public class ApproverOptionDao extends BasicDao implements IApproverOptionDao{

    @Override
    public boolean deleteByUserFormId(String userFormId) {
        List<ApproverOptionModel> list=queryByUserFormId(userFormId);
        for(ApproverOptionModel item:list){
            if(!delete(item))
                return false;
        }
        return true;
    }

    @Override
    public List<ApproverOptionModel> queryByUserFormId(String userFormId) {
        return executeQuery("FROM ApproverOptionModel approver WHERE approver.userFormId=?", new String[]{userFormId});
    }
    
}
