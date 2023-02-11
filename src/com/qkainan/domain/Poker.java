package com.qkainan.domain;


import java.util.ArrayList;
import java.util.Collections;

public class Poker {

//* 属性：List cards 整幅扑克牌的集合 、 People[] peoples 角色
//初始化牌；创建整幅扑克牌

        ArrayList<String> arr = new ArrayList<>();
        public void initCard () {
            String[] colors = {"♥", "♠", "♦", "♣"};
            String[] number = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A"};
            for (String c : colors) {
                for (String n : number) {
                    arr.add(c + n);
                }
            }
            arr.add("小王");
            arr.add("大王");
            //打乱排序
            Collections.shuffle(arr);
        };


//创建四个集合用于存储三个玩家的手牌以及底牌
        ArrayList<String> player1 = new ArrayList<>();
        ArrayList<String> player2 = new ArrayList<>();
        ArrayList<String> player3 = new ArrayList<>();
        ArrayList<String> dipai = new ArrayList<>();


//发牌、拿牌、看牌
        public void getPorkCard () {
            //发牌
            for (int i = 0; i < arr.size(); i++) {
                String poker = arr.get(i);
                if (i > arr.size() - 3) {
                    dipai.add(poker);
                } else if (i % 3 == 0) {
                    player1.add(poker);
                } else if (i % 3 == 1) {
                    player2.add(poker);
                } else if (i % 3 == 2) {
                    player3.add(poker);
                }
            }

            //整理牌
            Collections.sort(player1);
            Collections.sort(player2);
            Collections.sort(player3);

            //看牌
            System.out.print("玩家1的牌为：");
            seeCard(player1);
            System.out.print("玩家2的牌为：");
            seeCard(player2);
            System.out.print("玩家3的牌为：");
            seeCard(player3);
            System.out.print("底牌为： ");
            seeCard(dipai);
        }
        public static void seeCard (ArrayList < String > ar) {
            for (String s : ar) {
                System.out.print(s);
            }
            System.out.println(" ");
        }

    //叫地主


}
