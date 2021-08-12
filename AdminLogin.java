package sample;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class AdminLogin extends Application {
    //UI Views
    private String password;
    private String email;
    private Label dbResultLbl;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Admin Login");
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label headerLabel = new Label("Update the statistics");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        //Labels
        Label emailLabel = new Label("Email");
        Label passLabel = new Label("Password");
        Label infoLabel = new Label("Login to update.\nHelp Vaccinate.");

        //Input fields
        TextField emailField = new TextField();
        PasswordField passwordField = new PasswordField();

        //GridPane adjustments
        gridPane.addRow(3, emailLabel, emailField);
        gridPane.addRow(4,passLabel,passwordField);
        gridPane.addRow(1,infoLabel);


        //Submit Button
        Button submitButton = new Button("Login");
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 6, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        submitButton.setOnAction(actionEvent -> {
            //getData
            email = emailField.getText();
            password = passwordField.getText();
            checkDataFromDatabase(primaryStage);
        });
        Scene scene = new Scene(gridPane, 900, 700);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void checkDataFromDatabase(Stage primaryStage) {
        try {
            //DB Connection
            String connectionUrl = "jdbc:mysql://localhost:3306/DA3";
            Connection dbConnection = DriverManager.getConnection(connectionUrl, "root", "root");
            // create a statement object to send to database
            String insertQuery = "select * from admin where email=? and password=?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertQuery);
            // prepare all data before insert it
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);
            // return 0 if not insert it Or 1 if inserted
            ResultSet sqlResult = preparedStatement.executeQuery();
            boolean status = sqlResult.next();
//            System.out.println(sqlResult.next() + " " + sqlResult.next());
            preparedStatement.close();
            if (status) {
                try {
                    new AdminPanel().start(primaryStage);
                    System.out.println(email + " " +"logged in as an Admin!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            dbResultLbl.setTextFill(Color.RED);
            dbResultLbl.setText("Error: " + e.getMessage());
        }
    }
}