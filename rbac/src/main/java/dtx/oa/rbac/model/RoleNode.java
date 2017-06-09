package dtx.oa.rbac.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "rbac_role_node")
public class RoleNode implements Serializable {

    private static final long serialVersionUID = 1L;

    protected String uuid;
    protected Role role;

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "roleId",referencedColumnName = "uuid",nullable = false)
    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @ManyToOne(cascade = CascadeType.MERGE,fetch = FetchType.EAGER)
    @JoinColumn(name = "nodeId",referencedColumnName = "uuid",nullable = false)
    public Node getNode() {
        return node;
    }

    public void setNode(Node node) {
        this.node = node;
    }
    protected Node node;

    public RoleNode() {
    }

    public RoleNode(String uuid, Role role, Node node) {
        this.uuid = uuid;
        this.role=role;
        this.node=node;
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
                "role_id:"+((role==null) ? "[null]":role.getUuid())+";"+
                "node_id:"+((node==null) ? "[null]":node.getUuid());
    }

    @Override
    public boolean equals(Object obj) {
        if(obj==null||!(obj instanceof RoleNode))return false;
        return uuid==null ? false:uuid.equals(((RoleNode)obj).getUuid());
    }

    @Override
    public int hashCode() {
        if(uuid==null)
            return super.hashCode();
        int result=0;
        for(int i=0,len=uuid.length();i<len;i++)
            result+=uuid.charAt(i);
        return result;
    }
        
        
}
