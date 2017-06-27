/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.process.impl;

import dtx.oa.workflow.model.CustomFormInfoModel;

/**
 *
 * @author datouxi
 */
public interface ISpecialProcess extends IProcess{
    
    CustomFormInfoModel getCustomFormInfoModel();
    
}
