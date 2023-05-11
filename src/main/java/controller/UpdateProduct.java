package controller;


import oracle.jdbc.datasource.impl.OracleDataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class UpdateProduct {
    String result;
     int flag = 0;

    String id;
    String owner;
    String category;
    String high;
    String width;
    public void updateProduct(String id) throws SQLException {
        OracleDataSource orc = new OracleDataSource();
        orc.setURL("jdbc:oracle:thin:@localhost:1521:orcl");
        orc.setUser("software");
        orc.setPassword("123123");
        Connection conn = orc.getConnection();
       try (Statement stm = conn.createStatement()) {
           String query = "update product set owner = '" + getOwner() +"' , category = '" + getCategory() +"' ,high =  '" + getHigh() +"', " +
                   "width = '" + getWidth() +"' where id = '" +id+"'";

           int t = stm.executeUpdate(query);
           if (t == 0)
               flag =1;
       }
       catch (SQLException ex) {
           Logger.getLogger("You are in update product page");
       }
    }
    public String getResult() {
        if (flag == 1)
            result = "Please check the ID you entered";
        else result = "Product Information Updated Successfully";

        return result;

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getHigh() {
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }
}
