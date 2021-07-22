/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amos;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Amos Mutai
 */
public class DatabaseConnection {
       
////    
    public static void main(String[] args){
        DatabaseConnection conn = new DatabaseConnection();
        
        System.out.println(conn.getConnection());
    }
    
    
    public Connection getConnection(){
        
        Connection connection = null;

        
        try{
            Class.forName("com.mysql.jdbc.Driver");
//            Class.forName("com.mysql.cj.jdbc.Driver");
            
            
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_db", "mysql", "mysql123");
            
            
        }catch(Exception e){
            System.out.println(e);
            
        }
        
        return connection;
    }
        
}
