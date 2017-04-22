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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            logger.log(Level.INFO, "Can't find driver");
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

    public Integer getUserScore(String userName){
        Integer result = -1;
        try {
            String query = findQuery("GameServer/src/scripts/find_user_by_id.sql");

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                result = resultSet.getInt("user_score");
            }

            resultSet.close();
            preparedStatement.close();

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            logger.log(Level.INFO, "Can't find query");
        }
        return result;
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

    public void resetDataBase(){
        try {
            String queryForDrop = findQuery("GameServer/src/scripts/drop_table.sql");
            Statement statementForDrop = connection.createStatement();
            statementForDrop.execute(queryForDrop);
            statementForDrop.close();

            String queryForCreate = findQuery("GameServer/src/scripts/create_users_table.sql");
            Statement statementForCreate = connection.createStatement();
            statementForCreate.execute(queryForCreate);
            statementForCreate.close();

            resetSeq();

            insertUser("admin".toUpperCase(), "black".toUpperCase());

            serverView.getItems().add("DataBase has been reset");
        } catch (IOException e) {
            logger.log(Level.INFO, "Can't find query");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean checkUser(String name, String password){
        boolean result = false;
        try {
            String query = findQuery("GameServer/src/scripts/check_user_password.sql");

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, name);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                result = resultSet.getBoolean("value");
            }
        } catch (IOException e) {
            logger.log(Level.INFO, "Can't find query");
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    public void insertUser(String userName, String password){
        try {
            String query = findQuery("GameServer/src/scripts/insert_new_user.sql");

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            statement.setString(2, password);
            statement.setInt(3, 0);

            statement.execute();
            statement.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void resetSeq(){
        try {
            String query = findQuery("GameServer/src/scripts/drop_sequence.sql");

            Statement statement = connection.createStatement();
            statement.execute(query);
            statement.close();

            query = findQuery("GameServer/src/scripts/create_sequence.sql");
            statement = connection.createStatement();
            statement.execute(query);
            statement.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUserScore(String userName, Integer score){
        try {
            String query = findQuery("GameServer/src/scripts/update_user_score.sql");

            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, score);
            statement.setString(2, userName);

            statement.executeUpdate();
            statement.close();

        } catch (IOException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
