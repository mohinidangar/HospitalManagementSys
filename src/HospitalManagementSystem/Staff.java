package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Staff{
    private Connection connection;

    public Staff(Connection connection) {
        this.connection = connection;
    }

    public void viewStaff() {
        String query = "select * from Staff";

        try {
            PreparedStatement preparedStatement = this.connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Staff: ");
            System.out.println("+------------+--------------------+---------+--------------+----------+");
            System.out.println("| Staff Id   | Name               | Age     | Role         | Salary   |");
            System.out.println("+------------+--------------------+---------+--------------+----------+");

            while(resultSet.next()) {
                int id = resultSet.getInt("staff_id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                String role = resultSet.getString("role");
                int salary = resultSet.getInt("salary");
                System.out.printf("| %-10s | %-18s | %-7s | %-12s | %-8s |\n", id, name,age,role,salary);
                System.out.println("+------------+--------------------+---------+--------------+----------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
