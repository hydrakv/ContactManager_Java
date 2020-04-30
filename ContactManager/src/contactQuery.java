
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author varun
 */
public class contactQuery {
    
    
    public void insertContact(contact cont)
    {
      
        Connection con = myConneection.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("INSERT INTO `mycontacts`(`fname`, `lname`, `groupc`, `phone`, `email`, `address`, `pic`, `userid`) VALUES (?,?,?,?,?,?,?,?)");
            ps.setString(1, cont.getFname());
            ps.setString(2, cont.getLname());
            ps.setString(3, cont.getGroupc());
            ps.setString(4, cont.getPhone());
            ps.setString(5, cont.getEmail());
            ps.setString(6, cont.getAddress());
            ps.setBytes(7,cont.getPic() );
            ps.setInt(8, cont.getUid());
            
            
            if(ps.executeUpdate() !=0)
            {
                JOptionPane.showMessageDialog(null,"New Contact Added");
               
            }else
            {
                JOptionPane.showMessageDialog(null,"Something went Wrong");
               
            }
            
                    } catch (SQLException ex) {
            Logger.getLogger(contactQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
    
    
    public void updateContact(contact cont,boolean withImage)
    {
      
        Connection con = myConneection.getConnection();
        PreparedStatement ps;
        String updateQuery = "";
        
        if(withImage == true) // if the user wants to update contact pic
        {
            updateQuery = "UPDATE `mycontacts` SET `fname`=?,`lname`=?,`groupc`=?,`phone`=?,`email`=?,`address`=?,`pic`=? WHERE `id`=?";
            try {
            ps = con.prepareStatement(updateQuery);
            ps.setString(1, cont.getFname());
            ps.setString(2, cont.getLname());
            ps.setString(3, cont.getGroupc());
            ps.setString(4, cont.getPhone());
            ps.setString(5, cont.getEmail());
            ps.setString(6, cont.getAddress());
            ps.setBytes(7,cont.getPic() );
            ps.setInt(8, cont.getCid());
            
            
            if(ps.executeUpdate() !=0)
            {
                JOptionPane.showMessageDialog(null,"Contact Data Updated");
               
            }else
            {
                JOptionPane.showMessageDialog(null,"Something went Wrong");
               
            }
            
                    } catch (SQLException ex) {
            Logger.getLogger(contactQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        }
        else{//the user wants tokeep the same image [remove the updated image from the update]
            
           updateQuery = "UPDATE `mycontacts` SET `fname`=?,`lname`=?,`groupc`=?,`phone`=?,`email`=?,`address`=? WHERE `id`=?";
           try {
            ps = con.prepareStatement(updateQuery);
            ps.setString(1, cont.getFname());
            ps.setString(2, cont.getLname());
            ps.setString(3, cont.getGroupc());
            ps.setString(4, cont.getPhone());
            ps.setString(5, cont.getEmail());
            ps.setString(6, cont.getAddress());
            ps.setInt(7, cont.getCid());
            
            
            if(ps.executeUpdate() !=0)
            {
                JOptionPane.showMessageDialog(null,"Contact Data Updated");
               
            }else
            {
                JOptionPane.showMessageDialog(null,"Something went Wrong");
               
            }
            
                    } catch (SQLException ex) {
            Logger.getLogger(contactQuery.class.getName()).log(Level.SEVERE, null, ex);
        }    
    }
}
  
     public void deleteContact(int cid)
    {
      
        Connection con = myConneection.getConnection();
        PreparedStatement ps;
        
        try {
            ps = con.prepareStatement("DELETE FROM `mycontacts` WHERE `id`=?");
           ps.setInt(1, cid);
            
            
            if(ps.executeUpdate() !=0)
            {
                JOptionPane.showMessageDialog(null,"Contact Deleted");
               
            }else
            {
                JOptionPane.showMessageDialog(null,"Somethin went Wrong");
               
            }
            
                    } catch (SQLException ex) {
            Logger.getLogger(contactQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
     
    }
    
//search 
////     public void searchContact(String s)
////     {
////         Connection con = myConneection.getConnection();
////         PreparedStatement ps;
////         ResultSet rs;
////         try{
////         ps = con.prepareStatement("select * from users where id =?");
////         rs = ps.executeQuery();
////         }catch(Exception ex)
////         {
////             JOptionPane.showMessageDialog(null, ex.getMessage());
////         }
////         return rs;
////     }
    
    
    
    
    

//create a list of contact
    public ArrayList<contact> contactList(int userId){
        
        ArrayList<contact> clist = new ArrayList<contact>();
        
        Connection con = myConneection.getConnection();
        Statement st;
        ResultSet rs;
        
        try {
            st = con.createStatement();
            
            rs = st.executeQuery("SELECT `id`, `fname`, `lname`, `groupc`, `phone`, `email`, `address`, `pic` FROM `mycontacts` WHERE userid= "+userId);
        
            contact ct;
            
            while(rs.next())
            {
                ct = new contact(rs.getInt("id"),
                                rs.getString("fname"),
                                rs.getString("lname"),
                                rs.getString("groupc"),
                                rs.getString("phone"),
                                rs.getString("email"),
                                rs.getString("address"),
                                rs.getBytes("pic"),
                                userId);
                
                clist.add(ct);
            }
        
        
        } catch (SQLException ex) {
            Logger.getLogger(contactQuery.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
                
        return clist;
    }


}

