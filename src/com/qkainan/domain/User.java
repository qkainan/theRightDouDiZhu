package com.qkainan.domain;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;


import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import sun.misc.SharedSecrets;

public class User {
    String name;
    ArrayList<String> player;

    public User() {
    }

    public User(String name, ArrayList<String> player) {
        this.name = name;
        this.player = player;
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
        return "User{name = " + name + ", player = " + player + "}";
    }
}
