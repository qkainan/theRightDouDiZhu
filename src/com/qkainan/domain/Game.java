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
    public void shuffleCard() {
        Collections.shuffle(poker.getPokerNumber());
    }

    //发牌,一人 17 张，留 3 张做底牌，在确定地主之前玩家不能看底牌。
    public void dealPorkCard() {
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
        seeCard(poker.getPokerCard(), landOwner.getList());
        return landOwner;
    }

    public void playCard(){
        if (landOwner == player01) {
            goCard(landOwner, poker );
            goCard(player02, poker);
            goCard(player03, poker);
        } else if (landOwner == player02) {
            goCard(landOwner, poker);
            goCard(player03, poker);
            goCard(player02, poker);
        } else if (landOwner == player03) {
            goCard(landOwner, poker);
            goCard(player01, poker);
            goCard(player02, poker);
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

        boolean isValid = false;
        int cardIndex = -1;

        while (!u.getList().isEmpty()) { // 当手牌不为空时，循环进行出牌
            System.out.println("当前手牌为：" + u.getList());
            boolean isOut = false; // 重置标记
            List<Integer> outCards = new ArrayList<Integer>(); // 记录当前出的牌
            while (!isOut) { // 玩家一个一个输入牌，直到输入“出牌”
                Scanner scanner = new Scanner(System.in);
                System.out.println("请输入一张牌（输入“出牌”结束）：");
                String input = scanner.nextLine();
                if (input.equals("出牌")) {
                    isOut = true;
                    break;
                }
                //判断牌库中是否有该牌
                Integer inputIndex = turnStringToInteger(input);
                Integer index = p.getPokerNumber().get(inputIndex);
                if (index != null) {
                    outCards.add(index);
                } else {
                    System.out.println("无效牌，请重新输入。");
                }
            }
            if (!outCards.isEmpty()) { // 玩家出了牌
                if (judgeRight(outCards)) { // 判断牌型是否合法
                    for (Integer card : outCards) {
                        u.getList().remove(card); // 从手牌中删除出的牌
                    }
                    System.out.println("出牌成功。");
                } else {
                    System.out.println("出牌不合法，请重新出牌。");
                }
            } else { // 玩家选择不出牌
                System.out.println("玩家选择不出牌。");
            }
        }
        System.out.println("游戏结束。");
    }

    //定义一个方法用于将输入的字符串转换为牌库中的索引
    public Integer turnStringToInteger(String input){
        for (int i = 0; i < poker.getPokerCard().size(); i++) {
            if (poker.getPokerCard().get(i).equals(input)) {
                return i;
            }
        }
        return -1;
    }




    //定义一个方法,利用Judge判断牌型是否合法
    public boolean judgeRight(List<Integer> list){
        CardType cardType = judgeType(list);
        if (cardType == CardType.ct0){
            return false;
        }else {
            return true;
        }
    }

    //判断牌型
    public CardType judgeType(List<Integer> cards) {
        // 获取卡牌数量
        int size = cards.size();
        if (size == 0 || size > 8) {
            // 如果卡牌数量为0或大于8张，则返回无效牌型
            return CardType.ct0;
        }

        // 统计卡牌数字出现次数
        Map<Integer, Integer> counts = new HashMap<>();
        for (int card : cards) {
            // 获取卡牌数字
            int number = card % 100;
            // 统计卡牌数字出现次数
            counts.put(number, counts.getOrDefault(number, 0) + 1);
        }

        Set<Integer> numbers = counts.keySet();// 获取卡牌数字的集合
        int distinctCount = numbers.size();// 获取卡牌数字的不同个数
        int maxCount = Collections.max(counts.values());// 获取卡牌数字出现次数的最大值

        switch (size) {
            case 1:
                return CardType.ct1;// 单张
            case 2:
                if (distinctCount == 1 && maxCount == 2 && cards.get(0) == 52 && cards.get(1) == 53) {
                    return CardType.cthj; // 王炸
                } else if (distinctCount == 1 && maxCount == 2) {
                    return CardType.ct2;// 对子
                }
                return CardType.ct0;
            case 3:
                if (distinctCount == 1 && maxCount == 3) {
                    return CardType.ct3;// 三不带
                }
                return CardType.ct0;
            case 4:
                if (distinctCount == 2) {
                    if (maxCount == 3) {
                        return CardType.ct31;// 三带一
                    } else if (maxCount == 4) {
                        return CardType.ct4; // 炸弹
                    }
                }
                return CardType.ct0;
            case 5:
                if (distinctCount == 2 && maxCount == 3) {
                    return CardType.ct32;// 三带二
                } else if (isStraight(cards)) {
                    return CardType.ct123;// 顺子
                }
                return CardType.ct0;
            case 6:
                if (isStraight(cards) && isPairStraight(cards)) {
                    return CardType.ct1122;// 连对
                } else if (distinctCount == 1 && maxCount == 4) {
                    return CardType.ct4;// 四带二（单）
                } else if (distinctCount == 2 && maxCount == 3) {
                    return CardType.ct31;// 四带二（单）
                } else if (distinctCount == 3 && maxCount == 3) {
                    return CardType.ct411; // 飞机不带
                } else if (distinctCount == 2 && maxCount == 4) {
                    return CardType.ct422;// 四带二（对）
                }
                return CardType.ct0;
            case 7:
                if (isStraight(cards)) {
                    return CardType.ct123;// 顺子
                }
                return CardType.ct0;
            case 8:
                if (isStraight(cards) && isPairStraight(cards)) {
                    return CardType.ct1122;//连队
                } else if (isPlane(cards)) {
                    return CardType.ct111222;//飞机
                } else if (isPlaneWithSingle(cards)) {
                    return CardType.ct11122234;//飞机带单牌
                } else if (isPlaneWithPair(cards)) {
                    return CardType.ct1112223344;//飞机带对子
                }
        }
        return CardType.ct0;
    }

    //判断卡牌是否为飞机
    //cards 待判断的卡牌列表
    //return 是否为飞机
    public boolean isPlane(List<Integer> cards) {
        if (cards.size() % 3 != 0) {
            return false;
        }

        Map<Integer, Integer> counts = new HashMap<>();
        for (int card : cards) {
            int number = card % 100;
            counts.put(number, counts.getOrDefault(number, 0) + 1);
        }

        // 飞机必须满足3连牌，因此需要确保没有2和王
        if (counts.containsKey(15) || counts.containsKey(16) || counts.containsKey(17)) {
            return false;
        }

        int count = 0;
        for (int number : counts.keySet()) {
            if (counts.get(number) >= 3) {
                count++;
            }
        }

        return count >= 2 && isStraight(cards);
    }

    //判断卡牌是否为带单飞机
    // param cards 待判断的卡牌列表
    // return 是否为带单飞机
    public boolean isPlaneWithSingle(List<Integer> cards) {
        if (cards.size() % 4 != 0) {
            return false;
        }

        Map<Integer, Integer> counts = new HashMap<>();
        for (int card : cards) {
            int number = card % 100;
            counts.put(number, counts.getOrDefault(number, 0) + 1);
        }

        // 带单飞机必须满足3连牌，因此需要确保没有2和王
        if (counts.containsKey(15) || counts.containsKey(16) || counts.containsKey(17)) {
            return false;
        }

        int count = 0;
        for (int number : counts.keySet()) {
            if (counts.get(number) >= 3) {
                count++;
            }
        }

        return count >= 2 && isStraight(cards) && hasNSameCards(cards, 1, 2);
    }

    //判断卡牌是否为带对飞机
    //param cards 待判断的卡牌列表
    //return 是否为带对飞机
    public boolean isPlaneWithPair(List<Integer> cards) {
        if (cards.size() % 5 != 0) {
            return false;
        }

        Map<Integer, Integer> counts = new HashMap<>();
        for (int card : cards) {
            int number = card % 100;
            counts.put(number, counts.getOrDefault(number, 0) + 1);
        }

        // 带对飞机必须满足3连牌，因此需要确保没有2和王
        if (counts.containsKey(15) || counts.containsKey(16) || counts.containsKey(17)) {
            return false;
        }

        int count = 0;
        for (int number : counts.keySet()) {
            if (counts.get(number) >= 3) {
                count++;
            }
        }

        return count >= 2 && isStraight(cards) && hasNSameCards(cards, 2, 2);
    }

    //判断给定的牌是否为顺子。
    //cards 牌列表
    //如果牌列表为顺子，返回true；否则返回false。
    private boolean isStraight(List<Integer> cards) {
        // 牌的数量必须为5或更多
        if (cards.size() < 5) {
            return false;
        }

        // 首先将牌按牌面值从小到大排序
        Collections.sort(cards, Comparator.comparingInt(card -> card % 100));

        // 判断牌是否连续
        for (int i = 0; i < cards.size() - 1; i++) {
            int currentCard = cards.get(i) % 100;
            int nextCard = cards.get(i + 1) % 100;
            if (nextCard - currentCard != 1) {
                return false;
            }
        }

        return true;
    }

    //判断给定的牌是否为连对。
    //cards 牌列表
    //如果牌列表为连对，返回true；否则返回false。
    private boolean isPairStraight(List<Integer> cards) {
        // 牌的数量必须为偶数
        if (cards.size() % 2 != 0) {
            return false;
        }

        // 将牌按牌面值从小到大排序
        Collections.sort(cards, Comparator.comparingInt(card -> card % 100));

        // 判断牌是否为连续的对子
        for (int i = 0; i < cards.size() - 1; i += 2) {
            int currentCard = cards.get(i) % 100;
            int nextCard = cards.get(i + 1) % 100;
            if (nextCard - currentCard != 0 || (i > 0 && currentCard - cards.get(i - 1) % 100 != 0)) {
                return false;
            }
        }

        return true;
    }

    // 判断列表 cards 中是否存在 n 张相同的牌
    private boolean hasNSameCards(List<Integer> cards, int m, int n) {
        Map<Integer, Integer> countMap = new HashMap<>();
        for (int card : cards) {
            countMap.put(card, countMap.getOrDefault(card, 0) + 1);
        }
        for (int count : countMap.values()) {
            if (count == n) {
                return true;
            }
        }
        return false;
    }
}





