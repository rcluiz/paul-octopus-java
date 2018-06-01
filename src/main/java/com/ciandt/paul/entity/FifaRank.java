package com.ciandt.paul.entity;

import java.util.List;

import org.apache.commons.csv.CSVRecord;

/**
 * Fifa rank for a specific year
 */
public class FifaRank {

    private Integer rank;
    private String teamCode;
    private String teamName;
    private Integer points;

    /**
     * Constructor
     */
    public FifaRank(List<String> row) {
        this.setRank(Integer.valueOf(row.get(0)));
        this.setTeamCode(row.get(1));
        this.setTeamName(row.get(2));
        this.setPoints(Integer.parseInt(row.get(3)));
    }

    /**
     * Constructor
     */
    public FifaRank(CSVRecord record) {
        this.setRank(Integer.valueOf(record.get(0)));
        this.setTeamCode(record.get(1));
        this.setTeamName(record.get(2));
        this.setPoints(Integer.parseInt(record.get(3)));
    }

    public Integer getRank() {
        return rank;
    }

    public String getTeamCode() {
        return teamCode;
    }

    public String getTeamName() {
        return teamName;
    }

    public Integer getPoints() {
        return points;
    }

    private void setRank(Integer rank) {
        this.rank = rank;
    }

    private void setTeamCode(String teamCode) {
        this.teamCode = teamCode;
    }

    private void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    private void setPoints(Integer points) {
        this.points = points;
    }

    @Override
    public String toString() {
        return "FifaRank{" +
                "rank=" + rank +
                ", teamCode='" + teamCode + '\'' +
                ", teamName='" + teamName + '\'' +
                ", points=" + points +
                '}';
    }
}
