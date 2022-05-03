package pl.marcinycz.paper_stone_scissors;

public enum ChoicePSS {
    PAPER(0),
    STONE(1),
    SCISSORS(2);

    public int value;

    private ChoicePSS(int value) {
        this.value = value;
    }
}
