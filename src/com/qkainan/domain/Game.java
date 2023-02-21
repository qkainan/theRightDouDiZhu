package com.qkainan.domain;
import java.util.*;

public class Game {
    Poker pk = new Poker();
    pk.initCard();

    //定义一个方法将卡牌的索引分组
    public int groupIndex(List<Integer> group){
        Collection<Integer> value = hm.values();
        //将所有卡牌的索引植入集合
        if(value.hashCode() == 0 || value.hashCode() == 1){
            group.add(judgeMagnitude.CardMagnitude.cmW.ordinal());
            return judgeMagnitude.CardMagnitude.cmW.ordinal();
        }
        if(value.hashCode() > 1 && value.hashCode() < 6){
            group.add(judgeMagnitude.CardMagnitude.cm2.ordinal());
            return judgeMagnitude.CardMagnitude.cm2.ordinal();
        }
        if(value.hashCode() > 5 && value.hashCode() < 10){
            group.add(judgeMagnitude.CardMagnitude.cmA.ordinal());
            return judgeMagnitude.CardMagnitude.cmA.ordinal();
        }
        if(value.hashCode() > 9 && value.hashCode() < 14){
            group.add(judgeMagnitude.CardMagnitude.cmK.ordinal());
            return judgeMagnitude.CardMagnitude.cmK.ordinal();
        }
        if(value.hashCode() > 13 && value.hashCode() < 18){
            group.add(judgeMagnitude.CardMagnitude.cmQ.ordinal());
            return judgeMagnitude.CardMagnitude.cmQ.ordinal();
        }
        if(value.hashCode() > 17 && value.hashCode() < 23){
            group.add(judgeMagnitude.CardMagnitude.cmJ.ordinal());
            return judgeMagnitude.CardMagnitude.cmJ.ordinal();
        }
        if(value.hashCode() > 22 && value.hashCode() < 27){
            group.add(judgeMagnitude.CardMagnitude.cm10.ordinal());
            return judgeMagnitude.CardMagnitude.cm10.ordinal();
        }
        if(value.hashCode() > 26 && value.hashCode() < 31){
            group.add(judgeMagnitude.CardMagnitude.cm9.ordinal());
            return judgeMagnitude.CardMagnitude.cm9.ordinal();
        }
        if(value.hashCode() > 30 && value.hashCode() < 35){
            group.add(judgeMagnitude.CardMagnitude.cm8.ordinal());
            return judgeMagnitude.CardMagnitude.cm8.ordinal();
        }
        if(value.hashCode() > 34 && value.hashCode() < 39){
            group.add(judgeMagnitude.CardMagnitude.cm7.ordinal());
            return judgeMagnitude.CardMagnitude.cm7.ordinal();
        }
        if(value.hashCode() > 38 && value.hashCode() < 43){
            group.add(judgeMagnitude.CardMagnitude.cm6.ordinal());
            return judgeMagnitude.CardMagnitude.cm6.ordinal();
        }
        if(value.hashCode() > 42 && value.hashCode() < 47){
            group.add(judgeMagnitude.CardMagnitude.cm5.ordinal());
            return judgeMagnitude.CardMagnitude.cm5.ordinal();
        }
        if(value.hashCode() > 46 && value.hashCode() < 51){
            group.add(judgeMagnitude.CardMagnitude.cm4.ordinal());
            return judgeMagnitude.CardMagnitude.cm4.ordinal();
        }
        if(value.hashCode() > 50 && value.hashCode() < 54){
            group.add(judgeMagnitude.CardMagnitude.cm3.ordinal());
            return judgeMagnitude.CardMagnitude.cm3.ordinal();
        }else {
            group.add(judgeMagnitude.CardMagnitude.cm0.ordinal());
            return judgeMagnitude.CardMagnitude.cm0.ordinal();
        }
    }


    //发牌。 一副牌 54 张，一人 17 张，留 3 张做底牌，在确定地主之前玩家不能看底牌。
//初始化
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
        Collections.shuffle(pk.pokerNumber);
    }

    //发牌、拿牌、给牌排序
    public void getPorkCard() {
        //遍历索引ArrayList集合，获取每一张牌的索引
        for (int i = 0; i < pk.pokerNumber.size(); i++) {
            Integer in = pk.pokerNumber.get(i);
            //分出三张底牌
            if (i > pk.pokerNumber.size() - 4) {
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
        seeCard(pk.pokerCard, player01.getList());
        System.out.print("玩家2的牌为：");
        seeCard(pk.pokerCard, player02.getList());
        System.out.print("玩家3的牌为：");
        seeCard(pk.pokerCard, player03.getList());
        System.out.print("底牌为： ");
        seeCard(pk.pokerCard, diPai);
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
        seeCard(pk.pokerCard, landOwner.getList());
        return landOwner;
    }

    public void playCard(){
        if (landOwner == player01) {
            goCard(landOwner, pk.pokerCard, landOwner.getList());
            goCard(player02, pk.pokerCard, player02.getList());
            goCard(player03, pk.pokerCard, player02.getList());
        } else if (landOwner == player02) {
            goCard(landOwner, pk.pokerCard, landOwner.getList());
            goCard(player03, pk.pokerCard, player03.getList());
            goCard(player02, pk.pokerCard, player02.getList());
        } else if (landOwner == player03) {
            goCard(landOwner, pk.pokerCard, landOwner.getList());
            goCard(player01, pk.pokerCard, player01.getList());
            goCard(player02, pk.pokerCard, player02.getList());
        }

    }

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
    public void goCard(User u, HashMap<Integer, String> poker, List<Integer> list) {

        //出牌即将集合的索引植入弃牌区当中
        //输入想出的牌
        Scanner sc = new Scanner(System.in);
        String s = sc.nextLine();

        //判断出牌者牌库中是否有该牌
        //通过牌的索引，通过Map集合get()方法找到牌
        //判断牌是否存在
        for (Integer key : list) {
            //通过牌的索引，通过Map集合get()方法找到牌
            String value = poker.get(key);
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
        for (Integer key : list) {
            //通过牌的索引，通过Map集合get()方法找到牌
            String value = poker.get(key);
            for (int i = 0; i < list.size(); i++) {
                if (value.equals(s)) {
                    discardArea.add(list.get(i));
                }
            }
        }

        //判断胜利
        if (u.getList().size() == 0) {
            System.out.println(u.getName() + "及其队伍获得胜利");
        }
    }
}




