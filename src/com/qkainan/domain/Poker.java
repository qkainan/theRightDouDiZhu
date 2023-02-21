package com.qkainan.domain;

import java.util.*;
import java.util.stream.Collectors;


public class  Poker {
    //初始化牌；创建整幅扑克牌
    //创建一个Map集合，存储牌的索引和组装好的牌
    HashMap<Integer, String> pokerCard = new HashMap<>();
    //创建一个ArrayList集合，存储牌的索引
    ArrayList<Integer> pokerNumber = new ArrayList<>();


    public HashMap initCard() {
        //准备牌
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


}


