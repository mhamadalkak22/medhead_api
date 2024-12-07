package com.medhead.medhead.entities;

public class Recommandation implements Comparable {

    private double distance;
    private Hopital hopital;

    public Hopital getHopital() {
        return hopital;
    }

    public void setHopital(Hopital hopital) {
        this.hopital = hopital;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    @Override
    public int compareTo(Object o) {

        var otherRec = (Recommandation) o;

        if (this.distance < otherRec.distance) {
            return -1;
        } else if (this.distance > otherRec.distance) {
            return 1;
        }

        return 0;

    }

}
