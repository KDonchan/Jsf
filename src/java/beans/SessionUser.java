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
import sql.SqlMain;
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
    private String errMessage;
    private boolean loginFlg,adminFlg,editable;
    
    private List<User> userMember;
    /**
     * Creates a new instance of SessionUser
     */
    public SessionUser() {
        userMember = new ArrayList<>();
    }
    
  

    public String getErrMessage() {
        return errMessage;
    }

    public void setErrMessage(String errMessage) {
        this.errMessage = errMessage;
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
    
    //テーブル「userTbl」でuserIdとuserPassが一致する行があるかチェック
    // retrun値
    //    =true:あり  メンバー変数を一致行のデータで書き換え
    //    =false：一致する行無し
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
    
    //テーブル「userTbl」にユーザ一人を追加
    public boolean userAdd() throws SQLException, ClassNotFoundException {
        boolean flg = false;       
        Connection wcon = sql.SqlMain.makeConnection(jsfApp.getJdbcUrl());
        String wsql = "insert into userTbl(userId,userPass,userName,userNameKana) values(?,?,?,?)";
        PreparedStatement stmt = wcon.prepareStatement(wsql);
        System.out.println("userAdd ->" + userId +":" + userName+":" + userNameKana);
        stmt.setString(1, userId);
        stmt.setString(2, userPass);
        stmt.setString(3,userName);
        stmt.setString(4, userNameKana);
        stmt.executeUpdate();
        flg=true;        
        return flg;
    }
    
    //テーブル「userTbl」の行更新処理
    public String userEdit() {
        String nextPage=null;
        errMessage="";
        try{
        String wUrl = jsfApp.getJdbcUrl();
        Connection wcon = SqlMain.makeConnection(wUrl);
        String wsql="update userTbl set userPass=?,userName=?,userNameKana=? where userId=?";
        PreparedStatement stmt = wcon.prepareStatement(wsql);
        stmt.setString(1, userPass);
        stmt.setString(2, userName);
        stmt.setString(3, userNameKana);
        stmt.setString(4, userId);
        stmt.executeUpdate();
        nextPage="user";
        } catch (SQLException ex) {
            errMessage += ex.getMessage();
        } catch (ClassNotFoundException ex) {
            errMessage += ex.getMessage();
        }
        return nextPage;
    }
    
    //ログインユーザをテーブル「userTbl」から削除
    public boolean userDel() throws SQLException, ClassNotFoundException{
        boolean flg = false;
        Connection wcon = makeConnection(jsfApp.getJdbcUrl());
        String wsql = "delete from userTbl where userId =?";
        PreparedStatement stmt = wcon.prepareStatement(wsql);
        stmt.setString(1, userId);
        flg= true;
        return flg;
    }
    
    //userId重複チェック
    public boolean userIdFind(String wId) throws SQLException, ClassNotFoundException{
        boolean flg = false;
        Connection wcon = makeConnection(jsfApp.getJdbcUrl());
        String wsql = "select * from userTbl where userId =?";
        PreparedStatement stmt = wcon.prepareStatement(wsql);
        stmt.setString(1, wId);
        ResultSet rs = stmt.executeQuery();
        if(rs.next())
            flg=true;
        return flg;
    }
    

}
