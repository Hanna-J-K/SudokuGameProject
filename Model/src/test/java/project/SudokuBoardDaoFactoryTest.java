package project;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SudokuBoardDaoFactoryTest {

    @Test
    public void getFileDaoTest() {

        SudokuBoardDaoFactory factory = new SudokuBoardDaoFactory();

        assertNotNull(factory.getFileDao("Game 2"));
    }

}