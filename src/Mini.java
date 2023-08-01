import java.util.*;
import java.sql.*;


public class Mini extends Search{
    
    public static void main(String[] args) throws Exception{
    	Scanner sc=new Scanner(System.in);
    	
    	String url="jdbc:mysql://localhost:3306/songlibrary";
    	String userName="root";
    	String passWord="Ajat2805@";
    	
    	Connection con = DriverManager.getConnection(url,userName,passWord);
    	Statement st = con.createStatement();
        Mini mini=new Mini();
        
        System.out.print("_________\n");
        System.out.print("      Welcome to Music library!!!     \n");
        System.out.print("_________\n");
        mini.PreLogin(sc,st);
    	mini.Menu();
        mini.ChooseOption(sc,st);
    

        
        
        sc.close();
        con.close();
    }
}