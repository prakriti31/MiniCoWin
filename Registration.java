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

import javax.swing.*;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class Registration extends Application {

    //UI Views
    private TextField nameField, emailField, adNumField;
    private PasswordField passwordField;
    private DatePicker DOBField;
    private Date date;
    private String DOB;
    private String fullName;
    private String gender;
    private String password;
    private String email;
    private String adNum;
    private Label dbResultLbl;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Registration Form");
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label headerLabel = new Label("Registration Form");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        //Labels
        Label nameLabel = new Label("Full Name");
        Label passLabel = new Label("Password");
        Label genLabel = new Label("Gender");
        Label adNumLabel = new Label("Aadhaar Card Number");
        Label emailLabel = new Label("Email");
        Label DOBLabel = new Label("DOB");
        dbResultLbl = new Label();

        //Input fields
        nameField = new TextField();
        emailField = new TextField();
        adNumField = new TextField();
        passwordField = new PasswordField();
        //RadioButtons
        ToggleGroup tg = new ToggleGroup();
        RadioButton male = new RadioButton("Male");
        RadioButton female = new RadioButton("Female");
        RadioButton others = new RadioButton("Others");
        female.setToggleGroup(tg);
        male.setToggleGroup(tg);
        others.setToggleGroup(tg);
        //Date Picker
        DOBField = new DatePicker();
        //GridPane adjustments
        gridPane.addRow(1, nameLabel, nameField);
        gridPane.addRow(2, genLabel);
        gridPane.addRow(2, male);
        gridPane.add(female, 1, 3, 1, 1);
        gridPane.add(others, 1, 4, 1, 1);
        gridPane.addRow(5, emailLabel, emailField);
        gridPane.addRow(6, passLabel, passwordField);
        gridPane.addRow(7, adNumLabel, adNumField);
        gridPane.addRow(8, DOBLabel, DOBField);
        gridPane.addRow(11, dbResultLbl);

        Button submitButton = new Button("Register");
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 10, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        submitButton.setOnAction(actionEvent -> {
            fullName = nameField.getText();
            gender = ((RadioButton) tg.getSelectedToggle()).getText();
            email = emailField.getText();
            date = new Date(System.currentTimeMillis());
            System.out.println("Date of registration : " + date);
            adNum = adNumField.getText();
            password = passwordField.getText();
            DOB = DOBField.getValue().toString();
            addDataToDatabase();
        });
        Scene scene = new Scene(gridPane, 900, 700);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void addDataToDatabase() {
        try {
            //DB Connection
            String connectionUrl = "jdbc:mysql://localhost:3306/DA3";
            Connection dbConnection = DriverManager.getConnection(connectionUrl, "root", "root");
            // create a statement object to send to database
            String insertQuery = "insert into Register (fullName,gender,email,adNum,password,DOB,date) values (?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertQuery);
            // prepare all data before insert it
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, gender);
            preparedStatement.setString(3, email);
            preparedStatement.setString(4, adNum);
            preparedStatement.setString(5, password);
            preparedStatement.setString(6, DOB);
            preparedStatement.setDate(7, date);
            // return 0 if not insert it Or 1 if inserted
            int sqlResult = preparedStatement.executeUpdate();
            preparedStatement.close();
            if (sqlResult == 1) {
                //Emptying all fields after insertion
                nameField.clear();
                emailField.clear();
                adNumField.clear();
                passwordField.clear();
                DOBField.setValue(null);
                //gender.equals("");
                dbResultLbl.setTextFill(Color.GREEN);
                dbResultLbl.setText("Record successfully inserted.\n");
                System.out.println("Database Updated!");
            }
        } catch (Exception e) {
            dbResultLbl.setTextFill(Color.RED);
            dbResultLbl.setText("Error: " + e.getMessage());
        }
    }
}