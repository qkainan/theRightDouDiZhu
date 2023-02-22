package com.qkainan.domain;
import java.util.*;
import java.util.List;

public class Game {
    //初始化
    Poker poker = new Poker();

    User player01 = new User();
    User player02 = new User();
    User player03 = new User();
    User landOwner = new User();

    //弃牌区
    List<Integer> discardArea = new ArrayList<>();

    //创建四个集合用于存储三个玩家的手牌以及底牌
    List<Integer> player1 = new ArrayList<>();
    List<Integer> player2 = new ArrayList<>();
    List<Integer> player3 = new ArrayList<>();
    List<Integer> landowner = new ArrayList<>();

    List<Integer> diPai = new ArrayList<>();

    int score01 = 0;
    int score02 = 0;
    int score03 = 0;

    //用于存储牌的大小
    List<Integer> magnitude = new ArrayList<>();



    public void initUser() {
        player01.setName("player01");
        player02.setName("player02");
        player03.setName("player03");
        landOwner.setName("landOwner");

        player01.setList(player1);
        player02.setList(player2);
        player03.setList(player3);
        landOwner.setList(landowner);

        player01.setScore(score01);
        player02.setScore(score02);
        player03.setScore(score03);
    }

    //定义一个方法用于洗牌
    //使用Collections中的方法shuffle(List)方法,对poker的索引进行洗牌
    public void shuffleCard(){
        Collections.shuffle(poker.getPokerNumber());
    }

    //发牌,一人 17 张，留 3 张做底牌，在确定地主之前玩家不能看底牌。
    public void getPorkCard() {
        //遍历索引ArrayList集合，获取每一张牌的索引
        for (int i = 0; i < poker.getPokerNumber().size(); i++) {
            Integer in = poker.getPokerNumber().get(i);
            //分出三张底牌
            if (i > poker.getPokerNumber().size() - 4) {
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
        seeCard(poker.getPokerCard(), player01.getList());
        System.out.print("玩家2的牌为：");
        seeCard(poker.getPokerCard(), player02.getList());
        System.out.print("玩家3的牌为：");
        seeCard(poker.getPokerCard(), player03.getList());
        System.out.print("底牌为： ");
        seeCard(poker.getPokerCard(), diPai);
    }

    //叫牌按出牌的顺序轮流进行，每人只能叫一次。叫牌时可以叫 “1 分 ” ， “2 分 ” ， “3 分 ” ， “ 不叫 ” 。
    //抢地主
    public User qiangDiZhu() {
        System.out.print("玩家1叫牌：");
        score01 = jiaoPai(score01);

        System.out.print("玩家2叫牌：");
        score02 = jiaoPai(score02);

        System.out.print("玩家3叫牌：");
        score03 = jiaoPai(score03);

        int[] sc = {score01, score02, score03};

        //找出分最高的人
        for (int i = 0; i < sc.length - 1; i++) {
            for (int j = 0; j < sc.length - 1 - i; j++) {
                if (sc[j] > sc[j + 1]) {
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
        Collections.addAll(landOwner.getList(), dp);
        System.out.println("地主是：" + landOwner.getName());

        System.out.print("地主的牌为：");
        seeCard(poker.getPokerCard() , landOwner.getList());
        return landOwner;
    }

//    public void playCard(){
//        if (landOwner == player01) {
//            goCard(landOwner, poker.getPokerCard(), landOwner.getList());
//            goCard(player02, poker.getPokerCard(), player02.getList());
//            goCard(player03, poker.getPokerCard(), player02.getList());
//        } else if (landOwner == player02) {
//            goCard(landOwner, poker.getPokerCard(), landOwner.getList());
//            goCard(player03, poker.getPokerCard(), player03.getList());
//            goCard(player02, poker.getPokerCard(), player02.getList());
//        } else if (landOwner == player03) {
//            goCard(landOwner, poker.getPokerCard(), landOwner.getList());
//            goCard(player01, poker.getPokerCard(), player01.getList());
//            goCard(player02, poker.getPokerCard(), player02.getList());
//        }
//
//    }

    //定义一个方法用于叫牌
    public static int jiaoPai(int score) {
        String str = new String();
        System.out.println("请输入" + " " + "1分" + " " + "2分" + " " + "3分" + " " + "不叫");

        String one = "1分";
        String two = "2分";
        String three = "3分";
        String no = "不叫";

        Scanner in = new Scanner(System.in);
        str = in.nextLine();

        if (str.equals(one)) {
            score++;
        } else if (str.equals(two)) {
            score = score + 2;
        } else if (str.equals(three)) {
            score = score + 3;
        } else if (str.equals(no)) {
            score = score + 0;
        } else {
            System.out.println("输入错误");
        }
        System.out.println("叫牌后分数为:" + score);
        return score;
    }

    //定义一个方法用于看牌
    public static void seeCard(HashMap<Integer, String> poker, List<Integer> list) {
        //遍历玩家或底牌集合，获取牌的索引
        for (Integer key : list) {
            //通过牌的索引，通过Map集合get()方法找到牌
            String value = poker.get(key);
            //输出牌
            System.out.print(value + " ");
        }
        System.out.println();
    }

    //定义一个方法用于出牌
    //出牌思路:
    //1.首先定义一个变量，用于记录当前玩家是否输入“出牌”的标记，初始值为false。
    //2.进入出牌环节后，玩家每输入一张牌，先判断该牌是否为“出牌”。
    //      如果是，将标记置为true，退出输入循环。
    //      如果不是，将该牌转化为map中对应的索引。
    //3.当玩家输入“出牌”后，开始进行牌型判断。
    //4.如果牌型合法，则将玩家出的牌从手牌中删除，更新当前手牌。
    //5.如果牌型不合法，则提示玩家重新输入出牌。
    //6.循环进行，直到玩家出完所有手牌或选择不出牌。

    public void goCard(User u, Poker p) {
        //输入想出的牌
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        //判断出牌者牌库中是否有该牌
        //通过牌的索引，通过Map集合get()方法找到牌
        //判断牌是否存在
        for (Integer key : p.getPokerNumber()) {
            //通过牌的索引，通过Map集合get()方法找到牌
            String value = p.getPokerCard().get(key);
            try {
                if (value.equals(s)) {
                    System.out.println(u.getName() + "出牌了" + value);
                }
            } catch (NumberFormatException e) {
                System.out.println("输入有误，请重新输入");
            }
        }
        if (s.equals(" ")) {
            System.out.println(u.getName() + "不出牌，过");
            return;
        }

        //将出的牌置入弃牌区当中
        for (Integer key : p.getPokerNumber()) {
            //通过牌的索引，通过Map集合get()方法找到牌
            String value = p.getPokerCard().get(key);
            for (int i = 0; i < p.getPokerNumber().size(); i++) {
                if (value.equals(s)) {
                    discardArea.add(p.getPokerNumber().get(i));
                }
            }
        }

        //判断胜利
        if (u.getList().size() == 0) {
            System.out.println(u.getName() + "及其队伍获得胜利");
        }
    }

}




