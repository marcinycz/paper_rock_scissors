package pl.marcinycz.paper_rock_scissors;

public enum ChoicePRS {
    PAPER(0),
    ROCK(1),
    SCISSORS(2);

    public int value;

    private ChoicePRS(int value) {
        this.value = value;
    }
}
