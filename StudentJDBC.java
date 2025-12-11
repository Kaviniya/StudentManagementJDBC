import java.sql.*;
import java.util.*;

public class StudentJDBC {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String url = "jdbc:mysql://localhost:3306/student_db";
        String user = "root";       
        String pass = "kavi@123";       

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, user, pass);

            while (true) {
                System.out.println("\n===== Student Management System =====");
                System.out.println("1. Add Student");
                System.out.println("2. View All Students");
                System.out.println("3. Search Student");
                System.out.println("4. Update Student");
                System.out.println("5. Delete Student");
                System.out.println("6. Exit");
                System.out.print("Choose: ");

                int choice = sc.nextInt();
                sc.nextLine();

                switch (choice) {

                    case 1:
                        System.out.print("Enter ID: ");
                        int id = sc.nextInt();
                        sc.nextLine();

                        System.out.print("Enter Name: ");
                        String name = sc.nextLine();

                        System.out.print("Enter Grade: ");
                        String grade = sc.nextLine();

                        PreparedStatement ps1 = con.prepareStatement("INSERT INTO students VALUES (?,?,?)");
                        ps1.setInt(1, id);
                        ps1.setString(2, name);
                        ps1.setString(3, grade);
                        ps1.executeUpdate();

                        System.out.println("✔ Student Added!");
                        break;

                    case 2:
                        Statement st = con.createStatement();
                        ResultSet rs = st.executeQuery("SELECT * FROM students");

                        System.out.println("\n--- All Students ---");
                        while (rs.next()) {
                            System.out.println(rs.getInt(1) + " | " + rs.getString(2) + " | " + rs.getString(3));
                        }
                        break;

                    case 3:
                        System.out.print("Enter ID to Search: ");
                        int sid = sc.nextInt();

                        PreparedStatement ps2 = con.prepareStatement("SELECT * FROM students WHERE id=?");
                        ps2.setInt(1, sid);

                        ResultSet rs2 = ps2.executeQuery();
                        if (rs2.next()) {
                            System.out.println("ID: " + rs2.getInt(1));
                            System.out.println("Name: " + rs2.getString(2));
                            System.out.println("Grade: " + rs2.getString(3));
                        } else {
                            System.out.println("No student found!");
                        }
                        break;

                    case 4:
                        System.out.print("Enter ID to Update: ");
                        int uid = sc.nextInt();
                        sc.nextLine();

                        System.out.print("New Name: ");
                        String newName = sc.nextLine();

                        System.out.print("New Grade: ");
                        String newGrade = sc.nextLine();

                        PreparedStatement ps3 = con.prepareStatement("UPDATE students SET name=?, grade=? WHERE id=?");
                        ps3.setString(1, newName);
                        ps3.setString(2, newGrade);
                        ps3.setInt(3, uid);

                        int updated = ps3.executeUpdate();
                        System.out.println(updated > 0 ? "✔ Updated!" : "❌ ID Not Found");
                        break;

                    case 5:
                        System.out.print("Enter ID to Delete: ");
                        int did = sc.nextInt();

                        PreparedStatement ps4 = con.prepareStatement("DELETE FROM students WHERE id=?");
                        ps4.setInt(1, did);

                        int deleted = ps4.executeUpdate();
                        System.out.println(deleted > 0 ? "✔ Deleted!" : "❌ ID Not Found");
                        break;

                    case 6:
                        con.close();
                        System.out.println("Goodbye!");
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }
            }

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
