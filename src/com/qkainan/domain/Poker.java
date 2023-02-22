package com.qkainan.domain;

import java.util.*;


public class  Poker {
    //创建一个Map集合，存储牌的索引和组装好的牌
    private HashMap<Integer, String> pokerCard = new HashMap<>();
    //创建一个ArrayList集合，存储牌的索引
    private ArrayList<Integer> pokerNumber = new ArrayList<>();
    //创建一个List集合，用于存储牌的标签
    List group = new ArrayList<CardMagnitude>();

    public Poker() {
    }

    public Poker(HashMap<Integer, String> pokerCard, ArrayList<Integer> pokerNumber, List group) {
        this.pokerCard = pokerCard;
        this.pokerNumber = pokerNumber;
        this.group = group;
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
        index++;
        pokerCard.put(index, "小王");
        pokerNumber.add(index);
        index++;
        //循环嵌套遍历两个数组，花色和顺序，组装52张牌，存储到集合中
        for (String number : numbers) {
            for (String color : colors) {
                pokerCard.put(index, color + number);
                pokerNumber.add(index);
                index++;
            }
        }
        return pokerCard;
    }

    //定义一个方法将卡牌的索引分组，给每种牌都打上标签
    public CardMagnitude groupIndex() {
        for (int i = 0; i < pokerNumber.size(); i++) {
            //将所有卡牌的索引植入集合
            if (pokerNumber.get(i) == 0) {
                group.add(CardMagnitude.cmDW);

            } else if (pokerNumber.get(i) == 1) {
                group.add(CardMagnitude.cmXW);

            } else if (pokerNumber.get(i) > 1 && pokerNumber.get(i) < 6) {
                group.add(CardMagnitude.cm2);

            } else if (pokerNumber.get(i) > 5 && pokerNumber.get(i) < 10) {
                group.add(CardMagnitude.cmA);

            } else if (pokerNumber.get(i) > 9 && pokerNumber.get(i) < 14) {
                group.add(CardMagnitude.cmK);

            } else if (pokerNumber.get(i) > 13 && pokerNumber.get(i) < 18) {
                group.add(CardMagnitude.cmQ);

            } else if (pokerNumber.get(i) > 17 && pokerNumber.get(i) < 23) {
                group.add(CardMagnitude.cmJ);

            } else if (pokerNumber.get(i) > 22 && pokerNumber.get(i) < 27) {
                group.add(CardMagnitude.cm10);

            } else if (pokerNumber.get(i) > 26 && pokerNumber.get(i) < 31) {
                group.add(CardMagnitude.cm9);

            } else if (pokerNumber.get(i) > 30 && pokerNumber.get(i) < 35) {
                group.add(CardMagnitude.cm8);

            } else if (pokerNumber.get(i) > 34 && pokerNumber.get(i) < 39) {
                group.add(CardMagnitude.cm7);

            } else if (pokerNumber.get(i) > 38 && pokerNumber.get(i) < 43) {
                group.add(CardMagnitude.cm6);

            } else if (pokerNumber.get(i) > 42 && pokerNumber.get(i) < 47) {
                group.add(CardMagnitude.cm5);

            } else if (pokerNumber.get(i) > 46 && pokerNumber.get(i) < 51) {
                group.add(CardMagnitude.cm4);

            } else if (pokerNumber.get(i) > 50 && pokerNumber.get(i) < 54) {
                group.add(CardMagnitude.cm3);

            }
        }
        return CardMagnitude.cm0;
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
     * @return group
     */
    public List getGroup() {
        return group;
    }

    /**
     * 设置
     * @param group
     */
    public void setGroup(List group) {
        this.group = group;
    }

    public String toString() {
        return "Poker{pokerCard = " + pokerCard + ", pokerNumber = " + pokerNumber + ", group = " + group + "}";
    }
}


