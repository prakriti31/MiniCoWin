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
import java.time.LocalDate;

public class BookSlot extends Application {
    private Label dbResultLbl;
    private String phnnum;
    private String cfd;
    private String age;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        primaryStage.setTitle("Book Slot");
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label headerLabel = new Label("Book Slot for Vaccination");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        //Labels
        Label phNumLabel = new Label("Phone Number");
        Label ageLabel = new Label("Age Category");
        Label cfdLabel = new Label("Check for Dates");
        dbResultLbl = new Label();

        //Input fields
        TextField phNumField = new TextField();
        //RadioButtons
        ToggleGroup tg = new ToggleGroup();
        RadioButton first = new RadioButton("60 and above");
        RadioButton second = new RadioButton("40 to 60");
        RadioButton third = new RadioButton("18 to 40");
        first.setToggleGroup(tg);
        second.setToggleGroup(tg);
        third.setToggleGroup(tg);
        //Date Picker
        DatePicker cfdField = new DatePicker();
        //GridPane adjustments
        gridPane.addRow(2, ageLabel);
        gridPane.addRow(2, first);
        gridPane.add(second, 1, 3, 1, 1);
        gridPane.add(third,1,4,1,1);
        gridPane.addRow(5, phNumLabel,phNumField);
        gridPane.addRow(6, cfdLabel, cfdField);

        Button submitButton = new Button("Register");
        submitButton.setDefaultButton(true);
        submitButton.setPrefWidth(100);
        gridPane.add(submitButton, 0, 8, 2, 1);
        GridPane.setHalignment(submitButton, HPos.CENTER);
        GridPane.setMargin(submitButton, new Insets(20, 0, 20, 0));

        submitButton.setOnAction(actionEvent -> {
            age = ((RadioButton)tg.getSelectedToggle()).getText();
            cfd = cfdField.getValue().toString();
            phnnum = phNumField.getText();
            addDataToDatabase();
                try {
                    new Main().start(primaryStage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            System.out.println("You can go to any nearby centre and get vaccinated.\nThank you for your support in the vaccination drive.");
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

            String insertQuery = "insert into Slots (age, phnnum, cfd) values (?,?,?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertQuery);
            // prepare all data before insert it
            preparedStatement.setString(1, age);
            preparedStatement.setString(2, phnnum);
            preparedStatement.setString(3, cfd);
            // return 0 if not insert it Or 1 if inserted
            int sqlResult = preparedStatement.executeUpdate();
            preparedStatement.close();
            if (sqlResult == 1) {
                //Emptying all fields after insertion
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