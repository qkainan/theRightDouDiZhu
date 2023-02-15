package com.qkainan.domain;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;


import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import sun.misc.SharedSecrets;

public class User {
    int score;
    String name;
    ArrayList<String> player;

    public User() {
    }

    public User(int score, String name, ArrayList<String> player) {
        this.score = score;
        this.name = name;
        this.player = player;
    }

    /**
     * 获取
     * @return score
     */
    public int getScore() {
        return score;
    }

    /**
     * 设置
     * @param score
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * 获取
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * 设置
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取
     * @return player
     */
    public ArrayList<String> getPlayer() {
        return player;
    }

    /**
     * 设置
     * @param player
     */
    public void setPlayer(ArrayList<String> player) {
        this.player = player;
    }

    public String toString() {
        return "User{score = " + score + ", name = " + name + ", player = " + player + "}";
    }
}
