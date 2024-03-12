import java.sql.DriverManager;
import java.util.Arrays;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.io.BufferedReader;
import java.sql.SQLException;
import java.io.InputStreamReader;
import java.io.IOException;
public class DataUpdating {
    private Connection con=null;
    private Statement st=null;
    private ResultSet rs=null;
    private PreparedStatement ps=null;
    private BufferedReader scanData=null;
    private String passWord=null;
    private String userName=null;
    private String dataBaseName=null;
    private String dataBaseConnection=null;
    private String tableName=null;
    public boolean connectDataBase(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection(dataBaseConnection,userName,passWord);
            st=con.createStatement();
            System.out.println("Successfully DataBase Has Been Connected!!!!");
            return true;
        }
        catch(ClassNotFoundException e){
            System.out.println("Class Driver Not Found!!\nCheck The Driver Package is correct or Present in Your Project!!");
            e.printStackTrace();
            return false;
        }
        catch(SQLException e){
            System.out.println("Failed To Connect The dataBase!!!");
            e.printStackTrace();
            return false;
        }
        catch(Exception e){
            System.out.println("Failed Due To Some Error! try To fix it?? And try Again Later!");
            e.printStackTrace();
            return false;
        }
    }
    public boolean getDataFromUser(){
        try{
            scanData=new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Enter The data Base Name : ");
            dataBaseName=scanData.readLine();
            System.out.print("Enter The user Name : ");
            userName=scanData.readLine();
            System.out.print("Enter Your PassWord : ");
            passWord=scanData.readLine();
            dataBaseConnection="jdbc:mysql://localhost:3306/"+dataBaseName;
            System.out.print("Enter Your Table Name : ");
            tableName=scanData.readLine();
            return true;
        }
        catch(IOException e){
            e.printStackTrace();
            return false;
        }
        catch(Exception e){
            System.out.println("Somethinng Went Wrong!!!");
            e.printStackTrace();
            return false;
        }
    }
    public void getDataUpdateOnDatBase(){
        int n=0;
        try {
            System.out.print("Enter The Number Of Rows : ");
            n = Integer.parseInt(scanData.readLine());
            String name = null;
            int key = 0;
            int rollNo = 0;
            ps = con.prepareStatement("INSERT INTO " + tableName + " VALUE(?,?,?)");
            for (int loop = 0; loop < n; loop++) {
                System.out.print("Enter Your Key of " + (loop+1)+ " row : ");
                key = Integer.parseInt(scanData.readLine());
                System.out.print("Enter Your Register Number of " + key + " Key : ");
                rollNo = Integer.parseInt(scanData.readLine());
                System.out.print("Enter Your Name of " + rollNo + " Register No :");
                name = scanData.readLine();
                ps.setInt(1, key);
                ps.setInt(2, rollNo);
                ps.setString(3, name);
                int nn=ps.executeUpdate();
                System.out.println(nn+" rows Affetcted");
            }
            ps.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(IOException e){
            e.printStackTrace();
        }
        catch(Exception e){
            System.out.println("Something Went Wrong "+e);
        }
    }
    public void getDataFromDataBase(){
        try{
            rs=st.executeQuery("SELECT * FROM "+tableName);
            System.out.println("Key  Register Number  Name");
            while(rs.next()){
                System.out.println(rs.getInt(1)+"   "+rs.getInt(2)+"             "+rs.getString(3));
            }
            st.close();
            con.close();
        }
        catch(SQLException e){
            e.printStackTrace();
        }
        catch(Exception e){
           System.out.println("Something Went Wrong !!!\n"+e);
        }
    }
    public static void main(String[] args){
        DataUpdating data=new DataUpdating();
        data.getDataFromUser();
        data.connectDataBase();
        data.getDataUpdateOnDatBase();
        data.getDataFromDataBase();
    }

}
