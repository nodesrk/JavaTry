package controller.servlets;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import controller.DatabaseController;
import model.StudentModel;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        PrintWriter printOut = response.getWriter();
        response.setContentType("text/html");
        printOut.print("Hello World!");
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TODO Auto-generated method stub
        response.setContentType("text/html");
        PrintWriter printOut = response.getWriter();

        String username = request.getParameter("username");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String email = request.getParameter("email");
        String dob = request.getParameter("dob");
        String gender = request.getParameter("gender");
        String phone = request.getParameter("phone");
        String subject = request.getParameter("subject");
        String password = request.getParameter("password");

        // Creating a StudentModel object with the received data
        StudentModel student = new StudentModel(username, firstName, lastName, email, dob, gender, phone, subject, password);

        // Creating a DatabaseController object
        DatabaseController dbController = new DatabaseController();

        try {
            // Connecting to the database
            dbController.connect();

            // Saving the student information
            dbController.save(student);

            // Printing success message with CSS import
            printOut.println("<!DOCTYPE html>");
            printOut.println("<html lang=\"en\">");
            printOut.println("<head>");
            printOut.println("<meta charset=\"UTF-8\">");
            printOut.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            printOut.println("<title>Registration Success</title>");
            printOut.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/style.css\">");
            printOut.println("</head>");
            printOut.println("<body>");
            printOut.println("<div class='message success'>");
            printOut.println("<h1>Your account is registered</h1>");
            printOut.println("<h3>Username: " + username + "</h3>");
            printOut.println("<h3>Name: " + firstName + " " + lastName + "</h3>");
            printOut.println("<h3>Email: " + email + "</h3>");
            printOut.println("<h3>DOB: " + dob + "</h3>");
            printOut.println("<h3>Gender: " + gender + "</h3>");
            printOut.println("<h3>Phone: " + phone + "</h3>");
            printOut.println("<h3>Subject: " + subject + "</h3>");
            printOut.println("</div>");
            printOut.println("</body>");
            printOut.println("</html>");
        } catch (SQLException e) {
            // Printing error message if an SQL exception occurs
        	printOut.println("<!DOCTYPE html>");
        	printOut.println("<html lang=\"en\">");
        	printOut.println("<head>");
        	printOut.println("<meta charset=\"UTF-8\">");
        	printOut.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        	printOut.println("<title>Registration Error</title>");
        	printOut.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"styles/style.css\">"); // Adjusted path
        	printOut.println("</head>");
        	printOut.println("<body>");
        	printOut.println("<div class='message error'>");
        	printOut.println("<h1>Error occurred while registering</h1>");
        	printOut.println("<p>Please try again later.</p>");
        	printOut.println("</div>");    
        	printOut.println("</body>");
        	printOut.println("</html>");
            e.printStackTrace();
        } finally {
            try {
                // Disconnecting from the database
                dbController.disconnect();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
