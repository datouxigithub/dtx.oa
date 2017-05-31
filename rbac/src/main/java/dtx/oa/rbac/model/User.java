package dtx.oa.rbac.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenerationTime;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "rbac_user")
public class User implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String uuid,account,password,loginIp,remark;//password经过md5加密,account全部转换为小写存储
    protected Date createTime,loginTime;
    protected boolean status;
    protected Set<RoleUser> roleUsers=new HashSet<>();
    
    public User(){
    }
    
    public User(String userId){
        this.uuid=userId;
    }
    
    public User(String userId,String account,String password){
        this.uuid=userId;
        this.account=account;
        setPassword(password);
    }

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER,cascade = {javax.persistence.CascadeType.REFRESH,javax.persistence.CascadeType.REMOVE})
//    @Cascade(CascadeType.SAVE_UPDATE)
//    @JoinTable(name = "rbac_role_user",joinColumns = {@JoinColumn(name = "userId")},inverseJoinColumns = {@JoinColumn(name = "roleId")})
    public Set<RoleUser> getRoleUsers() {
        return roleUsers;
    }

    public void setRoleUsers(Set<RoleUser> roleUsers) {
        this.roleUsers = roleUsers;
    }

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid",strategy = "uuid")
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    
    @Column(nullable = false,unique = true)
    public String getAccount() {
        return account;
    }
    public void setAccount(String account) {
        this.account=account;
    }
    
    @Column(nullable = false)
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    @Column(nullable = true)
    public String getLoginIp() {
        return loginIp;
    }
    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }
    
    @Column(nullable = true,columnDefinition = "text")
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(nullable = false,columnDefinition = "timestamp default current_timestamp")
    @Temporal(TemporalType.TIMESTAMP)
    @org.hibernate.annotations.Generated(GenerationTime.INSERT)
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = true)
    public Date getLoginTime() {
        return loginTime;
    }
    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }
    
    @Column(nullable = false,columnDefinition = "tinyint DEFAULT 0")
    public boolean getStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }
}
