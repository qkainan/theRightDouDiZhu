package com.qkainan.domain;

import java.util.*;
import java.util.List;

public class Game {
    //初始化
    User player01 = new User();
    User player02 = new User();
    User player03 = new User();
    User landOwner = new User();
    List<User> userList = new ArrayList<User>();

    public List<User> getUserList() {
        return userList;
    }

    //创建四个集合用于存储三个玩家的手牌以及底牌
    List<Integer> player1 = new ArrayList<>();
    List<Integer> player2 = new ArrayList<>();
    List<Integer> player3 = new ArrayList<>();
    List<Integer> landowner = new ArrayList<>();

    List<Integer> diPai = new ArrayList<>();

    int score01 = 0;
    int score02 = 0;
    int score03 = 0;

    //游戏流程
    //初始化玩家
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

    //发牌,一人 17 张，留 3 张做底牌，在确定地主之前玩家不能看底牌。
    public void dealPorkCard(Poker poker) {
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

    public void lookCard(Poker poker) {
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
    public void qiangDiZhu(Poker poker) {
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

        //重置userList
        if (landOwner == player01){
            userList.add(player01);
            userList.add(player02);
            userList.add(player03);
        } else if (landOwner == player02) {
            userList.add(player02);
            userList.add(player03);
            userList.add(player01);
        } else if (landOwner == player03) {
            userList.add(player03);
            userList.add(player01);
            userList.add(player02);
        }
    }

    //定义一个方法用于进行核心流程
    //    1.首先将所有用户按顺序放入一个列表中，List<User> userList。
    //    2.取出列表中的第一个用户，调用goCard方法，传入该用户和poker对象，获取该用户出的牌，记录在一个列表中，List<Integer> playedCards。
    //    3.如果该用户出的牌playedCards为空，表示该用户选择不出牌，直接跳过该用户。
    //    4.否则，将playedCards传入checkCards方法，同时传入上一个用户出的牌lastPlayedCards和poker对象，获取checkCards的返回值checkResult。
    //    5.如果checkResult为1，表示该用户的牌比上一个用户的牌大，可以继续出牌。
    //    6.如果checkResult为-1，表示该用户的牌不合法，需要重新出牌，继续轮到该用户出牌。
    //    7.如果checkResult为0，表示该用户的牌不如上一个用户的牌大，需要重新出牌，继续轮到该用户出牌。
    //    8.将该用户出的牌playedCards从该用户的手牌中删除。
    //    9.将该用户从列表中移除，再将该用户加入列表的末尾，表示下一轮从下一个用户开始出牌。
    //    10.重复步骤2-9，直到只有一个用户的手牌不为空，该用户获胜。
    //    在这个实现中，使用一个变量currentPlayerIndex来记录当前出牌的用户在userList中的下标。
    //    每次出牌后，如果当前用户出的牌不为空，就将该用户从列表中移除，并将其添加到列表的末尾，表示下一轮从下一个用户开始出牌。
    //    如果该用户选择不出牌，就直接跳过该用户。当只剩下一个用户的手牌不为空时，该用户即为获胜者。
    public void playCard(List<User> userList , Poker poker) {

            List<Integer> lastPlayedCards = new ArrayList<>();
            int currentPlayerIndex = 0;
            while (userList.size() > 1) {
                User currentPlayer = userList.get(currentPlayerIndex);
                List<Integer> playedCards = goCard(currentPlayer, poker);
                if (playedCards.isEmpty()) {
                    currentPlayerIndex = (currentPlayerIndex + 1) % userList.size();
                    continue;
                }
                int checkResult = checkCards(playedCards, lastPlayedCards, poker);
                if (checkResult == -1 || checkResult == 0) {
                    continue;
                }
                lastPlayedCards = playedCards;
                currentPlayer.getList().removeAll(playedCards);
                userList.remove(currentPlayer);
                userList.add(currentPlayer);
                currentPlayerIndex = (currentPlayerIndex + 1) % userList.size();
            }
            System.out.println("游戏结束，获胜者是：" + userList.get(0).getName());
        }



    //进行游戏流程的工具
    //定义一个方法用于获取每张牌的大小,利用pokerGroup方法
    public Integer getMagnitude(Integer index , Poker poker){
        //得到index在pokerNumber中的位置
        int count = 0;
        while (index != poker.getPokerNumber().get(count)){
            count++;
        }
        //index对应的枚举类型
        CardMagnitude index_cardMagnitude = poker.getPokerGroup().get(count);
        return index_cardMagnitude.ordinal();
    }

    //定义一个方法用于洗牌
    //使用Collections中的方法shuffle(List)方法,对poker的索引进行洗牌
    public void shuffleCard(Poker poker) {
        Collections.shuffle(poker.getPokerNumber());
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
            System.out.println("叫牌后分数为:" + score);
        } else if (str.equals(two)) {
            score = score + 2;
            System.out.println("叫牌后分数为:" + score);
        } else if (str.equals(three)) {
            score = score + 3;
            System.out.println("叫牌后分数为:" + score);
        } else if (str.equals(no)) {
            score = score + 0;
            System.out.println("叫牌后分数为:" + score);
        } else {
            System.out.println("输入错误，请重新输入");
            jiaoPai(score);
        }
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

    //定义一个方法用于玩家出牌
    //出牌思路:
    //1.首先定义一个变量，用于记录当前玩家是否输入“出牌”的标记，初始值为false。
    //2.进入出牌环节后，玩家每输入一张牌，先判断该牌是否为“出牌”。
    //    如果是，将标记置为true，退出输入循环。
    //    如果不是，将该牌转化为map中对应的索引,并检查玩家手牌中有无该牌。
    //3.当玩家输入“出牌”后，开始进行牌型判断。
    //4.如果牌型合法，则将玩家出的牌从手牌中删除，更新当前手牌。
    //5.如果牌型不合法，则提示玩家重新输入出牌。
    //6.返回出牌暂存区，以便于下一个出牌玩家判断牌型
    public List<Integer> goCard(User u, Poker poker) {
        System.out.println("当前手牌为：");
        seeCard(poker.getPokerCard(), u.getList());

        //标记
        boolean isOut = false;

        //出牌暂存区
        List<Integer> playedCards = new ArrayList<>();

        //输入
        System.out.println("请输入一张牌（输入“出牌”、“不出牌”结束）：");
        Scanner in = new Scanner(System.in);
        String input = in.nextLine();
        if (input.equals("不出牌")) {
            System.out.println("玩家选择不出牌。");
            return playedCards;
        } else {
            while (!isOut) {
                if (input.equals("出牌")) {
                    if (judgeRight(playedCards)){
                        isOut = true;
                        System.out.println("出牌成功。");
                    }else {
                        System.out.println("出牌不合法，请重新出牌。");
                        playedCards.clear();
                        playedCards = goCard(u, poker); // 递归调用自身，重新出牌
                    }
                } else {
                    int playedCards_index = turnStringToInteger(input, poker);
                    playedCards.add(playedCards_index);
                    playedCards = goCard(u, poker); // 递归调用自身，重新出牌
                }
            }
        }
        return playedCards;
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

    //定义一个方法用于将输入的字符串转换为牌库中的索引
    public Integer turnStringToInteger(String input , Poker poker){
        Integer it = -1;
        for (int i = 0; i < poker.getPokerCard().size(); i++) {
            if (poker.getPokerCard().get(i).equals(input)) {
                it = i;
            }
        }
        return it;
    }

    //定义一个方法用于找到玩家出牌时，出现次数最多的牌中最大的牌的索引
    public Integer findMaxCard(List<Integer> list) {
        Map<Integer, Integer> map = new HashMap<>();
        for (Integer it : list) {
            Integer i = 1; //定义一个计数器，用来记录重复数据的个数
            if (map.get(it) != null) {
                i = map.get(it) + 1;
            }
            map.put(it, i);
        }
        //System.out.println("重复数据的个数："+map.toString());
        int maxCount = 0;
        Integer maxIndex = null;
        for (Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();//出现的次数
                maxIndex = entry.getKey();//要求的索引
            }
        }
        return maxIndex;
    }

    //检查牌的是否能出
    public int checkCards(List<Integer> current, List<Integer> previous , Poker poker) {
        CardType cType = judgeType(previous);

        //王炸
        if (cType == CardType.cthj) {
            if (previous.size() == 2)
                return 0;
            if (current.size() == 2)
                return 1;
        }

        //如果张数不同直接过滤
        if (current != previous)
            return 0;
        //比较此时的出牌类型
        if (judgeType(current) != judgeType(previous)) {
            return -1;
        }
        //比较出的牌是否要大
        if (getMagnitude(findMaxCard(current) , poker) <= getMagnitude(findMaxCard(previous) , poker)) {
            return 0;
        } else {
            return 1;
        }

    }




    //判断牌型
    public CardType judgeType(List<Integer> cards) {
        //最后返回的类型
        CardType cardType = CardType.ct0;
        // 获取卡牌数量
        int size = cards.size();
        if (size == 0 || size > 8) {
            // 如果卡牌数量为0或大于8张，则返回无效牌型
            cardType = CardType.ct0;
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
                cardType = CardType.ct1;
                break;// 单张
            case 2:
                if (distinctCount == 1 && maxCount == 2 && cards.get(0) == 52 && cards.get(1) == 53) {
                    cardType = CardType.cthj;
                    break; // 王炸
                } else if (distinctCount == 1 && maxCount == 2) {
                    cardType = CardType.ct2;
                    break;// 对子
                }else {
                    cardType = CardType.ct0;
                }
                case 3:
                if (distinctCount == 1 && maxCount == 3) {
                    cardType = CardType.ct3;
                    break;// 三不带
                }else {
                    cardType = CardType.ct0;
                }
            case 4:
                if (distinctCount == 2) {
                    if (maxCount == 3) {
                        cardType = CardType.ct31;
                        break;// 三带一
                    } else if (maxCount == 4) {
                        break; // 炸弹
                    }
                }else {
                    cardType = CardType.ct0;
                }
            case 5:
                if (distinctCount == 2 && maxCount == 3) {
                    cardType = CardType.ct32;
                    break;// 三带二
                } else if (isStraight(cards)) {
                    cardType = CardType.ct123;
                    break;// 顺子
                }else {
                    cardType = CardType.ct0;
                }
            case 6:
                if (isStraight(cards) && isPairStraight(cards)) {
                    cardType = CardType.ct1122;
                    break;// 连对
                } else if (distinctCount == 1 && maxCount == 4) {
                    cardType = CardType.ct4;
                    break;// 四带二（单）
                } else if (distinctCount == 2 && maxCount == 3) {
                    cardType = CardType.ct31;
                    break;// 四带二（单）
                } else if (distinctCount == 3 && maxCount == 3) {
                    cardType = CardType.ct411;
                    break; // 飞机不带
                } else if (distinctCount == 2 && maxCount == 4) {
                    cardType = CardType.ct422;
                    break;// 四带二（对）
                }else {
                    cardType = CardType.ct0;
                }
            case 7:
                if (isStraight(cards)) {
                    cardType = CardType.ct123;
                    break;// 顺子
                }else {
                    cardType = CardType.ct0;
                }
            case 8:
                if (isStraight(cards) && isPairStraight(cards)) {
                    cardType = CardType.ct1122;
                    break;//连队
                } else if (isPlane(cards)) {
                    cardType = CardType.ct111222;
                    break;//飞机
                } else if (isPlaneWithSingle(cards)) {
                    cardType = CardType.ct11122234;
                    break;//飞机带单牌
                } else if (isPlaneWithPair(cards)) {
                    cardType = CardType.ct1112223344;
                    break;//飞机带对子
                }
        }
        return cardType;
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





