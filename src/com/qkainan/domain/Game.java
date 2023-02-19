package com.qkainan.domain;
import java.util.*;

public class Game {
    Poker pk = new Poker();
    HashMap hm =pk.initCard();
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
    //定义一个方法用于出单张
}




