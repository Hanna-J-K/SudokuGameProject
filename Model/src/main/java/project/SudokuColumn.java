package project;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.util.List;

public class SudokuColumn extends SudokuCheck {
    public SudokuColumn(List<SudokuField> column) {
        super(column);
    }


}
