package com.qkainan.domain;

import java.util.ArrayList;
import java.util.Collections;

public class Poker {

//1 、发牌。 一副牌 54 张，一人 17 张，留 3 张做底牌，在确定地主之前玩家不能看底牌。
//初始化牌；创建整幅扑克牌
    ArrayList<String> arr = new ArrayList<>();

    public void initCard() {
        String[] colors = {"♥", "♠", "♦", "♣"};
        String[] number = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
        for (String c : colors) {
            for (String n : number) {
                arr.add(c + n);
            }
        }
        arr.add(" 小王");
        arr.add(" 大王");
        //打乱排序
        Collections.shuffle(arr);
    };


    ArrayList<String> player1 = new ArrayList<>();
    ArrayList<String> player2 = new ArrayList<>();
    ArrayList<String> player3 = new ArrayList<>();
    ArrayList<String> dipai = new ArrayList<>();
    //创建四个集合用于存储三个玩家的手牌以及底牌
    User player01 = new User("player01",player1) ;
    User player02 = new User("player02",player2) ;
    User player03 = new User("player03",player2) ;




    //发牌、拿牌
    public void getPorkCard() {
        //发牌
        for (int i = 0; i < arr.size(); i++) {
            String poker = arr.get(i);
            if (i > arr.size() - 4) {
                dipai.add(poker);
            } else if (i % 3 == 0) {
                player1.add(poker);
            } else if (i % 3 == 1) {
                player2.add(poker);
            } else if (i % 3 == 2) {
                player3.add(poker);
            }
        }
    }

    //给牌排序
    public void orderingCard(){

    }
    public void lookCard(){
        //看牌
        System.out.print("玩家1的牌为：");
        seeCard(player01.getPlayer());
        System.out.print("玩家2的牌为：");
        seeCard(player02.getPlayer());
        System.out.print("玩家3的牌为：");
        seeCard(player03.getPlayer());
        System.out.print("底牌为： ");
        seeCard(dipai);
    }

    //定义一个方法用于看牌
    public static void seeCard(ArrayList<String> ar) {
        for (String s : ar) {
            System.out.print(s);
        }
        System.out.println(" ");
    }


}




