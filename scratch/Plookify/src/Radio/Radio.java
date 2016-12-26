/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Radio;

import java.sql.*;

/**
 * 
 * @author salmansamie
 */

public class Radio {
    public static void main(String args[]){
        
        Connection conn = null;
        
        try{
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:PlaylistDatabase.sqlite");
            System.out.println("database open successful");
        }
        
        catch(Exception e){
            System.exit(0);
        }
    }  
}
