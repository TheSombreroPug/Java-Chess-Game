package Chess;


public enum CGS {
    INPLAY("", ContinueStatus.CONTINUE),
    CHECK("CurrentlyInCheck", ContinueStatus.WARN),
    WW("WhiteWins", ContinueStatus.END),
    BW("BlackWins", ContinueStatus.END),
    Draw("NoneWins", ContinueStatus.END),

    TOOMANYMOVES("Too many moves", ContinueStatus.END),
    TOOMANYREP("Repeat clause", ContinueStatus.END);

    public enum ContinueStatus {
        CONTINUE,
        WARN,
        END
    }

    private final String text;
    private final ContinueStatus continueStatus;

    CGS(String text, ContinueStatus continueStatus) {
        this.text = text;
        this.continueStatus = continueStatus;
    }

    public String textCode() {
        return text;
    }
    public boolean inCheck(){
        return continueStatus != ContinueStatus.CONTINUE;}

    public boolean GameOver(){
        return continueStatus == continueStatus.END;
    }
}
