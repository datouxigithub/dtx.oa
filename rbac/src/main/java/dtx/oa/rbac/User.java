/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dtx.oa.rbac;

/**
 *
 * @author datouxi
 */
public class User {
    private String uuid,account;

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUuid() {
        return uuid;
    }

    public String getAccount() {
        return account;
    }
}
