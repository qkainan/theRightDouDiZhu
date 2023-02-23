package com.qkainan.domain;

import java.util.*;


public class  Poker {
    //创建一个Map集合，存储牌的索引和组装好的牌
    private HashMap<Integer, String> pokerCard = new HashMap<>();
    //创建一个ArrayList集合，存储牌的索引
    private ArrayList<Integer> pokerNumber = new ArrayList<>();
    //创建一个Map集合，用于存储牌的标签
    private HashMap<Integer, CardMagnitude> pokerGroup = new HashMap<Integer, CardMagnitude>();

    public Poker() {
    }

    public Poker(HashMap<Integer, String> pokerCard, ArrayList<Integer> pokerNumber, HashMap<Integer, CardMagnitude> pokerGroup) {
        this.pokerCard = pokerCard;
        this.pokerNumber = pokerNumber;
        this.pokerGroup = pokerGroup;
    }


    //初始化牌；创建整幅扑克牌
    public HashMap creatCardBase() {
        //准备牌 一副牌 54 张
        //定义两个数组存储花色和牌的顺序
        String[] colors = {"♠", "♥", "♣", "♦"};
        String[] numbers = {"2", "A", "K", "Q", "J", "10", "9", "8", "7", "6", "5", "4", "3"};

        //先把大王和小王存储到集合中
        int index = 0;
        pokerCard.put(index, "大王");
        pokerNumber.add(index);
        pokerGroup.put(index,CardMagnitude.cmDW);
        index++;
        pokerCard.put(index, "小王");
        pokerNumber.add(index);
        pokerGroup.put(index, CardMagnitude.cmXW);
        index++;
        //循环嵌套遍历三个数组，花色、顺序以及标签，组装52张牌，存储到集合中
        for (String number : numbers) {
            for (String color : colors) {
                for (int i = 0; i < 4; i++) {
                    pokerCard.put(index, color + number);
                    pokerNumber.add(index);
                    pokerGroup.put(index , CardMagnitude.cm2);
                    index++;
                }for (int i = 0; i < 4; i++) {
                    pokerCard.put(index, color + number);
                    pokerNumber.add(index);
                    pokerGroup.put(index , CardMagnitude.cmA);
                    index++;
                }for (int i = 0; i < 4; i++) {
                    pokerCard.put(index, color + number);
                    pokerNumber.add(index);
                    pokerGroup.put(index , CardMagnitude.cmK);
                    index++;
                }for (int i = 0; i < 4; i++) {
                    pokerCard.put(index, color + number);
                    pokerNumber.add(index);
                    pokerGroup.put(index , CardMagnitude.cmQ);
                    index++;
                }for (int i = 0; i < 4; i++) {
                    pokerCard.put(index, color + number);
                    pokerNumber.add(index);
                    pokerGroup.put(index , CardMagnitude.cmJ);
                    index++;
                }for (int i = 0; i < 4; i++) {
                    pokerCard.put(index, color + number);
                    pokerNumber.add(index);
                    pokerGroup.put(index , CardMagnitude.cm10);
                    index++;
                }for (int i = 0; i < 4; i++) {
                    pokerCard.put(index, color + number);
                    pokerNumber.add(index);
                    pokerGroup.put(index , CardMagnitude.cm9);
                    index++;
                }for (int i = 0; i < 4; i++) {
                    pokerCard.put(index, color + number);
                    pokerNumber.add(index);
                    pokerGroup.put(index , CardMagnitude.cm8);
                    index++;
                }for (int i = 0; i < 4; i++) {
                    pokerCard.put(index, color + number);
                    pokerNumber.add(index);
                    pokerGroup.put(index , CardMagnitude.cm7);
                    index++;
                }for (int i = 0; i < 4; i++) {
                    pokerCard.put(index, color + number);
                    pokerNumber.add(index);
                    pokerGroup.put(index , CardMagnitude.cm6);
                    index++;
                }for (int i = 0; i < 4; i++) {
                    pokerCard.put(index, color + number);
                    pokerNumber.add(index);
                    pokerGroup.put(index , CardMagnitude.cm5);
                    index++;
                }for (int i = 0; i < 4; i++) {
                    pokerCard.put(index, color + number);
                    pokerNumber.add(index);
                    pokerGroup.put(index , CardMagnitude.cm4);
                    index++;
                }for (int i = 0; i < 4; i++) {
                    pokerCard.put(index, color + number);
                    pokerNumber.add(index);
                    pokerGroup.put(index , CardMagnitude.cm3);
                    index++;
                }
            }
        }
        return pokerCard;
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
    public ArrayList<Integer> getPokerNumber() {
        return pokerNumber;
    }

    /**
     * 设置
     * @param pokerNumber
     */
    public void setPokerNumber(ArrayList<Integer> pokerNumber) {
        this.pokerNumber = pokerNumber;
    }

    /**
     * 获取
     * @return pokerGroup
     */
    public HashMap<Integer, CardMagnitude> getPokerGroup() {
        return pokerGroup;
    }

    /**
     * 设置
     * @param pokerGroup
     */
    public void setPokerGroup(HashMap<Integer, CardMagnitude> pokerGroup) {
        this.pokerGroup = pokerGroup;
    }

    public String toString() {
        return "Poker{pokerCard = " + pokerCard + ", pokerNumber = " + pokerNumber + ", pokerGroup = " + pokerGroup + "}";
    }
}


