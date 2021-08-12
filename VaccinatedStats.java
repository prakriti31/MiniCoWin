package sample;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.sql.*;

public class VaccinatedStats extends Application {

    Label dbResultLbl;
    Date date;
    GridPane table;

    public static void main(String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Vaccinated Statistics");
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label headerLabel = new Label("Statistics: ");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.LEFT);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        DatePicker datePicker = new DatePicker();
        dbResultLbl = new Label();

        datePicker.setOnAction(actionEvent -> {
            date = Date.valueOf(datePicker.getValue());
            VaccinatedDatabase();
        });

        table = new GridPane();
        table.addRow(0, new Label("Full Name           "),
                new Label("Aadhaar Num                "),
                new Label("Phone Num            "),
                new Label("Age Category             "),
                new Label("Date of Vacc.          "));

        gridPane.addRow(1, datePicker);
        gridPane.addRow(2, table);

        Scene scene = new Scene(gridPane, 900, 700);

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void VaccinatedDatabase() {
        try {
            //DB Connection
            String connectionUrl = "jdbc:mysql://localhost:3306/DA3";
            Connection dbConnection = DriverManager.getConnection(connectionUrl, "root", "root");
            // create a statement object to send to database
            String selectQuery = "select * from Vaccinated where date=?";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(selectQuery);
            // prepare all data before insertion
            preparedStatement.setDate(1, date);
            // return 0 if not insert it Or 1 if inserted
            ResultSet sqlResult = preparedStatement.executeQuery();
            boolean status = sqlResult.next();

            int count = 1;
            //getData
            while (sqlResult.next()) {
                Text a = new Text(sqlResult.getString("fullName"));
                Text b = new Text(sqlResult.getString("adNum"));
                Text c = new Text(sqlResult.getString("phnnum"));
                Text d = new Text(sqlResult.getString("age"));
                Text e = new Text(sqlResult.getString("date"));

                table.addRow(count++, a, b, c, d, e);
            }
            preparedStatement.close();
            if (status) {
                dbResultLbl.setTextFill(Color.GREEN);
                dbResultLbl.setText("Record successfully inserted.\n");
                System.out.println("Find the information!");
            }
        } catch (Exception e) {
            dbResultLbl.setTextFill(Color.RED);
            dbResultLbl.setText("Error: " + e.getMessage());
        }
    }

}