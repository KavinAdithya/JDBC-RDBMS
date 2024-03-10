import java.sql.*;
public class MusicDataBase {
    public static void musicData(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url="jdbc:mysql://localhost:3306/music";
            String userName="root";
            String passWord="KavinDharani@3";
            Connection con=DriverManager.getConnection(url,userName,passWord);
            Statement st=con.createStatement();
            ResultSet rs=st.executeQuery("SELECT *FROM musiclist");
            while(rs.next()){
                System.out.println(rs.getInt(1)+"  "+rs.getInt(2)+"  "+rs.getInt(3)+"  "+rs.getString(4));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void main(String []args){
        musicData();
    }
}
