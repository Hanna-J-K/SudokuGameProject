package project;

import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import java.util.List;

public class SudokuBox extends SudokuCheck {
    public SudokuBox(List<SudokuField> box) {
        super(box);
    }

}
