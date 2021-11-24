/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.amos;

//import com.sun.media.jfxmedia.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;
//import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Amos Mutai
 */
@Named(value = "employee")
@SessionScoped
public class Employee implements Serializable {
    
    private int employee_id;
    private String employee_name;
    private String employee_address;

    /**
     * Creates a new instance of Employee
     */
    public Employee() {
    }
    
    /**
     * Get database records
     * @return  employee list
     */
    public ArrayList<Employee> getEmployeeList() {
        ArrayList<Employee> employeeList = new ArrayList<>();

        try{
            DatabaseConnection db = new DatabaseConnection();
            Connection connection = db.getConnection();
            
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM employees");
            
            while(rs.next()){
                Employee employee = new Employee();
                employee.setEmployee_id(rs.getInt("employee_id"));
                employee.setEmployee_name(rs.getString("employee_name"));
                employee.setEmployee_address(rs.getString("employee_address"));
                
                employeeList.add(employee);                        
            }

        }catch(SQLException e){
            System.out.println("Failed DB: " + e);
        } 
        
        return employeeList;        
    } 
    
     //add employee details
    public void addEmployee(){
        
        try{
            DatabaseConnection db = new DatabaseConnection();
            Connection connection = db.getConnection();
            
            
            PreparedStatement ps = connection.prepareStatement
                ("INSERT INTO employees(employee_name, employee_address)" +
                 " VALUES('"+ employee_name +"', '"+ employee_address +"')")
            ;
            ps.executeUpdate();
            
        }catch(SQLException e){ 
            System.out.println(e);
        }
    }
    
    //Edit Employee
    public String editEmployee(){
        
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int id = Integer.parseInt(params.get("action"));
        
        
        try{
            System.out.println("The id is : " +id);
            DatabaseConnection db = new DatabaseConnection();
            Connection connection = db.getConnection();
            
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM employees WHERE employee_id ="+ id);
            rs.next();

            employee_name = (rs.getString("employee_name"));
            employee_address = (rs.getString("employee_address"));
            employee_id = (rs.getInt("employee_id"));
            
        }catch(SQLException e){
            System.out.println(e);
        }
        return "/edit.xhtml?faces-redirect = true";
    }
    
    //Update edited employee
    public String updateEmployee(){
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        int id = Integer.parseInt(params.get("edit_employee_id"));
        
        try{
            DatabaseConnection db = new DatabaseConnection();
            Connection connection = db.getConnection();
     
            PreparedStatement ps = connection.prepareStatement("UPDATE employees SET employee_name = ?, employee_address = ? WHERE employee_id = ?");
            ps.setString(1, employee_name);
            ps.setString(2, employee_address);
            ps.setInt(3, id);
            
            ps.executeUpdate();
            
            
        }catch(SQLException e){
            System.out.println(e);
        }
        return "/index.xhtml?faces-redirect = true";
    }
    
    //Delete Employee
    public String deleteEmployee(){
        
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        
        int id = Integer.parseInt(params.get("action"));
        try{
            DatabaseConnection db = new DatabaseConnection();
            Connection connection = db.getConnection();
            
            PreparedStatement ps = connection.prepareStatement("DELETE FROM employees WHERE employee_id = "+id);
            ps.executeUpdate();
        }catch(SQLException e){
            System.out.println(e);
        }
        return "/index.xhtml?faces-redirect = true";
    }
     //    Setter Methods
    public void setEmployee_id(int employee_id){       
        this.employee_id = employee_id;
    }

    public void setEmployee_name(String employee_name) {
        this.employee_name = employee_name;
    }
    
    public void setEmployee_address(String employee_address) {
        this.employee_address = employee_address;
    }
    
    //    Getter methods
    public int getEmployee_id() {
        return employee_id;
    }

    public String getEmployee_name() {
        return employee_name;
    }

    public String getEmployee_address() {
        return employee_address;
    }   
}
