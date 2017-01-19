package com.sc.csl.retail.crm.persistence.entity;

import org.springframework.data.jpa.domain.AbstractPersistable;

import io.katharsis.resource.annotations.JsonApiId;
import io.katharsis.resource.annotations.JsonApiResource;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "users")
@Table(name = "users")
@JsonApiResource(type = "users")
public class User extends AbstractPersistable<Long> {

	@Id
	@JsonApiId
	private Long id;
	
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="employee_name", length = 64, nullable = false)
    private String employeeName;

    @Column(name="pw_id", length = 6, nullable = false)
    private String pwId;

    @Column(name="role", length = 32, nullable = false)
    private String role;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPwId() {
        return pwId;
    }

    public void setPwId(String pwId) {
        this.pwId = pwId;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
	
//	@Override
//	public boolean isNew(){
//		return super.isNew();
//	}	
	
	public void setNew(boolean isNew){
	}	

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        User user = (User) o;

        if (!employeeName.equals(user.employeeName)) return false;
        if (!pwId.equals(user.pwId)) return false;
        return role != null ? role.equals(user.role) : user.role == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + employeeName.hashCode();
        result = 31 * result + pwId.hashCode();
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }
}
