package dtx.oa.rbac.model;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "rbac_role")
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String uuid,roleName,remark;
    protected boolean status;
    protected Role parentRole;
    protected Set<User> users=new HashSet<>();

    @ManyToMany
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "rbac_role_user",joinColumns = {@JoinColumn(name = "roleId")},inverseJoinColumns = {@JoinColumn(name = "userId")})
    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

    @ManyToMany
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "rbac_role_node",joinColumns = {@JoinColumn(name = "roleId")},inverseJoinColumns = {@JoinColumn(name = "nodeId")})
    public Set<Node> getNodes() {
        return nodes;
    }

    public void setNodes(Set<Node> nodes) {
        this.nodes = nodes;
    }
    protected Set<Node> nodes=new HashSet<>();

    @ManyToOne(cascade = javax.persistence.CascadeType.ALL,fetch = FetchType.EAGER,optional = true)
    @JoinColumn(name = "parentId",referencedColumnName = "uuid")
    public Role getParentRole() {
        return parentRole;
    }

    public void setParentRole(Role parentRole) {
        this.parentRole = parentRole;
    }

    public Role() {
    }

    public Role(String uuid, String roleName, String remark, Role parent, boolean status) {
        this.uuid = uuid;
        this.roleName = roleName;
        this.remark = remark;
        this.parentRole = parent;
        this.status = status;
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
    public String getRoleName() {
        return roleName;
    }
    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
    
    @Column(nullable = true,columnDefinition = "text")
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    
    @Column(nullable = false,columnDefinition = "tinyint DEFAULT 0")
    public boolean getStatus() {
        return status;
    }
    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Role))return false;
        if(uuid==null)return false;
        return uuid.equals(((Role)obj).getUuid());
    }

    @Override
    public int hashCode(){
        if(uuid==null)return super.hashCode();
        int result=0;
        for(int i=0;i<uuid.length();i++)
                result+=uuid.charAt(i);
        return result;
    }
}
