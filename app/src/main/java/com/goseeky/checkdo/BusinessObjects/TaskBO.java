package com.goseeky.checkdo.BusinessObjects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.sql.Date;

/**
 * Created by abhay on 28/4/18.
 */

@Entity(tableName = "TASKS")
public class TaskBO {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "TASK_ID")
    private Integer taskId;

    @ColumnInfo(name = "TASK_DESC")
    private String taskDesc;

    @ColumnInfo(name = "TASK_DDLINE")
    private String taskDeadline;

    @ColumnInfo(name = "TASK_PRIORITY")
    private Integer taskPriority;

    @ColumnInfo(name = "IS_ACTIVE")
    private Boolean isActive;

    @ColumnInfo(name = "PARENT_ID")
    @NonNull
    private Integer taskParent;

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
    }

    public String getTaskDesc() {
        return taskDesc;
    }

    public void setTaskDesc(String taskDesc) {
        this.taskDesc = taskDesc;
    }

    public String getTaskDeadline() {
        return taskDeadline;
    }

    public void setTaskDeadline(String taskDeadline) {
        this.taskDeadline = taskDeadline;
    }

    public Integer getTaskPriority() {
        return taskPriority;
    }

    public void setTaskPriority(Integer taskPriority) {
        this.taskPriority = taskPriority;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @NonNull
    public Integer getTaskParent() {
        return taskParent;
    }

    public void setTaskParent(@NonNull Integer taskParent) {
        this.taskParent = taskParent;
    }
}
