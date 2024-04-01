class Song {
    private String title;
    private int score;

    public Song(String title) {
        this.title = title;
        this.score = 0;
    }

    public String getTitle() {
        return title;
    }

    public int getScore() {
        return score;
    }

    public void incrementScore() {
        score++;
    }
}
