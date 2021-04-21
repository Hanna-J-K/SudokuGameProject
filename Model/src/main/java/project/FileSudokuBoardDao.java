package project;


import project.custexceptions.FilesException;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.ref.Cleaner;

public class FileSudokuBoardDao implements Dao<SudokuBoard> {

    private String fileName;

    public FileSudokuBoardDao(String fileName) {
        this.fileName = fileName;
    }

    @Override
    public SudokuBoard read() throws FilesException {
        SudokuBoard obj = null;

        // try-with-resources
        try (FileInputStream fileInputStream = new FileInputStream(fileName);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
                obj = (SudokuBoard) objectInputStream.readObject();
        } catch (ClassNotFoundException | IOException ex) {
            throw new FilesException(ex); // nie ma nigdzie close bo autocloseable
        }
        return obj;
    }

    @Override
    public void write(SudokuBoard obj) throws FilesException {
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileName);
             ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream)) {
                objectOutputStream.writeObject(obj);
        } catch (IOException ex) {
            throw new FilesException(ex);
        }
    }

    // finalize, ale jest deprecated
    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }

    @Override
    public void close() throws Exception {
    }
}
