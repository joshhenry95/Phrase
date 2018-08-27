package joshuahenry.phrase;

public class Player {

    public static int score;
    public static int normalGameHighScore;
    public static int timeAttackHighScore;
    public static int timeAttackNumPhrasesHighScore;

    public static void addToScore(int n) {
        score += n;
    }

    public static void subtractFromScore(int n) {
        score -= n;

        if (score < 0)
            score = 0;

    }

    public static void setNormalGameHighScore() {
        if (score > normalGameHighScore) {
            normalGameHighScore = score;
        }
    }

    public static void setTimeAttackHighScore() {
        if (score > timeAttackHighScore) {
            timeAttackHighScore = score;
        }
    }

    public static void setTimeAttackNumPhrasesHighScore(int numPhrasesCompleted) {
        if (numPhrasesCompleted > timeAttackNumPhrasesHighScore) {
            timeAttackNumPhrasesHighScore = numPhrasesCompleted;
        }
    }

    public static void resetScore() {
        score = 0;
    }

}
