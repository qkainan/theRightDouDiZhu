package com.qkainan.domain;

import java.util.*;


public class  Poker {
    //初始化牌；创建整幅扑克牌
    //创建一个Map集合，存储牌的索引和组装好的牌
    private HashMap<Integer, String> pokerCard = new HashMap<>();
    //创建一个ArrayList集合，存储牌的索引
    private ArrayList<Integer> getPokerNumber = new ArrayList<>();

    public Poker() {
    }

    public Poker(HashMap<Integer, String> pokerCard, ArrayList<Integer> pokerNumber) {
        this.pokerCard = pokerCard;
        this.getPokerNumber = pokerNumber;
    }


    /**
     * 获取
     * @return pokerCard
     */
    public HashMap<Integer, String> getPokerCard() {
        return pokerCard;
    }

    /**
     * 设置
     * @param pokerCard
     */
    public void setPokerCard(HashMap<Integer, String> pokerCard) {
        this.pokerCard = pokerCard;
    }

    /**
     * 获取
     * @return pokerNumber
     */
    public ArrayList<Integer> getGetPokerNumber() {
        return getPokerNumber;
    }

    /**
     * 设置
     * @param getPokerNumber
     */
    public void setGetPokerNumber(ArrayList<Integer> getPokerNumber) {
        this.getPokerNumber = getPokerNumber;
    }

    public String toString() {
        return "Poker{pokerCard = " + pokerCard + ", pokerNumber = " + getPokerNumber + "}";
    }

    public HashMap creatCardBase() {
        //准备牌 一副牌 54 张
        //定义两个数组存储花色和牌的顺序
        String[] colors = {"♠", "♥", "♣", "♦"};
        String[] numbers = {"2", "A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3"};

        //先把大王和小王存储到集合中
        int index = 0;
        pokerCard.put(index, "大王");
        getPokerNumber.add(index);
        index++;
        pokerCard.put(index, "小王");
        getPokerNumber.add(index);
        index++;
        //循环嵌套遍历两个数组，花色和顺序，组装52张牌，存储到集合中
        for (String number : numbers) {
            for (String color : colors) {
                pokerCard.put(index, color + number);
                getPokerNumber.add(index);
                index++;
            }
        }
        return pokerCard;
    }

}


