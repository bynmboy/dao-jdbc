/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model.dao.impl;

import db.DB;
import db.DbException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.dao.DepartmentDao;
import model.entities.Department;

/**
 *
 * @author fabio
 */
public class DepartmentDaoJDBC implements DepartmentDao {
    
    private Connection conn;

    public DepartmentDaoJDBC(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void insert(Department obj) {
        PreparedStatement st = null;
        
        try {
            st = conn.prepareStatement(
                "insert into department " +
                "(Name) " +
                "values (?) ",
                Statement.RETURN_GENERATED_KEYS
            );
            
            st.setString(1, obj.getName());
            
            int rowsAffected = st.executeUpdate();
            
            if (rowsAffected > 0) {
                ResultSet rs = st.getGeneratedKeys();
                if (rs.next()) {
                    int id = rs.getInt(1);
                    obj.setId(id);
                }
                DB.closeResultSet(rs);
            } else {
                throw new DbException("Unexpected erro! No rows affected!");
            }
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(st);
        }
    }

    @Override
    public void update(Department obj) {
        PreparedStatement st = null;
        
        try {
            st = conn.prepareStatement(
                "update department " +
                    "set Name = ? " +
                    "where Id = ?"
            );
            
            st.setString(1, obj.getName());
            st.setInt(2, obj.getId());
            
            st.executeUpdate();
            
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(st);
        }
    }

    @Override
    public void deleteById(Integer id) {
        PreparedStatement st = null;
        
        try {
            st = conn.prepareStatement(
                "delete from department " +
                "where Id = ?"
            );
            
            st.setInt(1, id);
            
            int rowsAffected = st.executeUpdate();
            
            if (rowsAffected == 0) {
                throw new DbException("The Department does not exist on Database");
            }
            
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatment(st);
        }
    }

    @Override
    public Department findById(Integer id) {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "select * from department " +
                "where department.Id = ? "
            );
            
            st.setInt(1, id);
            rs = st.executeQuery();
            
            if (rs.next()) {
                Department dep = instanciateDepartment(rs);
                return dep;
            }
            
            return null;
                
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }

    @Override
    public List<Department> findAll() {
        PreparedStatement st = null;
        ResultSet rs = null;
        
        try {
            st = conn.prepareStatement(
                "select * from department " +
                "order by Id"
            );
            
            rs = st.executeQuery();
            
            List<Department> list = new ArrayList<>();
            Map<Integer, Department> map = new HashMap<>();
            
            while(rs.next()) {
                
                Department dep = map.get(rs.getInt("Id"));
                
                if (dep == null) {
                    dep = instanciateDepartment(rs);
                    map.put(rs.getInt("Id"), dep);
                }
                
                dep = instanciateDepartment(rs);
                list.add(dep);
            }
            
            return list;
            
        } catch (SQLException e) {
            throw new DbException(e.getMessage());
        }
    }
    
    private Department instanciateDepartment(ResultSet rs) throws SQLException {
        Department dep = new Department();
        dep.setId(rs.getInt("Id"));
        dep.setName(rs.getString("Name"));
        return dep;
    }   
}
