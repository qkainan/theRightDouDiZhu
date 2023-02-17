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
    //创建一个Map集合，存储牌的索引和组装好的牌
    HashMap<Integer, String> pokerCard = new HashMap<>();
    //创建一个ArrayList集合，存储牌的索引
    ArrayList<Integer> pokerNumber = new ArrayList<>();

    //创建四个集合用于存储三个玩家的手牌以及底牌
    List<Integer> player1 = new ArrayList<>();
    List<Integer> player2 = new ArrayList<>();
    List<Integer> player3 = new ArrayList<>();
    List<Integer> landowner = new ArrayList<>();

    List<Integer> diPai = new ArrayList<>();


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
        //洗牌
        //使用Collections中的方法shuffle(List)方法,对poker的索引进行洗牌
        Collections.shuffle(pokerNumber);
    }


    //发牌、拿牌、给牌排序
    public void getPorkCard() {
        //遍历索引ArrayList集合，获取每一张牌的索引
        for (int i = 0; i < pokerNumber.size(); i++) {
            Integer in = pokerNumber.get(i);
            //分出三张底牌
            if (i > pokerNumber.size() - 4) {
                //给底牌发牌
                diPai.add(in);
            } else if (i % 3 == 0) {
                //给玩家1发牌
                player1.add(in);
            } else if (i % 3 == 1) {
                //给玩家2发牌
                player2.add(in);
            } else if (i % 3 == 2) {
                //给玩家3发牌
                player3.add(in);
            }
        }
        //排序,使用Collections中的方法sort(List)，对玩家的牌进行排序
        Collections.sort(player01.getList());
        Collections.sort(player02.getList());
        Collections.sort(player03.getList());
    }

    public void lookCard() {
        //看牌
        System.out.print("玩家1的牌为：");
        seeCard(pokerCard, player01.getList());
        System.out.print("玩家2的牌为：");
        seeCard(pokerCard, player02.getList());
        System.out.print("玩家3的牌为：");
        seeCard(pokerCard, player03.getList());
        System.out.print("底牌为： ");
        seeCard(pokerCard, diPai);
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


        if (sc[2] == score01) {
            landOwner = player01;
        } else if (sc[2] == score02) {
            landOwner = player02;
        } else if (sc[2] == score03) {
            landOwner = player03;
        }

        //把底牌给地主
        Integer[] dp = new Integer[3];
        for (int i = 0; i < diPai.size(); i++) {
            dp[i] = diPai.get(i);
        }
        for (int i = 0; i < dp.length; i++) {
            landowner.add(dp[i]);
        }
        System.out.println("地主是：" + landOwner.getName());

        System.out.print("地主的牌为：");
        seeCard(pokerCard, landOwner.getList());
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
    public static void seeCard(HashMap<Integer, String> poker, List<Integer> list) {
        //遍历玩家或底牌集合，获取牌的索引
        for(Integer key:list){
            //通过牌的索引，通过Map集合get()方法找到牌
            String value = poker.get(key);
            //输出牌
            System.out.print(value+" ");
        }
        System.out.println();
    }
}
