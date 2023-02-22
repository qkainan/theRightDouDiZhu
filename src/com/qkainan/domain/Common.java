package com.qkainan.domain;

import java.util.*;
import java.util.stream.Collectors;

public class Common {
    // 存储卡牌大小的列表
    List group = new ArrayList<CardMagnitude>();

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