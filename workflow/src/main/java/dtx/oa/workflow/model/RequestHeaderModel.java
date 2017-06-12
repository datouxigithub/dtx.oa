/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.workflow.model;

import java.io.Serializable;

/**
 *
 * @author gg
 */
public class RequestHeaderModel implements Serializable{
    private String referer;

    public String getReferer() {
        return referer;
    }

    public void setReferer(String referer) {
        this.referer = referer;
    }
}
