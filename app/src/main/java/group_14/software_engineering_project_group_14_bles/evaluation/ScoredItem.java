package group_14.software_engineering_project_group_14_bles.evaluation;

import java.util.ArrayList;

import group_14.software_engineering_project_group_14_bles.Facility;

/**
 * Created by Jerry on 11/22/2015.
 */
public class ScoredItem {

    private double score;
    private String category;
    private ArrayList<Facility> enclosedFacilities;

    public ScoredItem(double score, String category) {
        this.score = score;
        this.category = category;
        this.enclosedFacilities = new ArrayList<>();
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScore() {
        return this.score;
    }

    public String getCategory() {
        return this.category;
    }

    public void addFacility(Facility facility) {
        this.enclosedFacilities.add(facility);
    }

    public ArrayList<Facility> getFacilities() {
        return this.enclosedFacilities;
    }
}
