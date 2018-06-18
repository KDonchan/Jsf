/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.servlet.http.Part;


/**
 *
 * @author j-knakagami2
 */
@Named(value = "requestUser")
@ViewScoped
public class RequestUser implements Serializable{
    @Inject private JsfApp jsfApp;
    @Inject private SessionUser sessionUser;
    private String userId ,userPass;
    private String userName,userNameKana;
    private byte[] userPhoto;//6月18日追加　ユーザ顔写真登録列
    private Part photoFile;//6月18日追加
    private String errMessage;
    private boolean loginFlg,editable;
    /**
     * Creates a new instance of RequestUser
     */
    public RequestUser() {
    }
    
    //getter , setterメソッド定義

    public byte[] getUserPhoto() {
        return userPhoto;
    }
    public void setUserPhoto(byte[] userPhoto) {
        this.userPhoto = userPhoto;
    }

    public Part getPhotoFile() {
        return photoFile;
    }

    public void setPhotoFile(Part photoFile) {
        this.photoFile = photoFile;
    }


    
    public JsfApp getJsfApp() {
        return jsfApp;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    
    
    public void setJsfApp(JsfApp jsfApp) {
        this.jsfApp = jsfApp;
    }

    public SessionUser getSessionUser() {
        return sessionUser;
    }

    public void setSessionUser(SessionUser sessionUser) {
        this.sessionUser = sessionUser;
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


    //ログインボタンクリック時の処理
    public String loginNext(){
        String nextPage=null;
        try{
        if(sessionUser.loginCheck(this)){ //ログインできるとき
            nextPage="user";
            errMessage="";
        }else{ //ログインできないとき
           userPass = "";
           errMessage="IDかパスワードが違います";
        }
        }catch (SQLException err){
            System.out.println("Connection make Error! ->" + userId +":" + userPass);
        }catch(ClassNotFoundException err){
            System.out.println("Driver not found!!");
        }
        return nextPage;
    }
    
    public String userAdd(){
        String nextPage = null;
        errMessage="";
        try{
        if(!sessionUser.userIdFind(userId)){
            sessionUser.setUserId(userId);
            sessionUser.setUserPass(userPass);
            sessionUser.setUserName(userName);
            sessionUser.setUserNameKana(userNameKana);
            
            //6月18日追加　顔写真を登録する処理
            try{
                InputStream ioStream= photoFile.getInputStream();
                userPhoto = new byte[(int) photoFile.getSize()];           
                ioStream.read(userPhoto);
                sessionUser.setUserPhoto(userPhoto);
            } catch (IOException ex) {
                Logger.getLogger(RequestUser.class.getName()).log(Level.SEVERE, null, ex);
            }
            //顔写真登録処理　ここまで
            
            if(sessionUser.userAdd()){
                nextPage="login";
            }        
        }else{
            errMessage += "登録済みIDです";
        }
        } catch (SQLException ex) {
                errMessage += ex.getMessage();
            } catch (ClassNotFoundException ex) {
                errMessage += ex.getMessage();
            }
        return nextPage;
    }
    
    
    //ユーザID重複チェック処理
    public void userIdCheck(){
        errMessage="";        
        try {
            if(sessionUser.userIdFind(userId)){
                errMessage += "入力されたID" + userId + "は登録済み";
                editable =false;
            }else
                editable = true;
        } catch (SQLException ex) {
            Logger.getLogger(RequestUser.class.getName()).log(Level.SEVERE, null, ex);
            errMessage += ex.getMessage();
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(RequestUser.class.getName()).log(Level.SEVERE, null, ex);
            errMessage += ex.getMessage();
        }
    }
}
