package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "asmit882003@";

     public static void main(String[] args) {
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
         Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            Patient patient = new Patient(connection,scanner);
            Doctor doctor = new Doctor(connection);
            Staff staff = new Staff(connection);
            while(true){
                System.out.println("HOSPITAL MANAGEMENT SYSTEM");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. View Staffs");
                System.out.println("5. Book Appointment");
                System.out.println("6. Exit");
                System.out.println("Enter Choice: ");
                int choice = scanner.nextInt();
                switch (choice){
                    case 1:
                        patient.addPatient();
                        System.out.println();
                        break;
                    case 2:
                        patient.viewPatients();
                        System.out.println();
                        break;
                    case 3:
                        doctor.viewDoctors();
                        System.out.println();
                        break;
                    case 4:
                        staff.viewStaff();
                        System.out.println();
                        break;
                    case 5:
                        appointment(patient,doctor,connection,scanner);
                        System.out.println();
                        break;
                    case 6:
                        System.out.println("THANK YOU!!");
                        return;
                    default:
                        System.out.println("Enter Valid Choice!!");
                        break;
                }
            }
        }catch (SQLException e){
                e.printStackTrace();
        }
    }
    public static void appointment(Patient patient,Doctor doctor,Connection connection,Scanner scanner){
         System.out.print("Enter Patient Id: ");
         int patient_id = scanner.nextInt();
         System.out.print("Enter Doctor Id: ");
         int doctor_id = scanner.nextInt();
         System.out.print("Enter Appointment Date(YYYY-MM-DD)");
         String appointment_date = scanner.next();
         if(patient.getPatientById(patient_id) && doctor.getDoctorById(doctor_id)){
             if(checkDoctorAvail(doctor_id,appointment_date,connection)){
                String query = "INSERT INTO appointments(patient_id,doctor_id,appointment_date) VALUES(?,?,?)";
                 try{
                     PreparedStatement preparedStatement = connection.prepareStatement(query);
                     preparedStatement.setInt(1,patient_id);
                     preparedStatement.setInt(2,doctor_id);
                     preparedStatement.setString(3,appointment_date);
                     int rowsAffected = preparedStatement.executeUpdate();
                     if(rowsAffected>0){
                         System.out.println("Appointment Booked");
                     } else {
                         System.out.println("Failed to book Appointment");
                     }
                 } catch (SQLException e){
                     e.printStackTrace();
                 }

             } else {
                 System.out.println("Doctor is not Avail in this date!!");
             }
         } else {
             System.out.println("Either doctor or patient do not exist!!");
         }
    }
    public static boolean checkDoctorAvail(int doctorid,String appointmentdate,Connection connection){
         String query = "SELECT COUNT(*) FROM appointments WHERE doctor_id = ? and appointment_date = ?";
         try {
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             preparedStatement.setInt(1,doctorid);
             preparedStatement.setString(2,appointmentdate);
             ResultSet resultSet = preparedStatement.executeQuery();
             if(resultSet.next()){
                 int count = resultSet.getInt(1);
                 if(count == 0){
                     return true;
                 } else {
                     return false;
                 }
             }
         } catch (SQLException e){
             e.printStackTrace();
         }
         return false;
    }
}
