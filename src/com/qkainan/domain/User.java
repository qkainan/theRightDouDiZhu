package com.qkainan.domain;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;


import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import sun.misc.SharedSecrets;

public class User {
    private int score;
    private String name;
    private ArrayList<String> arrayList;


    public User() {
    }

    public User(int score, String name, ArrayList<String> arrayList) {
        this.score = score;
        this.name = name;
        this.arrayList = arrayList;
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
     * @return arrayList
     */
    public ArrayList<String> getArrayList() {
        return arrayList;
    }

    /**
     * 设置
     * @param arrayList
     */
    public void setArrayList(ArrayList<String> arrayList) {
        this.arrayList = arrayList;
    }

    public String toString() {
        return "User{score = " + score + ", name = " + name + ", arrayList = " + arrayList + "}";
    }
}
