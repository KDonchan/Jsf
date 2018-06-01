/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import static sql.SqlMain.makeConnection;

/**
 *
 * @author j-knakagami2
 */
@Named(value = "sessionUser")
@SessionScoped
public class SessionUser implements Serializable {
    @Inject private JsfApp jsfApp;
    private String userId,userName,userPass,userNameKana;
    private boolean loginFlg,adminFlg,editable;
    
    private List<User> userMember;
    /**
     * Creates a new instance of SessionUser
     */
    public SessionUser() {
        userMember = new ArrayList<>();
    }

    public JsfApp getJsfApp() {
        return jsfApp;
    }

    public void setJsfApp(JsfApp jsfApp) {
        this.jsfApp = jsfApp;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
    }

    public String getUserNameKana() {
        return userNameKana;
    }

    public void setUserNameKana(String userNameKana) {
        this.userNameKana = userNameKana;
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

    public List<User> getUserMember() {
        return userMember;
    }

    public void setUserMember(List<User> userMember) {
        this.userMember = userMember;
    }

    public boolean loginCheck(RequestUser wUser) throws SQLException, ClassNotFoundException{
        loginFlg = false;
        String wUrl = jsfApp.getJdbcUrl();
        Connection wcon = makeConnection(wUrl);
        String wSql = "select * from userTbl where userId=? and userPass=?";
        PreparedStatement stmt = wcon.prepareStatement(wSql);
        stmt.setString(1, wUser.getUserId());
        stmt.setString(2, wUser.getUserPass());
        ResultSet rs = stmt.executeQuery();
        if(rs.next()){//ログインできた時
            loginFlg = true;
            userId = rs.getString("userId");
            userPass = rs.getString("userPass");
            userName = rs.getString("userName");
            userNameKana = rs.getString("userNameKana");
            wUser.setUserName(userName);
            wUser.setUserNameKana(userNameKana);
            wUser.setLoginFlg(loginFlg);
        }
        return loginFlg;
    }
}
