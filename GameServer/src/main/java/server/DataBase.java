package server;

import javafx.application.Platform;
import javafx.scene.control.ListView;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.util.HashMap;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Никита on 29.03.2017.
 */
public class DataBase {

    Logger logger = Logger.getLogger(this.getClass().getName());

    private Connection connection;
    private ListView<String> serverView;

    private DataBase(String user, String password){
        try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = null;
            try {
                Locale.setDefault(Locale.ENGLISH);
                connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "system", "root");
                //Statement statement = connection.createStatement();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {

        }
    }

    public static DataBase getInstance(){
        return new DataBase("system", "root");
    }

    public void setServerView(ListView<String> serverView){
        this.serverView = serverView;
    }

    public void printInfo(){
        if (connection != null){
            serverView.getItems().add(ServerConstants.SUCCESSFULL_CONNECTION_TO_DB);
        }
        else{
            serverView.getItems().add(ServerConstants.FAILED_CONNECTION_TO_DB);
        }
    }

    public String getUserName(String userID){
        String userName = "";

        try {
            String query = findQuery("GameServer/src/scripts/find_user_by_id.sql");

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.valueOf(userID));

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                userName = resultSet.getString("user_name");
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            logger.log(Level.INFO, "Can't find query");
        }

        if ("".equals(userName)){
            userName = "This user doesn't exist";
        }
        return userName;
    }

    private String findQuery(String fileName) throws IOException{
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line = null;
        StringBuilder builder = new StringBuilder();
        String ls = System.getProperty("line.separator");

        try{
            while((line = reader.readLine()) != null){
                builder.append(line);
                builder.append(ls);
            }

            return builder.toString();

        } finally {
            reader.close();
        }
    }

    public HashMap<String, Integer> findUserStatistic(String userID){
        HashMap<String, Integer> users = new HashMap<String, Integer>();

        try {
            String query = findQuery("GameServer/src/scripts/find_user_statistic.sql");

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, Integer.valueOf(userID));

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                String userName = resultSet.getString("user_name");
                Integer userScore = resultSet.getInt("user_score");
                users.put(userName, userScore);
            }

            resultSet.close();
            preparedStatement.close();

        } catch (IOException e) {
            logger.log(Level.INFO, "Can't find query");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return users;
    }
}
