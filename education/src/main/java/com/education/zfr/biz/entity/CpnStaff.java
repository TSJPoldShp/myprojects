package com.education.zfr.biz.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name = "cpn_staff")
public class CpnStaff {

    /**
     * 员工编号
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "STAFF_ID")
    private Long staffId;

    /**
     * 部门编号
     */
    @Column(name = "DEPARTMENT_ID")
    private Long departmentId;

    /**
     * 部门名称
     */
    @Transient
    private String departmentName;

    /**
     * 职位编号
     */
    @Column(name = "POSITION_ID")
    private Long positionId;

    /**
     * 职位名称
     */
    @Transient
    private String positionName;

    /**
     * 员工姓名
     */
    @Column(name = "STAFF_NAME")
    private String staffName;

    /**
     * 员工性别
     */
    @Column(name = "STAFF_GENDER")
    private String staffGender;

    /**
     * 身份证号
     */
    @Column(name = "STAFF_NUMBER")
    private String staffNumber;

    /**
     * 登录名
     */
    @Column(name = "LOGIN_NAME")
    private String loginName;

    /**
     * 登录密码
     */
    @Column(name = "LOGIN_PWD")
    private String loginPwd;

    /**
     * 创建时间
     */
    @Column(name = "CREATE_TIME")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name = "CREATE_BY")
    private String createBy;

    /**
     * 更新时间
     */
    @Column(name = "UPDATE_TIME")
    private Date updateTime;

    /**
     * 更新人
     */
    @Column(name = "UPDATE_BY")
    private String updateBy;

    /**
     * 详细信息
     */
    @Transient
    private String desc;

    @Transient
    private int number;

    public void setStaffId(Long staffId) {
        this.staffId = staffId;
    }

    public Long getStaffId() {
        return staffId;
    }

    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }

    public Long getDepartmentId() {
        return departmentId;
    }

    public void setPositionId(Long positionId) {
        this.positionId = positionId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public Long getPositionId() {
        return positionId;
    }

    public void setStaffName(String staffName) {
        this.staffName = staffName;
    }

    public String getStaffName() {
        return staffName;
    }

    public void setStaffGender(String staffGender) {
        this.staffGender = staffGender;
    }

    public String getStaffGender() {
        return staffGender;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public String getStaffNumber() {
        return staffNumber;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginPwd(String loginPwd) {
        this.loginPwd = loginPwd;
    }

    public String getLoginPwd() {
        return loginPwd;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
