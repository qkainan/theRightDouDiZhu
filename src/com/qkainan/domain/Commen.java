package com.qkainan.domain;

import java.util.*;

public class Common {
    List group = new ArrayList<CardMagnitude>();

    //判断牌型
    public CardType getCardType(List<Integer> cards) {
        int size = cards.size();
        if (size == 0 || size > 8) {
            return CardType.ct0; // 如果卡牌数量为0或大于8张，则返回无效牌型
        }

        // 统计卡牌数字出现次数
        Map<Integer, Integer> counts = new HashMap<>();
        for (int card : cards) {
            int number = card % 100;
            counts.put(number, counts.getOrDefault(number, 0) + 1);
        }

        Set<Integer> numbers = counts.keySet();
        int distinctCount = numbers.size();
        int maxCount = Collections.max(counts.values());

        switch (size) {
            case 1:
                return CardType.ct1;
            case 2:
                if (distinctCount == 1 && maxCount == 2 && cards.get(0) == 52 && cards.get(1) == 53) {
                    return CardType.cthj;
                } else if (distinctCount == 1 && maxCount == 2) {
                    return CardType.ct2;
                }
                return CardType.ct0;
            case 3:
                if (distinctCount == 1 && maxCount == 3) {
                    return CardType.ct3;
                }
                return CardType.ct0;
            case 4:
                if (distinctCount == 2) {
                    if (maxCount == 3) {
                        return CardType.ct31;
                    } else if (maxCount == 4) {
                        return CardType.ct4;
                    }
                }
                return CardType.ct0;
            case 5:
                if (distinctCount == 2 && maxCount == 3) {
                    return CardType.ct32;
                } else if (isStraight(cards)) {
                    return CardType.ct123;
                }
                return CardType.ct0;
            case 6:
                if (isStraight(cards) && isPairStraight(cards)) {
                    return CardType.ct1122;
                } else if (distinctCount == 1 && maxCount == 4) {
                    return CardType.ct4;
                } else if (distinctCount == 2 && maxCount == 3) {
                    return CardType.ct31;
                } else if (distinctCount == 3 && maxCount == 3) {
                    return CardType.ct411;
                } else if (distinctCount == 2 && maxCount == 4) {
                    return CardType.ct422;
                }
                return CardType.ct0;
            case 7:
                if (isStraight(cards)) {
                    return CardType.ct123;
                }
                return CardType.ct0;
            case 8:
                if (isStraight(cards) && isPairStraight(cards)) {
                    return CardType.ct1122;
                } else if (isPlane(cards)) {
                    return CardType.ct111222;
                } else if (isPlaneWithSingle(cards)) {
                    return CardType.ct11122234;
                } else if (isPlaneWithPair(cards)) {
                    return CardType.ct1112223344;
                }
        }
        return CardType.ct0;
    }

    /**
     * 判断给定的牌是否为顺子。
     *
     * @param cards 牌列表
     * @return 如果牌列表为顺子，返回true；否则返回false。
     */
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

    /**
     * 判断给定的牌是否为连对。
     *
     * @param cards 牌列表
     * @return 如果牌列表为连对，返回true；否则返回false。
     */
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

}