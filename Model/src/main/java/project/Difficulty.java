package project;

public enum Difficulty {
    EASY(18),
    MEDIUM(36),
    HARD(48);

    private int pole;

    Difficulty(int pole) {
        this.pole = pole;
    }
}
