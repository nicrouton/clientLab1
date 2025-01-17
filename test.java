import java.io.FileNotFoundException;
import java.sql.*;

/** CollegeDatabaseQuery.java
@author Nicholas Croulet
 Last edited on August 26th 2021 for CSCI 112 Section 901

 Program that queries a database and uses the result set to sort and write information about a college's comp sci courses
 to a CSV file. It also answers the question "Which CIS courses are available on Thursday?" and prints that answer to
 the console.

 */

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println("Connecting to the database...\n");

        // Connect to a database by establishing a Connection object
        Connection conn = DriverManager.getConnection("jdbc:mysql://160.153.75.195/CWHDemo", "CWHDemo", "Cwhdemo%123");

        System.out.println("Database connection established.\n");

        // Create a statement Object for this  database connection
        Statement st = conn.createStatement();

        // Call the method that queries the database for the course information/writes it to the csv file
        WriteCourse(st);

        // Call the method that answers the question
        CourseQuestion(st);

        // Close the connection
        conn.close();

    }



    // a method that queries the database, retrieving and writing in a CSV file the crn, subject, course, section, days
    // and time for all CSCI courses, in order according to the course number. Takes a statement object as a parameter
    // in order to connect to the database and send queries
    public static void WriteCourse(Statement s) throws SQLException, ClassNotFoundException, FileNotFoundException {

        String queryString;     // a String to hold an SQL query
        StringBuilder writeString = new StringBuilder();     // a Stringbuilder object to hold the result set and write to a new file
        ResultSet rs;      // the result set from an SQL query as a table

        // String variable that holds the query
        queryString = "SELECT crn, subject, course, section, days, time FROM fall2014 ORDER BY crn;";

        // Send the statement and store the result set
        rs = s.executeQuery(queryString);

        // print headings for the output
        System.out.println(queryString);
        System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%n", "crn", "subject", "course", "section", "days", "time");
        System.out.println("************************************************************************************************");

        // Iterate through the result set and print name, owner, and species attributes
        while (rs.next()) {
            writeString.append(rs.getString(1)).append(",").append(rs.getString(2)).append(",").append(rs.getString(3)).append(",").append(rs.getString(4)).append(",").append(rs.getString(5)).append(",").append(rs.getString(6)).append("\n");


            System.out.printf("%-20s%-20s%-20s%-20s%-20s%-20s%n", rs.getString(1), rs.getString(2),
                    rs.getString(3), rs.getString(4), rs.getString(5),
                    rs.getString(6));
        }   // end while result set
        System.out.println("************************************************************************************************");


        // write the String containing the data to the file
        // create a File class object
        java.io.File tut  = new java.io.File("courseResultSet.csv");
        // Create a PrintWriter text output stream and link it to the file x
        java.io.PrintWriter outfile  = new java.io.PrintWriter(tut);

        // Write the String
        outfile.print(writeString);

        outfile.close();
    }   // end WriteCourse




    // Method that queries the database to get the result set that answers the question "Which CIS courses are available
    // on Thursday?" and prints the answer to the console. Takes a statement object as a parameter in order to send
    // queries to the database
    public static void CourseQuestion(Statement s) throws SQLException {
        String questionString;     // a String to hold an SQL query
        ResultSet rsQuestion;      // the result set from an SQL query as a table

        // String variable that holds the query
        questionString = "SELECT subject, course, days, time FROM fall2014 WHERE days = 'TR' AND subject = 'CIS';";

        // Send the statement and store the result set
        rsQuestion = s.executeQuery(questionString);

        // Print the result set to the console with proper formatting
        System.out.println("");
        System.out.println(questionString);
        System.out.println("The query above answered the question: Which CIS courses are available on Thursday?");
        System.out.printf("%-20s%-20s%-20s%-20s%n", "subject", "course", "days", "time");
        System.out.println("************************************************************************************************");

        // Iterate through the result set and print name, owner, and species attributes
        while (rsQuestion.next()) {

            System.out.printf("%-20s%-20s%-20s%-20s%n", rsQuestion.getString(1), rsQuestion.getString(2),
                    rsQuestion.getString(3), rsQuestion.getString(4));
        }   // end while result set
        System.out.println("************************************************************************************************");

    }   // end CourseQuestion
}   // end Main class
