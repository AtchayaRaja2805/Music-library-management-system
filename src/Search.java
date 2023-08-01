import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.Statement;

class Songs{
    private String username;
    private String password;
    public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
}

public abstract class Search {
    public static void PreLogin(Scanner sc,Statement st) throws Exception
    {
        System.out.print("Have you registered already?(yes/no)");
        String reply=sc.nextLine();
        if(reply.equals("yes"))
        Login(sc,st);
        else
        Register(sc,st);
    }
    public static void end(Scanner sc,Statement st) throws Exception
    {
        System.out.println("Do you want to continue ? (yes/no)");
        String r=sc.next();
        if(r.equals("yes"))
        {
        Menu();
        ChooseOption(sc,st);
        }
        else if(r.equals("no"))
        {
            System.out.println("Thank You ! Have a Nice Day!!!");
        }
        else
        {
            System.out.print("Enter a valid option");
        }
    }
    public static void Register(Scanner sc,Statement st) throws Exception
    {
        System.out.println("Please Register !");
        System.out.print("Enter your username:");
        String Username=sc.nextLine();
        System.out.print("Enter your password:");
        String password=sc.nextLine();
        String query="insert into users values (\""+Username+"\",\""+password+"\")";
        st.executeUpdate(query);
        System.out.println("Resgistered Successfully!!!");
        // Login(sc,st);
    }
	public static int Login(Scanner sc,Statement st) throws Exception {
    	String query1="Select * from users";
        ResultSet rs1 = st.executeQuery(query1);
        
            Songs s=new Songs();
            System.out.print("\nEnter Username : ");
            s.setUsername(sc.nextLine());
            System.out.print("Enter Password : ");
            s.setPassword(sc.nextLine());
            String username = s.getUsername();
            String password = s.getPassword();
            
            int a=0;
            while(rs1.next())
             {
                if(username.equals(rs1.getString(1)) && password.equals(rs1.getString(2))) {
                    a=1;
                    System.out.println("Logged in Successfully!!");
                    break;
                }
             }
            while(a!=1) 
            {
                rs1 = st.executeQuery(query1);
                System.out.print("_________\n");
                System.out.print("Invalid Credentials !\n");
                a=Login(sc,st);
            }
        System.out.println("_________\n");
    rs1.close();
    return a;
        
    }
    public static void showplaylist(Scanner sc,Statement st) throws Exception
    {
        String query="Show tables where Tables_in_songlibrary not like 'songs' AND Tables_in_songlibrary not like 'users'";
        	ResultSet rs = st.executeQuery(query);
        	int count=1;
        	System.out.println();
        	while(rs.next()){
        		System.out.print(count+" :) ");
        		System.out.println(rs.getString(1));
        		count++;
            }
            end(sc,st);
    }
    public static void Menu(){
        System.out.println("1 :) Show Available Songs\n2 :) Add Song\n3 :) Create Playlist\n4 :)  Add Song to a playlist\n5 :) show Playlist\n6 :) Search songs");
        System.out.print("_________\n");
    } 
    public static void ChooseOption(Scanner sc,Statement st) throws Exception{
        System.out.print("Choose Your Option : ");
        int o=sc.nextInt();
        System.out.print("_________\n");
        if(o<1 || o>6){
            System.out.print("Invalid Option !\nChoose a Valid Option : ");
            o=sc.nextInt();
            System.out.println("_________\n");
        }
        else
        {
            if(o==1)
            showsongs(sc,st);
            if(o==2)
            addsongs(sc,st);
            if(o==3)
            createplaylist(sc,st);
            if(o==4)
            addsongtoplaylist(sc,st);
            if(o==5)
            showplaylist(sc,st);
            if(o==6)
            searchOptions(sc,st);
            
            
        }
        // return o;
    }
    public static void searchOptions(Scanner sc,Statement st) throws Exception
    {
        System.out.println("Search By\n1 :) Song Name\n2 :) Song Name & Composer");
        int y=sc.nextInt();
        if(y==1)
        {
            System.out.print("Song Name : ");
            sc.nextLine();
            String song_name=sc.nextLine();
            search(sc,st,song_name);
        }
        else if(y==2)
        {
            sc.nextLine();
            System.out.print("Song Name : ");
            String song_name=sc.nextLine();
            System.out.print("Composer : ");
            String composer=sc.nextLine();
            search(sc,st,song_name,composer);
        }
    }
    public static void showsongs(Scanner sc,Statement st) throws Exception
    {
        System.out.print(" Available Songs\n");
        String query="select * from songs";
        ResultSet rs=st.executeQuery(query);
        int count=1;
        while(rs.next())
        {
            System.out.println("number :"+count+"\n"+
            "Song Name :"+rs.getString(1)+"\n"+
            "Genre :"+rs.getString(2)+"\n"+
            "Playback Singer :"+rs.getString(3)+"\n"+
            "Composer :"+rs.getString(4)+"\n"
            // "URL :"+rs.getString(5)+"\n"
            );
            count++;
        }
        end(sc,st);
    }
    public static void search(Scanner sc,Statement st,String song_name) throws Exception
    {
        	String query="Select song_name,url from songs where song_name like '%"+song_name+"%'";
        	ResultSet rs = st.executeQuery(query);
            int g=0;
        	while(rs.next())
            {
                System.out.println(rs.getString(1));
        		System.out.println(rs.getString(2));
                g=1;
                break;
        	}
            if(g!=1)
            {
                System.out.println("Song not found !");
            }
            end(sc,st);
    }
    public static void search(Scanner sc,Statement st,String song_name,String composer) throws Exception
    {
        	String query="Select song_name,url from songs where song_name like '%"+song_name+"%' and composer like'%"+composer+"%'";
        	ResultSet rs = st.executeQuery(query);
            int g=0;
        	while(rs.next())
            {
                System.out.println(rs.getString(1));
        		System.out.println(rs.getString(2));
                g=1;
                break;
        	}
            if(g!=1)
            {
                System.out.println("Song not found !");
            }
            end(sc,st);
    }
    public static void addsongs(Scanner sc,Statement st) throws Exception
    {
        // System.out.println("Add a song !!!");
        sc.nextLine();
        System.out.print("Song name : ");
        String name=sc.nextLine();
        System.out.print("genre : ");
        String genre=sc.nextLine();
        System.out.print("Playback Singer : ");
        String singer=sc.nextLine();
        System.out.print("Composer : ");
        String composer=sc.nextLine();
        System.out.print("URL : ");
        String url=sc.nextLine();
        String query="insert into songs values (\""+name+"\",+\""+genre+"\",\""+singer+"\",\""+composer+"\",\""+url+"\")";
        st.executeUpdate(query);
        System.out.print("Song has been added successfully!!! ");
        end(sc,st);

    }
    // public static void display(Scanner sc,Statement st) throws Exception
    // {
    //     String query="select * from songs";
    //     ResultSet rs=st.executeQuery(query);
    //     while(rs.next())
    //     {
    //         System.out.println(rs.getString(1));
    //         System.out.println(rs.getString(2));
    //         System.out.println(rs.getString(3));
    //         System.out.println(rs.getString(4));
    //         System.out.println(rs.getString(5));
    //         System.out.println();
    //         System.out.println();
    //     }
    // }
    public static void createplaylist(Scanner sc,Statement st) throws Exception
    {
        System.out.println("Create a Playlist of your own!!!");
        System.out.println("Playlist name : ");
        sc.nextLine();
        String playlist_name=sc.next();
        String query=("Create table "+playlist_name+"(song_name varchar(50),genre varchar(50),singers varchar(50),composer varchar(50),url varchar(80),primary key(song_name))");
        st.executeUpdate(query);
        System.out.print("Playlist created Successfully !!!");
        end(sc,st);
    } 
    
