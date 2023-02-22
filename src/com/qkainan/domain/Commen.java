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
                    return CardType.ct0；
                }
        }
    }
}