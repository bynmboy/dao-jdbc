/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daojdbc;

import static java.lang.System.out;
import java.security.spec.DSAGenParameterSpec;
import java.util.List;
import model.dao.DaoFactory;
import model.dao.DepartmentDao;
import model.entities.Department;

/**
 *
 * @author fabio
 */
public class App2 {
    public static void main(String[] args) {
        DepartmentDao departmentDao = DaoFactory.createDepartmentDao();
        
//        System.out.println("=== Test 1: department insert ===");
//        Department newDepartment = new Department(null, "Sports");
//        departmentDao.insert(newDepartment);
//        System.out.println("Inserted! New Id = " + newDepartment.getId());
        
//        System.out.println("=== Test 2: department find by id ===");
//        Department dep = departmentDao.findById(7);
//        System.out.println(dep);

//        System.out.println("=== Test 3: department update ===");
//        dep = departmentDao.findById(5);
//        dep.setName("Toys");
//        departmentDao.update(dep);
//        System.out.println("Updated! department updated to = " + dep.getName());
//        
//        System.out.println("=== Test 3: department delete ===");
//        Department dep = departmentDao.findById(8);
//        departmentDao.deleteById(8);
//        System.out.println("Deleted! department: " + dep.getName() + " has been deleted!");
        
        System.out.println("=== Test 3: department find all ===");
        List<Department> depList = departmentDao.findAll();
        for (Department department : depList) {
            System.out.println(department);
        }
    }
}
