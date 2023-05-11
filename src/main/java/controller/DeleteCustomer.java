package controller;

import oracle.jdbc.datasource.impl.OracleDataSource;
import java.sql.Connection;
import java.sql.Statement;
import java.util.logging.Logger;

public class DeleteCustomer {
    
    String result;
    int flag = 0;
    public int sqlValue( String query){
        int x = 0;
        try{
            OracleDataSource orc = new OracleDataSource();
            orc.setURL("jdbc:oracle:thin:@localhost:1521:orcl");
            orc.setUser("software");
            orc.setPassword("123123");
            Connection conn = orc.getConnection();
            try (Statement stm = conn.createStatement()) {
                x = stm.executeUpdate(query);
            }
        }catch(SQLException ex){
            Logger.getLogger(ex.toString());
        }
        return x;

    }
    public void deleteCustomerFunc(String id , String name){
        try{
            String query = "Delete from Customer where id = '" + id+ "' or name = '" + name +"'";
            int x = sqlValue(query);
            if (x > 0)
                flag = 1;
            else
                flag = 0;

        }catch (Exception ex){
            Logger.getLogger("You are in delete customer");
        }


    }
    public String getResult() {
        if (flag == 1)
            result = "Customer Deleted Successfully";
        else
            result = "please fill correct information";
        return result;
    }
}
