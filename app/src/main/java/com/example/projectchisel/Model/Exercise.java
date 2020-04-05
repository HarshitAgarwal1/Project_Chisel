package com.example.projectchisel.Model;

public class Exercise {
    public String exercise;
    public String muscle;
    public String imgResource;
    public int reps;
    public int sets;


    public Exercise(String exercise, String muscle, String imgResource, int reps, int sets) {
        this.exercise = exercise;
        this.muscle = muscle;
        this.imgResource = imgResource;
        this.reps = reps;
        this.sets = sets;
    }

    public Exercise() {


    }

    @Override
    public String toString() {
        return "Exercise{" +
                "exercise='" + exercise + '\'' +
                ", muscle='" + muscle + '\'' +
                ", imgResource='" + imgResource + '\'' +
                ", reps=" + reps +
                ", sets=" + sets +
                '}';
    }

    public String getExercise() {
        return exercise;
    }

    public void setExercise(String exercise) {
        this.exercise = exercise;
    }

    public String getMuscle() {
        return muscle;
    }

    public void setMuscle(String muscle) {
        this.muscle = muscle;
    }

    public String getImgResource() {
        return imgResource;
    }

    public void setImgResource(String imgResource) {
        this.imgResource = imgResource;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }
}