package dtx.oa.rbac.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "rbac_node")
public class Node implements Serializable {
    private static final long serialVersionUID = 1L;

    public final static int NODETYPESIGLENODE=1;
    public final static int NODETYPEGROUP=2;

    protected String uuid,title,address,remark;
    protected boolean status;
    protected int nodeType;
    protected Node parentNode;
    protected Set<Role> roles=new HashSet<>();

    @ManyToMany
    @Cascade(CascadeType.SAVE_UPDATE)
    @JoinTable(name = "rbac_role_node",joinColumns = {@JoinColumn(name = "nodeId")},inverseJoinColumns = {@JoinColumn(name = "roleId")})
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    @ManyToOne(cascade = javax.persistence.CascadeType.ALL)
    @JoinColumn(name = "parentId",referencedColumnName = "uuid")
    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public Node() {
    }

    public Node(String uuid, String title, String address, String remark, Node parent, boolean status, int nodeType) {
        this.uuid = uuid;
        this.title = title;
        this.address = address;
        this.remark = remark;
        this.parentNode = parent;
        this.status = status;
        this.nodeType = nodeType;
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
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    
    @Column(nullable = false)
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
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
    
    @Column(nullable = false)
    public int getNodeType() {
        return nodeType;
    }
    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    @Override
    public boolean equals(Object obj){
        if(!(obj instanceof Node))return false;
        if(uuid==null)return false;
        return uuid.equals(((Node)obj).getUuid());
    }

    @Override
    public int hashCode(){
        if(uuid==null)return super.hashCode();
        int result=0;
        for(int i=0;i<uuid.length();i++)
                result+=uuid.charAt(i);
        return result;
    }

    @Override
    public String toString() {
        return uuid;
    }
        
        
	
}
