package com.worktracker.model.dto;

public class UpdatePasswordDTO {

    private char[] newPassword;
    private char[] oldPassword;


    public char[] getNewPassword() {
        return newPassword;
    }

    public char[] getOldPassword() {
        return oldPassword;
    }

    public void setNewPassword(char[] newPassword) {
        this.newPassword = newPassword;
    }

    public void setOldPassword(char[] oldPassword) {
        this.oldPassword = oldPassword;
    }
}
