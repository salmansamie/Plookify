/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Accountjavafx;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author guxuanyu
 */
public class LoginModel {
    Connection connect;
    public LoginModel(){
        connect = SQLConnection.Connector();
        if(connect == null) System.exit(1);
    }
    
    public boolean isConnected(){
        try {
           return !connect.isClosed();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        
    }
    public boolean isLogin(String user,String pass) throws SQLException {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "select * from Users where username = ? and password = ?";
        
        try{
           preparedStatement = connect.prepareStatement(query);
           preparedStatement.setString(1,user);
           preparedStatement.setString(2,pass);
           
           resultSet = preparedStatement.executeQuery();
           if(resultSet.next()){
               return true;
           }
           else {
               return false;
           }
        } catch(Exception e){
            return false;
        } finally{
            preparedStatement.close();
            resultSet.close();
        }
    }
}
