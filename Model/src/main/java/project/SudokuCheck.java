package project;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class SudokuCheck implements Cloneable {
    private List<SudokuField> field;

    public SudokuCheck(List<SudokuField> field) {
        this.field = field;
    }

    public boolean verify() {
        Set<Integer> correctSet = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            if (field.get(i).getFieldValue() != 0) {
                if (!correctSet.add(field.get(i).getFieldValue())) {
                    return false;
                }
            }
        }
        return true;
    }

    // ZADANIE 6

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        SudokuCheck that = (SudokuCheck) object;
        return Objects.equal(field, that.field);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("field", field)
                .toString();
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(field);
    }


    protected Object clone() throws CloneNotSupportedException {
        return (SudokuCheck)super.clone();
    }
}

