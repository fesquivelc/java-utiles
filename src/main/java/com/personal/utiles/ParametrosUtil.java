/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.personal.utiles;

/**
 *
 * @author fesquivelc
 */
public class ParametrosUtil {
    public static final int MSSQLSERVER = 1;
    public static final int MYSQL = 4;
    public static final int POSTGRESQL = 2;
    public static final int ORACLE = 3;
    
    public static String obtenerDriver(int tipoBD){
        switch(tipoBD){
            case MSSQLSERVER:
                return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
                
            case MYSQL:
                return "com.mysql.jdbc.Driver";
            case POSTGRESQL:
                return "org.postgresql.Driver";
            case ORACLE:
                return "oracle.jdbc.OracleDriver";
            default:
                return "";
        }
    }
}
