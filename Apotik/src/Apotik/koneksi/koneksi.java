

package Apotik.koneksi;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;


public class koneksi {
    Connection koneksi = null;
    public static Connection koneksiDb() {
    try{
        Class.forName("com.mysql.jdbc.Driver");
        Connection koneksi = DriverManager.getConnection("jdbc:mysql://localhost/apotik","root","");
        return koneksi;
    }
    catch (Exception e){
        JOptionPane.showMessageDialog(null, e);
        return null;
    }
    }

}
