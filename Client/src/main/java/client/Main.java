package client;

import client.listeners.LogInListener;
import client.listeners.RegistrationListener;
import client.messager.Data;
import javafx.animation.*;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import client.listeners.QuantityListener;

import javax.swing.*;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage)throws Exception {
        Pane root = new Pane();
        Pane main_root = new Pane();
        String fxmlFile = "/fxml/ClientForm.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent rootForNewGame = loader.load(getClass().getResourceAsStream(fxmlFile));
        ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/images/fon.jpg")));
        img.setFitHeight(600);
        img.setFitWidth(800);
        root.getChildren().add(img);

        ImageView main_img = new ImageView(new Image(getClass().getResourceAsStream("/images/fon.jpg")));
        main_img.setFitHeight(600);
        main_img.setFitWidth(800);
        main_root.getChildren().add(main_img);

        MenuItem newGame = new MenuItem("NEW GAME");
        MenuItem options = new MenuItem("OPTIONS");
        MenuItem exitGame = new MenuItem("EXIT");
        SubMenu mainMenu = new SubMenu(newGame, options, exitGame);

        MenuItem registration = new MenuItem("JOIN NOW");
        MenuItem login = new MenuItem("LOGIN");
        SubMenu logPage = new SubMenu(registration, login);

        MenuItem sound = new MenuItem("MUSIC");
        MenuItem optionsBack = new MenuItem("BACK");
        SubMenu optionsMenu = new SubMenu(sound, optionsBack);

        MenuBox menuBox_1 = new MenuBox(logPage);
        root.getChildren().add(menuBox_1);
        Scene scene = new Scene(root,800,600);
        menuBox_1.setVisible(true);

        MenuBox menuBox_2 = new MenuBox(mainMenu);
        main_root.getChildren().addAll(menuBox_2);

        Scene main_scene = new Scene(main_root,800,600);
        menuBox_2.setVisible(true);

        newGame.setOnMouseClicked((event) -> {
            Data.quantity = QuantityListener.sendRequest();
            QuantityListener.printMap();
            primaryStage.setTitle("Black Race");
            primaryStage.setScene(new Scene(rootForNewGame, 800, 600));
            primaryStage.show();
        });
        /*
        newGame.setOnMouseClicked((event) -> {
            primaryStage.setTitle("Black Race");
            primaryStage.setScene(new Scene(rootForNewGame, 800, 600));
            primaryStage.show();
        });
*/
        registration.setOnMouseClicked((event) -> {
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));

            Text scenetitle = new Text("Welcome");
            scenetitle.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
            grid.add(scenetitle, 0, 0, 2, 1);

            Label userName = new Label("User Name:");
            grid.add(userName, 0, 1);

            TextField userTextField = new TextField();
            grid.add(userTextField, 1, 1);

            Label pw = new Label("Password:");
            grid.add(pw, 0, 2);

            PasswordField pwBox = new PasswordField();
            grid.add(pwBox, 1, 2);

            Label pw_repeat = new Label("Password:");
            grid.add(pw_repeat, 0, 3);

            PasswordField pwBox2 = new PasswordField();
            grid.add(pwBox2, 1, 3);

            Button button = new Button("Join");
            button.setAlignment(Pos.CENTER);
            button.setPrefWidth(60);
            button.setOnMouseClicked(event1->{
                registration(userTextField.getText(), pwBox.getText());
                primaryStage.setScene(scene);
            });
            grid.add(button, 1, 4);

            Button button_back = new Button("Back");
            button_back.setAlignment(Pos.CENTER);
            button_back.setPrefWidth(60);
            button_back.setOnMouseClicked(event1->primaryStage.setScene(scene));
            grid.add(button_back, 2, 4);

            Scene scene2 = new Scene(grid, 800, 600);
            primaryStage.setScene(scene2);
        });




        login.setOnMouseClicked((event) -> {
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(25, 25, 25, 25));

            Text scenetitle = new Text("Welcome");
            scenetitle.setFont(Font.font("Arial", FontWeight.NORMAL, 20));
            grid.add(scenetitle, 0, 0, 2, 1);

            Label userName = new Label("User Name:");
            grid.add(userName, 0, 1);

            TextField userTextField = new TextField();
            grid.add(userTextField, 1, 1);

            Label pw = new Label("Password:");
            grid.add(pw, 0, 2);

            PasswordField pwBox = new PasswordField();
            grid.add(pwBox, 1, 2);

            Button button = new Button("Sign in");
            button.setAlignment(Pos.CENTER);
            button.setPrefWidth(60);
            button.setOnMouseClicked(event1->{
                boolean flag = LogInListener.checkUser(userTextField.getText(), pwBox.getText());
                if (flag) {
                    Data.setName(userTextField.getText());
                    primaryStage.setScene(main_scene);
                }
                else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password!","Error",JOptionPane.ERROR_MESSAGE);
                    //TODO alert
                }
            });
            grid.add(button, 1, 4);

            Button button_back = new Button("Back");
            button_back.setAlignment(Pos.CENTER);
            button_back.setPrefWidth(60);
            button_back.setOnMouseClicked(event1->primaryStage.setScene(scene));
            grid.add(button_back, 2, 4);

            Scene scene2 = new Scene(grid, 800, 600);
            primaryStage.setScene(scene2);
        });

        options.setOnMouseClicked(event->menuBox_2.setSubMenu(optionsMenu));
        exitGame.setOnMouseClicked(event-> System.exit(0));
        optionsBack.setOnMouseClicked(event->menuBox_2.setSubMenu(mainMenu));

        primaryStage.setTitle("Black Race");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void registration(String userName, String password) {
        RegistrationListener.sendRequestForRegistrate(userName, password);
    }

    private static class MenuItem extends StackPane{
        public  MenuItem(String name){
            Rectangle bg = new Rectangle(200,20,Color.WHITE);
            bg.setOpacity(0.5);

            Text text = new Text(name);
            text.setFill(Color.WHITE);
            text.setFont(Font.font("Arial",FontWeight.BOLD,14));

            setAlignment(Pos.CENTER);
            getChildren().addAll(bg,text);
            FillTransition st = new FillTransition(Duration.seconds(0.3),bg);
            setOnMouseEntered(event -> {
                st.setFromValue(Color.DARKGRAY);
                st.setToValue(Color.DARKGOLDENROD);
                st.setCycleCount(Animation.INDEFINITE);
                st.setAutoReverse(true);
                st.play();
            });
            setOnMouseExited(event -> {
                st.stop();
                bg.setFill(Color.WHITE);
            });
        }
    }
    private static class MenuBox extends Pane{
        static SubMenu subMenu;
        public MenuBox(SubMenu subMenu){
            MenuBox.subMenu = subMenu;
            setVisible(false);
            Rectangle bg = new Rectangle(800,600,Color.LIGHTBLUE);
            bg.setOpacity(0.4);
            getChildren().addAll(bg, subMenu);
        }
        public void setSubMenu(SubMenu subMenu){
            getChildren().remove(MenuBox.subMenu);
            MenuBox.subMenu = subMenu;
            getChildren().add(MenuBox.subMenu);
        }
    }

    private static class SubMenu extends VBox{
        public SubMenu(MenuItem...items){
            setSpacing(15);
            setTranslateY(100);
            setTranslateX(300);
            for(MenuItem item : items){
                getChildren().addAll(item);
            }
        }
    }



    public static void main(String[] args){
        launch(Main.class);
    }
}