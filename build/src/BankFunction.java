
import java.io.File;;
import java.sql.*;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class BankFunction {
 public static boolean CheckID(Connection connection,String acc)throws Exception{
      boolean decision=true ;
      String CekSql = "SELECT * from Account WHERE accountNumber = ? ";  
      try (PreparedStatement Check = connection.prepareStatement(CekSql)){
          Check.setString(1,acc)  ;        
            ResultSet rs = Check.executeQuery();
            while(!rs.next()){
            return false;
            
            }
            while(rs.next()){
            return true;
            }
      }
     return decision ;
  }
 
  public static boolean CheckUsername(Connection connection,String uname)throws Exception{
      boolean decision=true ;
      String CekSql = "SELECT * from User WHERE UserName = ? ";  
      try (PreparedStatement Check = connection.prepareStatement(CekSql)){
          Check.setString(1,uname)  ; //uname = check usernamee       
            ResultSet rs = Check.executeQuery();
            while(!rs.next()){
            return false;
            
            }
            while(rs.next()){
            return true;
            }
      }
     return decision ;
  }
  public static boolean CheckName(Connection connection,String nm)throws Exception{
      boolean decision=true ;
      String CheckSql = "SELECT * from User WHERE Name = ? ";  
      try (PreparedStatement Check = connection.prepareStatement(CheckSql)){
          Check.setString(1,nm)  ; //nm= check name       
            ResultSet rs = Check.executeQuery();
            while(!rs.next()){
            return false;            
            }
            while(rs.next()){
            return true;
            }
      }
     return decision ;
  }
  
    public static double CheckBal(Connection connection,String acc)throws Exception{
        String CalSql = "SELECT * from Account WHERE accountNumber = ? ";  
        double bal = 0;
        try (PreparedStatement registerCalculate = connection.prepareStatement(CalSql)){
          
            registerCalculate.setString(1,acc)  ;        
            ResultSet rs = registerCalculate.executeQuery();
                while(rs.next()){
                 bal = rs.getInt("balance"); 
                }
             }
        return bal;
    }
    
    
    
           private static int ID;
           private static String in;
           private static String out;
           private static double amt;
           private static String bank;
           private static String date;          
           private static String nmIn;
           private static String nmOut;
           public static String blank=null;
    public static void tableAcc_out (Connection connection,String loginAcc,DefaultTableModel tableHistory)throws Exception{
        String cs = "SELECT * from Transaction WHERE acc_out = ? "; 
        
          BankFunction BF = new BankFunction();
        try (PreparedStatement us = connection.prepareStatement(cs)) {
            us.setString(1,loginAcc) ; 
            ResultSet rs = us.executeQuery();
            while(rs.next()){
             ID =rs.getInt("transactionID");
             in=rs.getString("acc_in");
             nmIn=BF.UIDconvertName(connection,in);
            System.out.println(nmIn);
             amt=rs.getDouble("amount");
             bank=rs.getString("bank");
             date=rs.getString("transactionTime");
             
             Object row[]={ID,in,nmIn,bank,blank,amt,date};
            System.out.println("Transaction ID:"+ID+"\nName:"+nmIn+"\nAcc_in:"+in+"\nAmount:"+amt+"\nDate:"+date);
            tableHistory.addRow(row);
            }
        }    
    }
    public static void tableAcc_in(Connection connection,String loginAcc,DefaultTableModel tableHistory)throws Exception{
        String cs = "SELECT * from Transaction WHERE acc_in = ? ";  
       
        BankFunction BF = new BankFunction();
        try (PreparedStatement us = connection.prepareStatement(cs ) ) {
            us.setString(1,loginAcc) ; 
            ResultSet rs = us.executeQuery();
            while(rs.next()){
             ID =rs.getInt("transactionID");
             out=rs.getString("acc_out");
             nmOut=BF.UIDconvertName(connection,out);
            System.out.println(nmOut);
             amt=rs.getDouble("amount");
             bank=rs.getString( "bank");
             date=rs.getString("transactionTime");
            
             Object row[]={ID,out,nmOut,bank,amt,blank,date};
            System.out.println("Transaction ID:"+ID+"\nName:"+nmOut+"\nAcc_in:"+out+"\nAmount:"+amt+"\nDate:"+date);
            tableHistory.addRow(row);
            }
        }    
    }
   public static String UIDconvertName (Connection connection,String id)throws Exception{
       String CalSql = "SELECT * from User WHERE accountNumber = ? ";  
        String nm = null;
        try (PreparedStatement registerCalculate = connection.prepareStatement(CalSql)){
          
            registerCalculate.setString(1,id)  ;        
            ResultSet rs = registerCalculate.executeQuery();
                while(rs.next()){
                 nm=rs.getString("Name"); 
                
                }
             }
        return nm;
    }
   
public static String UNconvertID (Connection connection,String Un)throws Exception{
       String CalSql = "SELECT * from User WHERE UserName = ? ";  
        String ID=null;
        try (PreparedStatement registerCalculate = connection.prepareStatement(CalSql)){
          
            registerCalculate.setString(1,Un)  ;        
            ResultSet rs = registerCalculate.executeQuery();
                while(rs.next()){
                 ID=rs.getString("accountNumber"); 
                
                }
             }
        return ID;
    }

    //used in TransanctionMenu 
    //insert the latest data into databse
    public static void History(Connection connection,String acc,String loginAcc,double amount,String bankName)throws Exception{ 
        String htySql ="INSERT INTO Transaction( acc_in, acc_out,amount,bank,transactionTime) values(?,?,?,?,?)";  
        
        LocalDateTime DateNTimeNow = LocalDateTime.now();
        DateTimeFormatter DateTimeF=DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss a");
        String DateTimeSQL = DateTimeF.format(DateNTimeNow);
            try(PreparedStatement registerHistory = connection.prepareStatement(htySql)){
                    registerHistory.setString(1,acc);
                    registerHistory.setString(2,loginAcc);
                    registerHistory.setDouble(3,amount);
                    registerHistory.setString(4,bankName);
                    registerHistory.setString(5,DateTimeSQL);
                        registerHistory.execute();
            }
    }

//update the latest amount to the database 
public static void Update(Connection connection,String acc,double total)throws Exception{
            String sqlUsAcc = "UPDATE Account SET balance = ? WHERE accountNumber = ? ";
    
            PreparedStatement usAcc = connection.prepareStatement(sqlUsAcc);
            usAcc.setDouble(1, total); 
            usAcc.setString(2, acc);  
                                  
            usAcc.executeUpdate();
           
    }
public static void LoginCheckRole(String user,String pw, String rl)throws Exception{
        V v=new V();
        Sound s = new Sound();
        LoginMenu loginMenu = new LoginMenu();
        v.setRole(rl);
                        v.setName(user);
                        s.LoginSound();
                        if("Admin".equals(rl)){
                        OptionMenu am = new OptionMenu();
                        am.setVisible(true);
                        loginMenu.dispose();                    
                        }else if("User".equals(rl)){
                        OptionMenu am = new OptionMenu();
                        am.setVisible(true);
                        loginMenu.dispose();  
                        }else{
                        s.ErrorSound();
                        JOptionPane.showMessageDialog(loginMenu,"Login error! Please contact the authorities!");  
                        }
}

public static String decimalFormat(double num){
        DecimalFormat df = new DecimalFormat("#,###.00");
        String DFnum = df.format(num);
        return DFnum;
}
}   
class V{
    private static String name; //get user name and change automatically
    private static String Role; // get the role of the user and change different layout
        public static String getName(){
        return name;
     }
        public static void setName(String n){
        name=n;
     }
        public static String getRole(){
        return Role;
     }
        public static void setRole(String a){
       Role=a;
     }
        
}
class Sound{
public static void LoginSound() {
        try {
            File soundFile = new File("build/classes/music/mixkit-select-click-1109.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e){
                System.err.println("Got an exception!");
              
                e.printStackTrace();
                
                System.out.println(e); 
        }      
    }
public static void SuccessSound() {
        try {
            File soundFile = new File("build/classes/music/mixkit-select-click-1109.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e){
                System.err.println("Got an exception!");
              
                e.printStackTrace();
                
                System.out.println(e); 
        }      
    }
public static void ErrorSound() {
     try {
            File soundFile = new File("build/classes/music/error-4-199275.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e){
                System.err.println("Got an exception!");
              
                e.printStackTrace();
                
                System.out.println(e); 
        }      
    }
public static void OptionSound(){
    try {
            File soundFile = new File("build/classes/music/mixkit-cool-interface-click-tone-2568.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(soundFile);
            Clip clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e){
                System.err.println("Got an exception!");
              
                e.printStackTrace();
                
                System.out.println(e); 
        }      
    }
}