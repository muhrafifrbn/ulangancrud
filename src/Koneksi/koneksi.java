/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package Koneksi;
import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;
/**
 *
 * @author lenovot470s
 */
public class koneksi {
 private static Connection conn;
    /**
     * @param args the command line arguments
     */public static Connection getcon(){
        if(conn == null){
            try {
                String url = "jdbc:mysql://localhost/dataBarangJam";
                String username = "root";
                String password = "";
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                conn = DriverManager.getConnection(url,username,password);
//                JOptionPane.showMessageDialog(null, "Koneksi Berhasil!","Pesan",JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception e) {
                System.out.println(e);
                JOptionPane.showMessageDialog(null, "Koneksi Tidak Berhasil!","Pesan",JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Erorr"+e.getMessage());
            }
        }
        return conn;
    }
}
