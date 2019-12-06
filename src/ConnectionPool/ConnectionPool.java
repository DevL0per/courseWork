package ConnectionPool;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class ConnectionPool {

    public static ConnectionPool shared = new ConnectionPool();

    private List<Connection> connections = new ArrayList<>(NUMBER_OF_CONNECTION);
    private Queue<Connection> priorityQueue = new PriorityQueue<>(Comparator.comparingInt(Object::hashCode));

    private static final int NUMBER_OF_CONNECTION = 5;

    private ConnectionPool() {

        String userName = "root";
        String password = "roma1586100";
        String connectionURL = "jdbc:mysql://localhost:3306/University";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception exp) {
            exp.printStackTrace();
        }

        for(int number = 0; number <= NUMBER_OF_CONNECTION; number++) {
            try {
                Connection connection = DriverManager.getConnection(connectionURL, userName, password);
                connections.add(connection);
                priorityQueue.add(connection);
            } catch (SQLException exp) {
                exp.printStackTrace();
            }
        }
    }

    public synchronized Connection getConnection() {
        return priorityQueue.remove();
    }

    public synchronized void returnConnection(Connection connection) {
        priorityQueue.add(connection);
    }
}