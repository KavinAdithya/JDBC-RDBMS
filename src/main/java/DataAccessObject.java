import java.sql.Connection;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.ResultSet;
public class DataAccessObject {
    private String userName="root";
    private String passWord="KavinDharani@3";
    private String dataBaseConnectingLink="jdbc:mysql://localhost:3306/music";
    private String getDataQuery="SELECT * FROM student";
    private String insertDataQuery="INSERT INTO cell VALUE(?,?)";
    private String createTable="CREATE TABLE cell (Key_id INT PRIMARY KEY,phone INT)";
    private String deleteTable="DROP TABLE cell";
    private Connection con=null;
    private PreparedStatement ps=null;
    private Statement st=null;
    public void connectionForDataBase(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection(dataBaseConnectingLink,userName,passWord);
            st=con.createStatement();
            System.out.println("DataBaseConnected SuccessFully!!!");
        }
        catch(ClassNotFoundException e){
            System.out.println(e);
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
    public void createTable(){
        try{
            st=con.createStatement();
            int n=st.executeUpdate(createTable);
            System.out.println(n+" rows affected");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public static void main(String[] args){
        DataAccessObject dao=new DataAccessObject();
        dao.connectionForDataBase();
       // dao.createTable();
//        dao.updateRow(1,2);
//        dao.updateRow(2,3);
           //dao.addColumn("name");
       // dao.updateColumn("Key_id");
       // dao.dataParticular();
       // dao.setData();
       // dao.deleteRow();
        //dao.updateColumn("name");
        dao.deleteTable();

    }
    public void deleteTable(){
        try{
            System.out.println(st.executeUpdate("DROP TABLE cell"));
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void updateRow(int n,int r){
        try{
            ps=con.prepareStatement(insertDataQuery);
            ps.setInt(1,n);
            ps.setInt(2,r);
            System.out.println(ps.executeUpdate()+" rows affected");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void addColumn(String s){
        try{
            int n=st.executeUpdate("ALTER TABLE cell ADD "+s+" VARCHAR(10)");
            System.out.println(n+" Columns added");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void updateColumn(String s){
        try{
            int n=st.executeUpdate("ALTER TABLE cell DROP COLUMN "+s);
            System.out.println(n+" column affected!!!");
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void dataParticular(){
        try{
            ResultSet rs=st.executeQuery("SELECT name FROM cell WHERE phone=2");
            rs.next();
            System.out.println(" "+rs.getInt(1));
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void setData(){
        try{
            System.out.println(st.executeUpdate("UPDATE cell SET name='Dharani'"));
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
    public void deleteRow(){
        try{
            System.out.println(st.executeUpdate("DELETE FROM cell "));
        }
        catch(SQLException e){
            e.printStackTrace();
        }
    }
}
