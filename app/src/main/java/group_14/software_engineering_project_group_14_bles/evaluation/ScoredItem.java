package group_14.software_engineering_project_group_14_bles.evaluation;

/**
 * Created by Jerry on 11/22/2015.
 */
public class ScoredItem {

    private double score;
    private String category;

    public ScoredItem(double score, String category) {
        this.score = score;
        this.category = category;
    }

    public double getScore() {
        return this.score;
    }

    public String getCategory() {
        return this.category;
    }
}
