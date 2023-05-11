
package controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import oracle.jdbc.datasource.impl.OracleDataSource;
import software.Product;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HomePage {

    int managerid;

    @FXML
    public BorderPane HomePagePane;


    @FXML
    public Pane HomePane;
    @FXML
    public Label WorkerCountLabel ;

    @FXML
    public Label carpetsWorkerCountLabel;

    @FXML
    public Label coverWorkerContLabel;

    @FXML
    public Label customerCountLabel;

    @FXML
    public PieChart personChart;

    @FXML
    public PieChart productChart;

    @FXML
    public Label carpetProductCountLabel;

    @FXML
    public Label coverProductCountLabel;

    @FXML
    public Label moneyForCarpetLabel;

    @FXML
    public Label moneyForCoverLabels;



    @FXML
    private void backToLogin (ActionEvent event) throws IOException {
        Alert alert = new Alert (Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("You are about to logout?");
        if(alert.showAndWait().get() == ButtonType.OK )
        {
            Stage stage = (Stage) HomePagePane.getScene().getWindow();
            stage.close();
            Parent root = FXMLLoader.load(getClass().getResource("/login.fxml"));
            stage.setTitle("Cleaning Services");
            stage.setScene(new Scene(root, 770, 561));
            stage.show();


        }
    }
    public  String getFromDatabse(String s) throws SQLException {
        oracle.jdbc.datasource.impl.OracleDataSource orc = new OracleDataSource();
        orc.setURL("jdbc:oracle:thin:@localhost:1521:orcl");
        orc.setUser("software");
        orc.setPassword("123123");
        Connection conn = orc.getConnection();
        Statement stm = conn.createStatement();
        ResultSet rs = stm.executeQuery(s);
        String m ="";
        while(rs.next()){
            m = (rs.getInt(1)+"");
        }

        return m;
    }




    @FXML
    void centerHomePage(ActionEvent event) throws Exception {

        HomePagePane.setCenter(HomePane);
        String numOfWorker = "select count(ID) from worker";
        String numOfCustomer = "select count(ID) from customer";
        String numOfCarpetsWorker = "select count(ID) from worker where SPECIALIZATION='carpet'";
        String numOfCoverWorker = "select count(ID) from worker where SPECIALIZATION='cover'";

        String numOfCarpetProduct = "select count(ID) from product where CATEGORY = 'carpet'";
        String numOfCoverProduct = "select count(ID) from product where CATEGORY = 'cover'";
        String allMoneyForCarpet = "select sum(totalPrice) from Product where CATEGORY = 'carpet'";
        String allMoneyForCover = "select sum(totalPrice) from Product where CATEGORY = 'cover'";

        WorkerCountLabel.setText(getFromDatabse(numOfWorker));
        customerCountLabel.setText(getFromDatabse(numOfCustomer));
        carpetsWorkerCountLabel.setText(getFromDatabse(numOfCarpetsWorker));
        coverWorkerContLabel.setText(getFromDatabse(numOfCoverWorker));

        carpetProductCountLabel.setText(getFromDatabse(numOfCarpetProduct));
        coverProductCountLabel.setText(getFromDatabse(numOfCoverProduct));
        moneyForCarpetLabel.setText(getFromDatabse(allMoneyForCarpet));
        moneyForCoverLabels.setText(getFromDatabse(allMoneyForCover));


        ObservableList<PieChart.Data> personChartData = FXCollections.observableArrayList(
                new PieChart.Data("All Worker", Double.parseDouble(getFromDatabse(numOfWorker))),
                new PieChart.Data("All Customer",Double.parseDouble(getFromDatabse(numOfCustomer))),
                new PieChart.Data("Carpet Worker", Double.parseDouble(getFromDatabse(numOfCarpetsWorker))),
                new PieChart.Data("Cover Worker", Double.parseDouble(getFromDatabse(numOfCoverWorker))));

        personChart.setData(personChartData);
        personChart.setTitle("Persone Chart");
        personChart.setVisible(true);


        ObservableList<PieChart.Data> productChartData = FXCollections.observableArrayList(
                new PieChart.Data("Carpet Product", Double.parseDouble(getFromDatabse(numOfCarpetProduct))),
                new PieChart.Data("Cover Product",Double.parseDouble(getFromDatabse(numOfCoverProduct))),
                new PieChart.Data("Carpet Money", Double.parseDouble(getFromDatabse(allMoneyForCarpet))),
                new PieChart.Data("Cover Money", Double.parseDouble(getFromDatabse(allMoneyForCover))));

        productChart.setData(productChartData);
        productChart.setTitle("Product Chart");
        productChart.setVisible(true);


    }
    @FXML
    void Add(ActionEvent event) throws IOException {
        loadpage("/AddAll");
    }

    @FXML
    void ViewAll(ActionEvent event) throws IOException {
        loadpage("/ViewAll");
    }
    @FXML
    void openSendEmailPane(ActionEvent event) throws IOException {
        loadpage("/SendEmail");
    }


    private void loadpage (String page) throws IOException {
        Parent root = null ;
        root = FXMLLoader.load(getClass().getResource(page+".fxml"));
        HomePagePane.setCenter(root);
    }





}

