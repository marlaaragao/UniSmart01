/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.sql.SQLException;

public class DerbyUtils {

    public DerbyUtils() {
        //empty constructor -- helper class
    }

    public static boolean tableAlreadyExists(SQLException e) {
        boolean exists;
        if(e.getSQLState().equals("X0Y32")) {
            exists = true;
        } else {
            exists = false;
        }
        return exists;
    }
}