package dtx.oa.rbac.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "rbac_role_user")
public class RoleUser implements Serializable {
    private static final long serialVersionUID = 1L;

    protected String uuid;
    protected User user;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "userId",referencedColumnName = "uuid")
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "roleId",referencedColumnName = "uuid")
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    protected Role role;

    public RoleUser(){}

    public RoleUser(String id,User user,Role role){
        this.uuid=id;
        this.user=user;
        this.role=role;
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

    @Override
    public String toString(){
        return "uuid:"+((uuid==null) ? "[null]":uuid)+";"+
                "user_id:"+((user==null) ? "[null]":user.getUuid())+";"+
                "role_id:"+((role==null) ? "[null]":role.getUuid());
    }
}