    public static void addsongtoplaylist(Scanner sc,Statement st) throws Exception
    {
        System.out.println("Add song to your playslist");
        sc.nextLine();
        String query5="Show tables where Tables_in_songlibrary not like 'songs' AND Tables_in_songlibrary not like 'users'";
        	ResultSet rs9 = st.executeQuery(query5);
        	int count=1;
        	System.out.println();
        	while(rs9.next()){
        		System.out.print(count+" :) ");
        		System.out.println(rs9.getString(1));
        		count++;
            }
        System.out.println("Playlist Name : ");
        String playlist=sc.nextLine();
        System.out.println("Song Name : ");
        String song_name=sc.nextLine();
        String query=("select * from "+playlist);
        ResultSet rs=st.executeQuery(query);
        int z=0;
        while(rs.next())
        {
            if(song_name.equals(rs.getString(1)))
            {
                System.out.print("Song already found in playlist!!!");
                z=1;
                break;
            }
        }
        if(z!=1)
        {
            String query1="select * from songs";
            ResultSet rs2= st.executeQuery(query1);
            while(rs2.next())
            {
                if(song_name.equals(rs2.getString(1)))
                {
                z=2;
                // System.out.println("Song found in library");
                String name=rs2.getString(1);
                String genre=rs2.getString(2);
                String singer=rs2.getString(3);
                String composer=rs2.getString(4);
                String url=rs2.getString(5);
                String query3="insert into "+playlist+" values (\""+name+"\",+\""+genre+"\",\""+singer+"\",\""+composer+"\",\""+url+"\")";
                st.executeUpdate(query3);
                System.out.print("Song has been added to the playlist !!!");
                break;
                }
            }
        }
        if(z!=2)
        {
            System.out.print("Song was not found in library!!! Please add to the library!!!");
            addsongs(sc,st);
        }
        end(sc,st);
    }
}
