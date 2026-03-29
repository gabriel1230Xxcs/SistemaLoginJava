package conexion;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexion {
    
    public static Connection conectar() {
        Connection con = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/login_java",
                "root",
                ""
            );
            
            System.out.println("Conectado");
            
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
        
        return con;
    }
}