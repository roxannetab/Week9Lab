/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package services;

import dataaccess.RoleDB;
import java.util.List;
import models.Role;
/**
 *
 * @author RT
 */
public class RoleService {

    public Role get(int roleId) throws Exception {
        RoleDB roleDB = new RoleDB();
        Role role = roleDB.get(roleId);
        return role;
    }

    public List<Role> getAll() throws Exception {
        RoleDB roleDB = new RoleDB();
        List<Role> roles = roleDB.getAll();
        return roles;
    }

    public void insert(int roleId, String roleName) throws Exception {
        Role role = new Role(roleId, roleName);
        RoleDB roleDB = new RoleDB();
        roleDB.insert(role);
    }

    public void update(int roleId, String roleName) throws Exception {
        Role role = new Role(roleId, roleName);
        RoleDB roleDB = new RoleDB();
        roleDB.update(role);
    }

    public void delete(int roleId) throws Exception {
        Role role = new Role();
        role.setRoleId(roleId);
        RoleDB roleDB = new RoleDB();
        roleDB.update(role);
    }
}