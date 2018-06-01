/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

/**
 *
 * @author j-knakagami2
 */
public class User {
  private String userId ,userPass;
    private String userName,userNameKana;
    private String errMessage;
    private boolean loginFlg,adminFlg,editable,editFlg;

    public User() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserNameKana() {
        return userNameKana;
    }

    public void setUserNameKana(String userNameKana) {
        this.userNameKana = userNameKana;
    }

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
    }

    public boolean isLoginFlg() {
        return loginFlg;
    }

    public void setLoginFlg(boolean loginFlg) {
        this.loginFlg = loginFlg;
    }

    public boolean isAdminFlg() {
        return adminFlg;
    }

    public void setAdminFlg(boolean adminFlg) {
        this.adminFlg = adminFlg;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public boolean isEditFlg() {
        return editFlg;
    }

    public void setEditFlg(boolean editFlg) {
        this.editFlg = editFlg;
    }
    
    
}
