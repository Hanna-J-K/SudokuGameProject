package project;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.Serializable;

// klasa observable
public class SudokuField implements Serializable, Cloneable, Comparable<SudokuField> {

    private transient IntegerProperty value = new SimpleIntegerProperty();
    private PropertyChangeSupport support;

    public SudokuField(int value) {
        support = new PropertyChangeSupport(this);
        this.value.setValue(value);
        this.value.addListener(((observableValue, number, t1) ->
                support.firePropertyChange("value", number, t1)));
    }

    public int getFieldValue() {
        return this.value.get();
    }

    public IntegerProperty getFieldProperty() { return this.value; }

    public void setFieldValue(int value) {
        support.firePropertyChange("value", this.value, value);
        this.value.setValue(value);
    }

    public void addPropertyChangeListener(PropertyChangeListener sudokuFieldListener) {
        support.addPropertyChangeListener(sudokuFieldListener);
    }

    /*//to na później innym listenerem?
    public void setOldValue(int value) {
        this.value = value;
    }*/

    // ZADANIE 6


    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        SudokuField that = (SudokuField) object;
        return value.get() == that.value.get();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("value", value.get())
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value.get());
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compareTo(SudokuField object) {

        return this.getFieldValue() - object.getFieldValue();
    }
}
