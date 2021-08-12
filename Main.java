package sample;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {

    String[] args;

    public static void main(String[] args) {
        args = args;
        launch(args);

        //For running individual pages
        //Registration.launch(Registration.class,args);
        //Login.launch(Login.class,args);
        //BookSlot.launch(BookSlot.class, args);
        //AdminLogin.launch(AdminLogin.class,args);
        //AdminPanel.launch(AdminPanel.class,args);
        //VaccinatedStats.launch(VaccinatedStats.class, args);
        //RegisteredStats.launch(RegisteredStats.class,args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Home Page");
        GridPane gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setPadding(new Insets(40, 40, 40, 40));
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        Label headerLabel = new Label("MiNiCoWin");
        headerLabel.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        gridPane.add(headerLabel, 0, 0, 2, 1);
        GridPane.setHalignment(headerLabel, HPos.CENTER);
        GridPane.setMargin(headerLabel, new Insets(20, 0, 20, 0));

        Button registerButton = new Button("Register");
        registerButton.setDefaultButton(true);
        registerButton.setPrefWidth(200);
        gridPane.add(registerButton, 0, 2, 2, 1);
        GridPane.setHalignment(registerButton, HPos.CENTER);
        GridPane.setMargin(registerButton, new Insets(20, 0, 20, 0));

        Button loginButton = new Button("Login");
        loginButton.setDefaultButton(true);
        loginButton.setPrefWidth(200);
        gridPane.add(loginButton, 0, 4, 2, 1);
        GridPane.setHalignment(loginButton, HPos.CENTER);
        GridPane.setMargin(loginButton, new Insets(20, 0, 20, 0));

        Button adminButton = new Button("Admin");
        adminButton.setDefaultButton(true);
        adminButton.setPrefWidth(200);
        gridPane.add(adminButton, 0, 6, 2, 1);
        GridPane.setHalignment(adminButton, HPos.CENTER);
        GridPane.setMargin(adminButton, new Insets(20, 0, 20, 0));

        Button VaccButton = new Button("Vaccinated Stats");
        VaccButton.setDefaultButton(true);
        VaccButton.setPrefWidth(200);
        gridPane.add(VaccButton, 0, 8, 2, 1);
        GridPane.setHalignment(VaccButton, HPos.CENTER);
        GridPane.setMargin(VaccButton, new Insets(20, 0, 20, 0));

        Button RegButton = new Button("Registered Stats");
        RegButton.setDefaultButton(true);
        RegButton.setPrefWidth(200);
        gridPane.add(RegButton, 0, 10, 2, 1);
        GridPane.setHalignment(RegButton, HPos.CENTER);
        GridPane.setMargin(RegButton, new Insets(20, 0, 20, 0));

        registerButton.setOnAction(actionEvent -> {
            try {
                new Registration().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        loginButton.setOnAction(actionEvent -> {
            try {
                new Login().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        adminButton.setOnAction(actionEvent -> {
            try {
                new AdminLogin().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        VaccButton.setOnAction(actionEvent -> {
            try {
                new VaccinatedStats().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        RegButton.setOnAction(actionEvent -> {
            try {
                new RegisteredStats().start(primaryStage);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        Scene scene = new Scene(gridPane, 900, 700);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
