package controller.servlets;

import controller.DatabaseController;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.StudentModel;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private DatabaseController databaseController;

    @Override
    public void init() throws ServletException {
        super.init();
        databaseController = new DatabaseController();
        try {
            databaseController.connect(); // Ensure connection is established during initialization
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle connection error
        }
    }

    @Override
    public void destroy() {
        super.destroy();
        try {
            databaseController.disconnect(); // Close connection during servlet shutdown
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle disconnection error
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        int result = databaseController.getStudentLoginInfo(username, password);

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<title>Login Result</title>");
        out.println("</head>");
        out.println("<body>");

        if (result == 1) {
            out.println("<h1>Login Successful!</h1>");
        } else if (result == 0) {
            out.println("<h1>Login Failed! Invalid username or password.</h1>");
        } else {
            out.println("<h1>Error occurred while processing your request.</h1>");
        }

        out.println("</body>");
        out.println("</html>");
    }
}
