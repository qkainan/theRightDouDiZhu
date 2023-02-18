package com.qkainan.domain;

import java.util.ArrayList;
import java.util.List;

public class Game {
    //卡牌类型
    public enum CardType {
        c1,//单牌。
        c2,//对子。
        c3,//3不带。
        c4,//炸弹。
        c31,//3带1。
        c32,//3带2。
        c411,//4带2个单，或者一对
        c422,//4带2对
        c123,//连子。
        c1122,//连队。
        c111222,//飞机。
        c11122234,//飞机带单排.
        c1112223344,//飞机带对子.
        c0//不能出牌
    }

    public class Model {
        //一组牌
        int value; //权值
        int num;// 手数 (几次能够走完，没有挡的情况下)
        List<String> a1 = new ArrayList<String>(); //单张
        List<String> a2 = new ArrayList<String>(); //对子
        List<String> a3 = new ArrayList<String>(); //3带
        List<String> a123 = new ArrayList<String>(); //连子
        List<String> a112233 = new ArrayList<String>(); //连牌
        List<String> a111222 = new ArrayList<String>(); //飞机
        List<String> a4 = new ArrayList<String>(); //炸弹
    }


}
