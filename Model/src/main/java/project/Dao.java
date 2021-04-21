package project;

import project.custexceptions.FilesException;

public interface Dao<T> extends AutoCloseable {
    T read() throws FilesException;

    void write(T obj) throws FilesException;
}
