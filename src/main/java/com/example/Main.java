package com.example;
import java.sql.*;
public class Main {
    static final String DB_HOST = "localhost";
    static final String DB_NAME = "sys2";
    static final String DB_USER = "test";
    static final String DB_PASS = "1234";
    private static Connection connection = getConnection();
    public static void main(String[] args) {
        if(args[0].equals("readconnections")){
            displayConnections(Integer.parseInt(args[1]));
        } else if(args[0].equals("newconnection")) {
            insertNewConnection(args[1]);
        }
    }
    public static Connection getConnection(){
        if(connection == null){
            try{
                String url = "jdbc:postgresql://"+DB_HOST+":5432/"+DB_NAME;
                connection = DriverManager.getConnection(
                        url,
                        DB_USER,
                        DB_PASS
                );
            }catch (SQLException e){
                e.printStackTrace();
            }
        }
        return connection;
    }
    public static void insertNewConnection(String name){
        Connection myConnection = getConnection();
        try{
            String sql = "INSERT INTO \"connection\" (firstname) VALUES (?)";
            PreparedStatement statement = myConnection.prepareStatement(sql);
            statement.setString(1, name);
            int rowCount = statement.executeUpdate();
            System.out.println("Vous avez cree : "+rowCount+" nouvelle connexion");
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
    public static void displayConnections(int number){
        Connection myConnection = getConnection();
        try{
            String sql = "SELECT * FROM \"connection\" ORDER BY connection_datetime DESC LIMIT ?";
            PreparedStatement statement = myConnection.prepareStatement(sql);
            System.out.println("-----Liste des dernieres connexions-----");
            statement.setInt(1, number);
            ResultSet results = statement.executeQuery();
            while(results.next()){
                System.out.println(
                        results.getString("id")+" - "+
                                results.getString("firstname")+", Derniere connexion: "+
                                results.getTimestamp("connection_datetime")
                );
            }
            statement.close();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
