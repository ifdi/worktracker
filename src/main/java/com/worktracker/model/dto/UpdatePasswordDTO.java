package com.worktracker.model.dto;

public class UpdatePasswordDTO {

    private String newPassword;
    private String oldPassword;


    public String getNewPassword() {
        return newPassword;
    }

    public String getOldPassword() {
        return oldPassword;
    }
}
