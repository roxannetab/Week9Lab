/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;

/**
 *
 * @author RT
 */

public class Role implements Serializable {

    int id;
    String name;

    public Role() {
    }

    public Role(int id) {
        this.id = id;
        if (id == 1) {

            this.name = "System Admin";
        } else if (id == 2) {
            this.name = "Regular User";

        }
    }

    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}