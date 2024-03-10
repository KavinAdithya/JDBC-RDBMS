import java.sql.*;
public class JavaConnectivity {
    public static void main(String []args) throws Exception{
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url="jdbc:mysql://localhost:3306/company";
        String userName="root";
        String passWord="KavinDharani@3";
        Connection con=DriverManager.getConnection(url,userName,passWord);
        Statement st=con.createStatement();
        ResultSet rs=st.executeQuery("SELECT * FROM programming");
        while(rs.next()){
            System.out.println(rs.getString(1)+" "+rs.getInt(2)+" "+rs.getString(3)+" "+rs.getString(4));
        }
    }
}
