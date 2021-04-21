package project;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import project.custexceptions.DBException;
import project.custexceptions.DaoException;
import project.custexceptions.FilesException;

import java.io.IOException;
import java.io.Serializable;
import java.sql.*;
import java.util.Locale;
import java.util.ResourceBundle;

public class SudokuJDBC implements Dao<SudokuBoard>, AutoCloseable, Serializable {

    private final static String USER = "postgres";
    private final static String PASSWORD = "postgres";
    private final static String URL = "jdbc:postgresql://localhost:5432/postgres";
    private final static String DBDRIVER = "org.postgresql.Driver";

    public static final Logger logger = LoggerFactory.getLogger(SudokuJDBC.class);
    static Locale locale = new Locale("en", "UK");
    static ResourceBundle exceptBundle = ResourceBundle.getBundle("Exception", locale);

    private String name;

    private Connection connection = null;
    Statement statement = null;



    public  SudokuJDBC(String name) {

        this.name = name;

        try {

            Class.forName(DBDRIVER);
            connection = DriverManager.getConnection(URL, USER, PASSWORD);

            connection.setAutoCommit(false);

            createTable();
            logger.debug(exceptBundle.getString("dbconnected"));

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
            logger.debug(exceptBundle.getString("dberror"));
        }

    }

    void createTable() {
        try {
            statement = connection.createStatement();

            String createSudokuTable = "CREATE TABLE IF NOT EXISTS SUDOKU(BOARD int PRIMARY KEY GENERATED ALWAYS AS " +
                    "IDENTITY, " + "BOARD_NAME varchar(50) UNIQUE NOT NULL)";

            statement.executeUpdate(createSudokuTable);
            logger.info(exceptBundle.getString("tablecr"));

            String createFieldsTable = "CREATE TABLE IF NOT EXISTS SUDOKUFIELDS(VAL int NOT NULL, "
                    + "SUROW int NOT NULL, SUCOL int NOT NULL, BOARD int, FOREIGN KEY (BOARD) "
                    + "REFERENCES SUDOKU (BOARD) ON UPDATE CASCADE ON DELETE CASCADE )";

            statement.executeUpdate(createFieldsTable);
            logger.debug(exceptBundle.getString("tablecr"));

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public SudokuBoard read() throws FilesException {

        SudokuBoard board = new SudokuBoard(new BacktrackingSudokuSolver());

        ResultSet resultSet;
        ResultSet resultSet1;

        PreparedStatement preparedStatement;

        int param;

        int field, row, column;

        try {
            statement = connection.createStatement();

            String selectQuery = "SELECT SUDOKU.BOARD FROM SUDOKU WHERE SUDOKU.BOARD_NAME=?";
            preparedStatement = connection.prepareStatement(selectQuery);

            preparedStatement.setString(1, name);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                param = resultSet.getInt(1);
            } else {
                throw new IOException();
            }

            logger.debug(exceptBundle.getString("tableread"));


            String selectQuery2 = "SELECT SUDOKUFIELDS.VAL, SUDOKUFIELDS.SUROW," +
                    " SUDOKUFIELDS.SUCOL FROM SUDOKUFIELDS WHERE SUDOKUFIELDS.BOARD=?";
            preparedStatement = connection.prepareStatement(selectQuery2);
            preparedStatement.setInt(1, param);

            resultSet1 = preparedStatement.executeQuery();

            while (resultSet1.next()) {

                field = resultSet1.getInt(1);
                row = resultSet1.getInt(2);
                column = resultSet1.getInt(3);

                board.set(row, column, field);

            }

            logger.debug(exceptBundle.getString("tableread"));

            statement.close();
            connection.close();

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }


        return board;
    }

    @Override
    public void write(SudokuBoard obj) throws FilesException {

        ResultSet resultSet;
        PreparedStatement preparedStatement;

        int param;

        try {
            statement = connection.createStatement();
            String insertQuery = "INSERT INTO SUDOKU (BOARD_NAME) VALUES (?)";

            preparedStatement = connection.prepareStatement(insertQuery);
            preparedStatement.setString(1, name);

            preparedStatement.executeUpdate();

            logger.debug(exceptBundle.getString("tableinsert"));
            preparedStatement.close();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
            logger.debug(exceptBundle.getString("dberror"));
        }


        try {
            String selectQuery = "SELECT SUDOKU.BOARD FROM SUDOKU WHERE SUDOKU.BOARD_NAME=?";
            String insertFields = "INSERT INTO SUDOKUFIELDS VALUES(?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(selectQuery);
            preparedStatement.setString(1, name);

            resultSet = preparedStatement.executeQuery();


            if (resultSet.next()) {
                param = resultSet.getInt(1);
            } else {
                throw new IOException();
            }


            statement = connection.createStatement();
            preparedStatement = connection.prepareStatement(insertFields);

            preparedStatement.setInt(4, param);

            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    preparedStatement.setInt(1, obj.get(i,j));
                    preparedStatement.setInt(2, i);
                    preparedStatement.setInt(3, j);
                    preparedStatement.executeUpdate();
                }
            }

            logger.debug(exceptBundle.getString("tableinsert"));
            connection.commit();
//            statement.close();
//            preparedStatement.close();
//            connection.close();

        } catch (SQLException | IOException throwables) {
            throwables.printStackTrace();
        }


    }

    @Override
    public void close() throws Exception {
        try {
            statement.close();
            connection.close();
        } catch (SQLException e) {
            logger.error(exceptBundle.getString("dberror"));
        }
    }
}
