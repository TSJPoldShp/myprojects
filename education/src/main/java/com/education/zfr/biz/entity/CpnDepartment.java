package com.education.zfr.biz.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cpn_department")
public class CpnDepartment{

    /**
     *
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="DEPARTMENT_ID")
    private Long departmentId;

    /**
     *
     */
    @Column(name="PARENT_ID")
    private Long parentId;

    /**
     *
     */
    @Column(name="DEPARTMENT_NAME")
    private String departmentName;

    /**
     *
     */
    @Column(name="DEPARTMENT_DESC")
    private String departmentDesc;

    /**
     * 创建时间
     */
    @Column(name="CREATE_TIME")
    private Date createTime;

    /**
     * 创建人
     */
    @Column(name="CREATE_BY")
    private String createBy;

    /**
     * 更新时间
     */
    @Column(name="UPDATE_TIME")
    private Date updateTime;

    /**
     * 更新人
     */
    @Column(name="UPDATE_BY")
    private String updateBy;

    public void setDepartmentId(Long departmentId){
        this.departmentId=departmentId;
    }
    public Long getDepartmentId(){
        return departmentId;
    }
    public void setParentId(Long parentId){
        this.parentId=parentId;
    }
    public Long getParentId(){
        return parentId;
    }
    public void setDepartmentName(String departmentName){
        this.departmentName=departmentName;
    }
    public String getDepartmentName(){
        return departmentName;
    }
    public void setDepartmentDesc(String departmentDesc){
        this.departmentDesc=departmentDesc;
    }
    public String getDepartmentDesc(){
        return departmentDesc;
    }
    public void setCreateTime(Date createTime){
        this.createTime=createTime;
    }
    public Date getCreateTime(){
        return createTime;
    }
    public void setCreateBy(String createBy){
        this.createBy=createBy;
    }
    public String getCreateBy(){
        return createBy;
    }
    public void setUpdateTime(Date updateTime){
        this.updateTime=updateTime;
    }
    public Date getUpdateTime(){
        return updateTime;
    }
    public void setUpdateBy(String updateBy){
        this.updateBy=updateBy;
    }
    public String getUpdateBy(){
        return updateBy;
    }
    @Override
    public String toString()
    {
        return ToStringBuilder.reflectionToString(this);
    }
}
