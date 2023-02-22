package com.qkainan.domain;

import java.util.List;


public class User {
    private int score;
    private String name;
    private List<Integer> list;

    public User() {
    }

    public User(int score, String name, List<Integer> list) {
        this.score = score;
        this.name = name;
        this.list = list;
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
     * @return list
     */
    public List<Integer> getList() {
        return list;
    }

    /**
     * 设置
     * @param list
     */
    public void setList(List<Integer> list) {
        this.list = list;
    }

    public String toString() {
        return "User{score = " + score + ", name = " + name + ", list = " + list + "}";
    }
}
