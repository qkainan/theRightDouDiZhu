package com.qkainan.domain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public enum CardType {
    ct1,//单牌。
    ct2,//对子。
    ct3,//3不带。
    ct4,//炸弹。
    ct31,//3带1。
    ct32,//3带2。
    ct411,//4带2个单，或者一对
    ct422,//4带2对
    ct123,//顺子。
    ct1122,//连队。
    ct111222,//飞机。
    ct11122234,//飞机带单牌.
    ct1112223344,//飞机带对子.
    cthj,//火箭
    ct0//无效
}

