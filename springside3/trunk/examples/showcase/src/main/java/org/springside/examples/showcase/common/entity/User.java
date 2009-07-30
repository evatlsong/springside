package org.springside.examples.showcase.common.entity;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.apache.commons.lang.builder.ToStringBuilder;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springside.examples.showcase.orm.hibernate.AuditableEntity;
import org.springside.modules.utils.ReflectionUtils;

/**
 * 用户.
 * 
 * 使用JPA annotation定义ORM关系.
 * 
 * @author calvin
 */
@Entity
@Table(name = "SS_USER")
public class User extends AuditableEntity {
	private String loginName;
	private String password;
	private String name;
	private String email;
	private String status;
	private Integer version;

	private Set<Role> roles = new LinkedHashSet<Role>(); //有序的关联对象集合

	//Hibernate自动维护的Version字段
	@Version
	public Integer getVersion() {
		return version;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	//多对多定义
	@ManyToMany
	//级联保存
	@Cascade( { CascadeType.SAVE_UPDATE })
	//中间表定义,表名采用默认命名规则
	@JoinTable(name = "SS_USER_ROLE", joinColumns = { @JoinColumn(name = "USER_ID") }, inverseJoinColumns = { @JoinColumn(name = "ROLE_ID") })
	//Fecth策略定义
	@Fetch(FetchMode.SUBSELECT)
	//集合按id排序
	@OrderBy("id ASC")
	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Transient
	public String getRoleNames() {
		return ReflectionUtils.fetchElementPropertyToString(roles, "name", ", ");
	}

	@SuppressWarnings("unchecked")
	@Transient
	public List<Long> getRoleIds() {
		return ReflectionUtils.fetchElementPropertyToList(roles, "id");
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}
}