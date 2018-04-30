package com.goseeky.checkdo.BusinessObjects;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

/**
 * Created by abhay on 28/4/18.
 * BO for folders
 */
@Entity(
        tableName = "FOLDERS",
        indices= {@Index(value = {"FOLDER_DESC"},
                unique = true)})
public class FolderBO {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "FOLDER_ID")
    private Integer folderId;

    @ColumnInfo(name = "FOLDER_DESC")
    private String folderDesc;

    @ColumnInfo(name = "IS_ACTIVE")
    private Boolean isActive;

    @ColumnInfo(name = "PARENT_ID")
    @NonNull
    private Integer folderParent;

    public Integer getFolderId() {
        return folderId;
    }

    public void setFolderId(Integer folderId) {
        this.folderId = folderId;
    }

    public String getFolderDesc() {
        return folderDesc;
    }

    public void setFolderDesc(String folderDesc) {
        this.folderDesc = folderDesc;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    @NonNull
    public Integer getFolderParent() {
        return folderParent;
    }

    public void setFolderParent(@NonNull Integer folderParent) {
        this.folderParent = folderParent;
    }


    @Override
    public String toString() {
        return "FolderBO{" +
                "folderId=" + folderId +
                ", folderDesc='" + folderDesc + '\'' +
                ", isActive=" + isActive +
                ", folderParent=" + folderParent +
                '}';
    }
}
