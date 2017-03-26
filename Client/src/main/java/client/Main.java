package client;

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

public class Main extends Application {
    @Override
    public void start(Stage primaryStage)throws Exception {
        Pane root = new Pane();
        String fxmlFile = "/fxml/ClientForm.fxml";
        FXMLLoader loader = new FXMLLoader();
        Parent rootForNewGame = loader.load(getClass().getResourceAsStream(fxmlFile));
        ImageView img = new ImageView(new Image(getClass().getResourceAsStream("/images/fon.jpg")));
        img.setFitHeight(600);
        img.setFitWidth(800);
        root.getChildren().add(img);

        MenuItem newGame = new MenuItem("NEW GAME");
        MenuItem connection = new MenuItem("CONNECT TO SERVER");
        MenuItem options = new MenuItem("OPTIONS");
        MenuItem exitGame = new MenuItem("EXIT");
        SubMenu mainMenu = new SubMenu(newGame, connection, options, exitGame);

        MenuItem sound = new MenuItem("MUSIC");
        MenuItem optionsBack = new MenuItem("BACK");
        SubMenu optionsMenu = new SubMenu(sound, optionsBack);

        MenuBox menuBox = new MenuBox(mainMenu);


       newGame.setOnMouseClicked((event) -> {
            primaryStage.setTitle("Black Race");
            primaryStage.setScene(new Scene(rootForNewGame, 800, 600));
            primaryStage.show();
        });

        options.setOnMouseClicked(event->menuBox.setSubMenu(optionsMenu));
        exitGame.setOnMouseClicked(event-> System.exit(0));
        optionsBack.setOnMouseClicked(event->menuBox.setSubMenu(mainMenu));

        root.getChildren().addAll(menuBox);

        Scene scene = new Scene(root,800,600);
        menuBox.setVisible(true);

        primaryStage.setTitle("Black Race");
        primaryStage.setScene(scene);
        primaryStage.show();
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
            Rectangle bg = new Rectangle(900,600,Color.LIGHTBLUE);
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