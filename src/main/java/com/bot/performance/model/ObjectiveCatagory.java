package com.bot.performance.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "objective_catagory")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ObjectiveCatagory {
    @Id
    @Column(name = "ObjectiveCatagoryId")
    @JsonProperty("ObjectiveCatagoryId")
    int objectiveCatagoryId;
    @Column(name = "ObjectiveCatagoryType")
    @JsonProperty("ObjectiveCatagoryType")
    public String objectiveCatagoryType;
    @Column(name = "TypeDescription")
    @JsonProperty("TypeDescription")
    public String typeDescription;
    @Column(name = "RolesId")
    @JsonProperty("RolesId")
    public String rolesId;
    @Column(name = "ObjectivesId")
    @JsonProperty("ObjectivesId")
    public String objectivesId;
    @Column(name = "IsTagByRole")
    @JsonProperty("IsTagByRole")
    public boolean isTagByRole;
    @Column(name = "IsTagByDepartment")
    @JsonProperty("IsTagByDepartment")
    public boolean isTagByDepartment;
    @Column(name = "FromDate")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("FromDate")
    public Date fromDate;
    @Column(name = "ToDate")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    @JsonProperty("ToDate")
    public Date toDate;
    @Column(name = "CreatedBy")
    @JsonProperty("CreatedBy")
    public Long createdBy;
    @Column(name = "UpdatedBy")
    @JsonProperty("UpdatedBy")
    public Long updatedBy;
    @Column(name = "CreatedOn")
    @JsonProperty("CreatedOn")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    public Date createdOn;
    @Column(name = "UpdatedOn")
    @JsonProperty("UpdatedOn")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    public Date updatedOn;
    @Column(name = "Status")
    @JsonProperty("Status")
    public String status;
    @Transient
    @JsonProperty("Total")
    int total;
    @Transient
    @JsonProperty("Index")
    int index;
}

