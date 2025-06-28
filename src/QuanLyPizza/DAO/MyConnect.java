package QuanLyPizza.DAO;

import MyCustom.MyDialog;
import com.mysql.jdbc.Driver;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnect {

    public static Connection conn = null;
    private String severName;
    private String dbName;
    private String userName;
    private String password;

    public MyConnect() {
        docFileText();

        String strConnect = "jdbc:mysql://" + severName + "/" + dbName + "?useUnicode=true&characterEncoding=utf-8";
        Properties pro = new Properties();
        pro.put("user", userName);
        pro.put("password", password);
        try {
            com.mysql.jdbc.Driver driver = new Driver();
            conn = driver.connect(strConnect, pro);
        } catch (SQLException ex) {
            new MyDialog("Không kết nối được tới CSDL!", MyDialog.ERROR_DIALOG);
            System.exit(0);
        }

    }

   private void docFileText() {
    severName = "";
    dbName = "";
    userName = "";
    password = "";

    try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("ConnectVariable.txt"), "UTF-8"))) {
        severName = br.readLine();
        dbName = br.readLine();
        userName = br.readLine();
        password = br.readLine();

        if (severName == null || dbName == null || userName == null) {
            throw new IllegalArgumentException("Thiếu tham số kết nối trong ConnectVariable.txt");
        }

        if (password == null) {
            password = "";
        }
    } catch (Exception e) {
        new MyDialog("Không thể đọc file ConnectVariable.txt!\n" + e.getMessage(), MyDialog.ERROR_DIALOG);
        System.exit(1);
    }
   }
}
