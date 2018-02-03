package mypack;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

import java.sql.DriverManager;

public class BookDB {
    private String dbUrl ="jdbc:mysql://localhost:3306/BookDB";
    private String dbUser="dbuser";
    private String dbPwd="1234";

    public BookDB() throws Exception {
        Class.forName("com.mysql.jdbc.Driver");
    }
    public Connection getConnection() throws Exception {
        return DriverManager.getConnection(dbUrl,dbUser,dbPwd);
    }
    public void closeConnection(Connection con) {
        try {
            if (con != null) {
                con.close();
            }

        }  catch(Exception e) {
            e.printStackTrace();
        } 
    }
    public void closeResutlSet(ResultSet rs){
        try {
            if (rs != null) {
                rs.close();
            }

        }  catch(Exception e) {
            e.printStackTrace();
        } 
    }
    public void closePrepStmt(PreparedStatement prepStmt){
        try {
            if (prepStmt != null) {
                prepStmt.close();
            }

        }  catch(Exception e) {
            e.printStackTrace();
        } 
    }
    public int getNumberOfBooks() throws Exception {
        Connection con = null;
        PreparedStatement prepStmt=null;
        ResultSet rs=null;
        int count=0;
        try{
            con =getConnection();
            String selectSql="select count(*) from BOOKS";
            prepStmt=con.prepareStatement(selectSql);
            rs=prepStmt.executeQuery();
            if (rs.next()){
                count=rs.getInt(1);
            }
        }
        finally{
            closeResutlSet(rs);
            closePrepStmt(prepStmt);
            closeConnection(con);
        }
        return count;
    }
    public Collection getBooks() throws Exception {
        Connection con =null;
        PreparedStatement prepStmt=null;
        ResultSet rs=null;
        ArrayList books = new ArrayList();
        try {
            con=getConnection();
            String selectSql = "select * from BOOKS";
            prepStmt = con.prepareStatement(selectSql);
            rs=prepStmt.executeQuery();
            while(rs.next()) {
                BookDetails bd=new BookDetails(rs.getString(1), rs.getString(2),
                rs.getString(3), rs.getFloat(4), rs.getInt(5), rs.getString(6),rs.getInt(7));
                books.add(bd);
            }            
        }finally{
            closeResutlSet(rs);
            closePrepStmt(prepStmt);
            closeConnection(con);
        }
        Collections.sort(books);
        return books;
    }
    public BookDetails getBookDetails(String bookId) throws Exception {
        Connection con =null;
        PreparedStatement prepStmt=null;
        ResultSet rs=null;
        try {
            con=getConnection();
            String selectSql = "select * from BOOKS where ID = ?";
            prepStmt = con.prepareStatement(selectSql);
            prepStmt.setString(1, bookId);
            rs=prepStmt.executeQuery();
            if (rs.next()) {
                BookDetails bd=new BookDetails(rs.getString(1), rs.getString(2),
                rs.getString(3), rs.getFloat(4), rs.getInt(5), rs.getString(6),rs.getInt(7));
                prepStmt.close();
                return bd;
            }        
            else {
                return null;
            }    
        }finally{
            closeResutlSet(rs);
            closePrepStmt(prepStmt);
            closeConnection(con);
        }
    }
    public void buyBooks(ShoppingCart cart) throws Exception {
        Connection con =null;
        Collection items=cart.getItems();
        Iterator it = items.iterator();

        try {
            con=getConnection();
            con.setAutoCommit(false);
            while(it.hasNext()){
                ShoppingCartItem sci=(ShoppingCartItem) it.next();
                BookDetails bd=(BookDetails)sci.getItem();
                String bookId = bd.getBookId();
                int quantity = sci.getQuantity();
                buyBook(bookId, quantity, con);
            }
            con.commit();
            con.setAutoCommit(true);

        }catch(Exception e ){
            con.rollback();
            throw e;
        }
        finally{
            closeConnection(con);
        }

    }
    public void buyBook(String bookId,int quantity,Connection con) throws Exception {
        PreparedStatement prepStmt=null;
        ResultSet rs=null;
        try {
            String selectSql = "select * from BOOKS where ID = ?";
            prepStmt = con.prepareStatement(selectSql);
            prepStmt.setString(1, bookId);
            rs=prepStmt.executeQuery();
            if (rs.next()) {
                prepStmt.close();
                String updateSql ="update BOOKS set SALE_AMOUNT = SALE_AMOUNT + ? where ID = ?";
                prepStmt = con.prepareStatement(updateSql);
                prepStmt.setInt(1,quantity);
                prepStmt.setString(2, bookId);
                prepStmt.executeUpdate();
                prepStmt.close();
            }    
        }finally{
            closeResutlSet(rs);
            closePrepStmt(prepStmt);
        }
    }
}
