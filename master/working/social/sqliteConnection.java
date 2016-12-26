/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Social;

import javax.swing.*;
import java.sql.*;




public class sqliteConnection {
    
    Connection conn = null; 
    
    public static Connection dbConnector(){
        try{
            
            Class.forName("org.sqlite.JDBC");
            Connection conn = DriverManager.getConnection("jdbc:sqlite:/homes/au302/Desktop/PlaylistDatabase.sqlite");
            return conn;
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
            return null;
            
        }
        
        
        
    }
    
    
}
