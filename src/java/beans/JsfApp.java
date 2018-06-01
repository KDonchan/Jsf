/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;

/**
 *
 * @author j-knakagami2
 */
@Named(value = "jsfApp")
@ApplicationScoped
public class JsfApp {
private String srvName,dbName,portNumber,loginUser,loginPass;
    private String jdbcUrl;
    private boolean integratedFlg;
    {
        srvName="pc145-07";
        dbName="web3";
        portNumber="1433";
        integratedFlg=true;
        loginUser="3bst";
        loginPass="P@ssword";
    }
    /**
     * Creates a new instance of JsfApp
     */
    public JsfApp() {
    }

    public String getSrvName() {
        return srvName;
    }

    public void setSrvName(String srvName) {
        this.srvName = srvName;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getPortNumber() {
        return portNumber;
    }

    public void setPortNumber(String portNumber) {
        this.portNumber = portNumber;
    }

    public String getLoginUser() {
        return loginUser;
    }

    public void setLoginUser(String loginUser) {
        this.loginUser = loginUser;
    }

    public String getLoginPass() {
        return loginPass;
    }

    public void setLoginPass(String loginPass) {
        this.loginPass = loginPass;
    }

    public void setJdbcUrl(String jdbcUrl) {
        this.jdbcUrl = jdbcUrl;
    }

    public boolean isIntegratedFlg() {
        return integratedFlg;
    }

    public void setIntegratedFlg(boolean integratedFlg) {
        this.integratedFlg = integratedFlg;
    }
    
    public String getJdbcUrl() {
        jdbcUrl ="jdbc:sqlserver://" + srvName+":"+portNumber +
                ";IntegratedSecurity=" +String.valueOf(integratedFlg) + ";databaseName="+ dbName;
        if(integratedFlg ==false )
            jdbcUrl+= ";;user=" +loginUser +";password="+ loginPass;
        System.out.println("JDBC URL ->" + jdbcUrl);
        return jdbcUrl;
    }
    
}
