package com.qkainan.domain;

import java.util.*;
import java.util.stream.Collectors;


public class Poker {
//1 、发牌。 一副牌 54 张，一人 17 张，留 3 张做底牌，在确定地主之前玩家不能看底牌。
//初始化
    User player01 = new User() ;
    User player02 = new User() ;
    User player03 = new User() ;
    User landOwner = new User();

    //初始化牌；创建整幅扑克牌
    List<String> arr = new ArrayList<>();

    //创建四个集合用于存储三个玩家的手牌以及底牌
    List<String> player1 = new ArrayList<>();
    List<String> player2 = new ArrayList<>();
    List<String> player3 = new ArrayList<>();
    List<String> landowner = new ArrayList<>();

    List<String> dipai = new ArrayList<>();


    int score01 = 0;
    int score02 = 0;
    int score03 = 0;

    public void initUser(){
        player01.setName("player01");
        player02.setName("player02");
        player03.setName("player03");

        player01.setList(player1);
        player02.setList(player2);
        player03.setList(player3);

        player01.setScore(score01);
        player02.setScore(score02);
        player03.setScore(score03);
    }

    public void initCard() {

        String[] colors = {"♥", "♠", "♦", "♣"};
        String[] number = { "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K", "A","2"};
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
        seeCard(player01.getList());
        System.out.print("玩家2的牌为：");
        seeCard(player02.getList());
        System.out.print("玩家3的牌为：");
        seeCard(player03.getList());
        System.out.print("底牌为： ");
        seeCard(dipai);
    }

    //抢地主
    // 叫牌
    //叫牌按出牌的顺序轮流进行，每人只能叫一次。叫牌时可以叫 “1 分 ” ， “2 分 ” ， “3 分 ” ， “ 不叫 ” 。
    //后叫牌者只能叫比前面玩家高的分或者不叫。叫牌结束后所叫分值最大的玩家为地主；如果有玩家叫 “3 分 ” 则立即结束叫牌，该玩家为地主；
    //如果都不叫，则重新发牌，重新叫牌。
    //抢地主
    public User qiangDiZhu(){

        int[] sc = {score01,score02,score03};
        System.out.print("玩家1叫牌：");
        jiaoPai(score01);

        System.out.print("玩家2叫牌：");
        jiaoPai(score02);

        System.out.print("玩家3叫牌：");
        jiaoPai(score03);

        //找出分最高的人
        for (int i = 0; i < sc.length - 1; i++) {
            for (int j = 0; j < sc.length - 1 - i; j++) {
                if (sc[j] > sc[j + 1]){
                    int temp = sc[j];
                    sc[j] = sc[j + 1];
                    sc[j + 1] = temp;
                }
            }
        }
        System.out.print("分数最高的是:" + sc[2]);


        if (sc[2] == score01){
            landOwner = player01;
        } else if (sc[2] == score02) {
            landOwner = player02;
        } else if (sc[2] == score03) {
            landOwner = player03;
        }

        //把底牌给地主
        String[] dp = new String[3];
        for (int i = 0; i < dipai.size(); i++) {
            dp[i] = dipai.get(i);
        }
        for (int i = 0; i < dp.length; i++) {
            landowner.add(dp[i]);
        }
        System.out.println("地主是：" + landOwner.getName());

        System.out.print("地主的牌为：");
        seeCard(landOwner.getList());
        return landOwner;
    }

    //定义一个方法用于叫牌
    public static int jiaoPai(int score){
        String str = new String();
        System.out.println("请输入" +" " + "1分" + " " + "2分" + " " +"3分" + " " +"不叫");

        String one = "1分";
        String two = "2分";
        String three = "3分";
        String no = "不叫";

        Scanner in = new Scanner(System.in);
        str = in.nextLine();

        if (str.equals(one)){
            score++;
        } else if (str.equals(two)) {
            score = score + 2;
        }
        else if (str.equals(three)) {
            score = score + 3;
        } else if (str.equals(no)) {
            score = score + 0;
        }else {
            System.out.println("输入错误");
        }
        return score;

    }

    //定义一个方法用于看牌
    public static void seeCard(List<String> l) {
        for (String s : l) {
            System.out.print(s);
        }
        System.out.println(" ");
    }

}




