package com.qkainan.domain;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;


import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import sun.misc.SharedSecrets;

public class User {
    private int score;
    private String name;
    private List<String> list;


    public User() {
    }

    public User(int score, String name, List<String> list) {
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
    public List<String> getList() {
        return list;
    }

    /**
     * 设置
     * @param list
     */
    public void setList(List<String> list) {
        this.list = list;
    }

    public String toString() {
        return "User{score = " + score + ", name = " + name + ", list = " + list + "}";
    }


}
