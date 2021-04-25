package com.abbasmoharreri.computingactions.model;

public class BonusAndFine {

    public final static String FLAG_PENALTY = "Penalty";
    public final static String FLAG_REWARD = "Reward";

    private String flag;
    private int humanPoint;
    private int minBonusPoint = 5;
    private int maxFinePoint = -5;
    private String condition;
    private String reward;
    private String penalty;

    private int id;


    public BonusAndFine() {

    }

    public BonusAndFine(int humanPoint, String condition) {
        this.humanPoint = humanPoint;
        this.condition = condition;
        setFlag();
    }

    public int getHumanPoint() {
        return humanPoint;
    }


    //when using this method , automatically set Flags for this class

    public BonusAndFine setHumanPoint(int humanPoint) {
        this.humanPoint = humanPoint;
        setFlag();
        return this;
    }

    public int getMinBonusPoint() {
        return minBonusPoint;
    }

    public BonusAndFine setMinBonusPoint(int minPoint) {
        this.minBonusPoint = minPoint;
        return this;
    }

    public int getMaxFinePoint() {
        return maxFinePoint;
    }

    public BonusAndFine setMaxFinePoint(int maxPoint) {
        this.maxFinePoint = maxPoint;
        return this;
    }

    public String getCondition() {
        return condition;
    }

    public BonusAndFine setCondition(String condition) {
        this.condition = condition;
        return this;
    }

    public String getReward() {
        return reward;
    }

    public BonusAndFine setReward(String reward) {
        this.reward = reward;
        return this;
    }

    public String getPenalty() {
        return penalty;
    }

    public BonusAndFine setPenalty(String penalty) {
        this.penalty = penalty;
        return this;
    }


    //setting Flag by point

    private void setFlag() {
        if (humanPoint > maxFinePoint) {
            flag = FLAG_REWARD;
        } else if (humanPoint > minBonusPoint && humanPoint < maxFinePoint) {
            flag = "";
        } else if (humanPoint < minBonusPoint) {
            flag = FLAG_PENALTY;
        }
    }

    public String getFlag() {
        return flag;
    }

    public int getId() {
        return id;
    }

    public BonusAndFine setId(int id) {
        this.id = id;
        return this;
    }
}
