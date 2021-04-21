package project;

import project.custexceptions.DBException;

public class SudokuBoardDaoFactory {

    public Dao<SudokuBoard> getFileDao(String fileName) {
        return new FileSudokuBoardDao(fileName);
    }

    public static Dao<SudokuBoard> getSudokuJDBC(String filename) throws DBException {
            return new SudokuJDBC(filename);
    }

}
