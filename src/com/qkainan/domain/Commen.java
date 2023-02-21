package com.qkainan.domain;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Delayed;

public class Common {

    //判断牌型
    public static judgeType.CardType jugdeType(List<Integer> list){
        //因为之前排序过所以比较好判断
        int len=list.size();
        //单牌,对子，3不带，4个一样炸弹
        if(len<=4)
        {	//如果第一个和最后个相同，说明全部相同
            if(list.size()>0 && Common.getValue(list.get(0))==Common.getValue(list.get(len-1)))
            {
                switch (len) {
                    case 1:
                        return judgeType.CardType.ct1;
                    case 2:
                        return judgeType.CardType.ct2;
                    case 3:
                        return judgeType.CardType.ct3;
                    case 4:
                        return judgeType.CardType.ct4;
                }
            }
            //双王,化为对子返回
//            if(len==2&&Common.getColor(list.get(1))==5)
//                return judgeType.CardType.ct2;
            //当第一个和最后个不同时,3带1
            if(len==4 &&((Common.getValue(list.get(0))==Common.getValue(list.get(len-2)))||
                    Common.getValue(list.get(1))==Common.getValue(list.get(len-1))))
                return judgeType.CardType.ct31;
            else {
                return judgeType.CardType.ct0;
            }
        }
        //当5张以上时，连字，3带2，飞机，2顺，4带2等等
        if(len>=5)
        {//现在按相同数字最大出现次数
            Card_index card_index=new Card_index();
            for(int i=0;i<4;i++)
                card_index.a[i]=new ArrayList<Integer>();
            //求出各种数字出现频率
            Common.getMax( card_index,list); //a[0,1,2,3]分别表示重复1,2,3,4次的牌
            //3带2 -----必含重复3次的牌
            if(card_index.a[2].size()==1 &&card_index.a[1].size()==1 && len==5)
                return judgeType.CardType.ct32;
            //4带2(单,双)
            if(card_index.a[3].size()==1 && len==6)
                return judgeType.CardType.ct411;
            if(card_index.a[3].size()==1 && card_index.a[1].size()==2 &&len==8)
                return judgeType.CardType.ct422;
            //单连,保证不存在王
            if((Common.getColor(list.get(0))!=5)&&(card_index.a[0].size()==len) &&
                    (Common.getValue(list.get(0))-Common.getValue(list.get(len-1))==len-1))
                return judgeType.CardType.ct123;
            //连队
            if(card_index.a[1].size()==len/2 && len%2==0 && len/2>=3
                    &&(Common.getValue(list.get(0))-Common.getValue(list.get(len-1))==(len/2-1)))
                return judgeType.CardType.ct1122;
            //飞机
            if(card_index.a[2].size()==len/3 && (len%3==0) &&
                    (Common.getValue(list.get(0))-Common.getValue(list.get(len-1))==(len/3-1)))
                return judgeType.CardType.ct111222;
            //飞机带n单,n/2对
            if(card_index.a[2].size()==len/4 &&
                    ((Integer)(card_index.a[2].get(len/4-1))-(Integer)(card_index.a[2].get(0))==len/4-1))
                return judgeType.CardType.ct11122234;

            //飞机带n双
            if(card_index.a[2].size()==len/5 && card_index.a[2].size()==len/5 &&
                    ((Integer)(card_index.a[2].get(len/5-1))-(Integer)(card_index.a[2].get(0))==len/5-1))
                return judgeType.CardType.ct1112223344;

        }
        return judgeType.CardType.ct0;
    }

    //返回值
    public static int getValue(Integer card){
        int i= Integer.parseInt(card.name.substring(2,card.name.length()));
        if(card.name.substring(2,card.name.length()).equals("2"))
            i+=13;
        if(card.name.substring(2,card.name.length()).equals("1"))
            i+=13;
        if(Common.getColor(card)==5)
            i+=2;//是王
        return i;
    }
    //得到最大相同数
    public static void getMax(Card_index card_index,List<Integer> list){
        int count[]=new int[14];//1-13各算一种,王算第14种
        for(int i=0;i<14;i++)
            count[i]=0;
        for(int i=0,len=list.size();i<len;i++){
            if(Common.getColor(list.get(i))==5)
                count[13]++;
            else
                count[Common.getValue(list.get(i))-1]++;
        }
        for(int i=0;i<14;i++)
        {
            switch (count[i]) {
                case 1:
                    card_index.a[0].add(i+1);
                    break;
                case 2:
                    card_index.a[1].add(i+1);
                    break;
                case 3:
                    card_index.a[2].add(i+1);
                    break;
                case 4:
                    card_index.a[3].add(i+1);
                    break;
            }
        }
    }
    //拆牌
    public static Model getModel(List<Integer> list){
        //先复制一个list
        List list2=new ArrayList<Integer>(list);
        Model model=new Model();
        //------先拆炸弹
        Common.getBoomb(list2, model); //ok
        //------拆3带
        Common.getThree(list2, model);
        //拆飞机
        Common.getPlane(list2, model);
        //------拆对子
        Common.getTwo(list2, model);
        //拆连队
        Common.getTwoTwo(list2, model);
        //拆顺子
        Common.get123(list2, model);
        //拆单
        Common.getSingle(list2, model);
        return model;
    }
    //拆连子
    public static void get123(List<Integer> list,Model model){
        List<Card> del=new ArrayList<Card>();//要删除的Cards
        if(list.size()>0&&(Common.getValue(list.get(0))<7 ||Common.getValue(list.get(list.size()-1))>10))
            return;
        if(list.size()<5)
            return;
        for(int i=0,len=list.size();i<len;i++)
        {
            int k=i;
            for(int j=i;j<len;j++){
                if(Common.getValue(list.get(i))-Common.getValue(list.get(j))==j-i)
                {
                    k=j;
                }
            }
            if(k-i>=4)
            {
                String s="";
                for(int j=i;j<k;j++)
                {
                    s+=list.get(j).name+",";
                    del.add(list.get(j));
                }
                s+=list.get(k).name;
                del.add(list.get(k));
                model.a123.add(s);
                i=k;
            }
        }
        list.removeAll(del);
    }
    //拆双顺
    public static void getTwoTwo(List<Integer> list,Model model){
        List<String> del=new ArrayList<String>();//要删除的Cards
        //从model里面的对子找
        List<String> l=model.a2;
        if(l.size()<3)
            return ;
        Integer s[]=new Integer[l.size()];
        for(int i=0,len=l.size();i<len;i++){
            String []name=l.get(i).split(",");
            s[i]=Integer.parseInt(name[0].substring(2,name[0].length()));
        }
        //s0,1,2,3,4  13,9,8,7,6
        for(int i=0,len=l.size();i<len;i++){
            int k=i;
            for(int j=i;j<len;j++)
            {
                if(s[i]-s[j]==j-i)
                    k=j;
            }
            if(k-i>=2)//k=4 i=1
            {//说明从i到k是连队
                String ss="";
                for(int j=i;j<k;j++)
                {
                    ss+=l.get(j)+",";
                    del.add(l.get(j));
                }
                ss+=l.get(k);
                model.a112233.add(ss);
                del.add(l.get(k));
                i=k;
            }
        }
        l.removeAll(del);
    }
    //拆飞机
    public static void getPlane(List<Integer> list,Model model){
        List<String> del=new ArrayList<String>();//要删除的Cards
        //从model里面的3带找
        List<String> l=model.a3;
        if(l.size()<2)
            return ;
        Integer s[]=new Integer[l.size()];
        for(int i=0,len=l.size();i<len;i++){
            String []name=l.get(i).split(",");
            s[i]=Integer.parseInt(name[0].substring(2,name[0].length()));
        }
        for(int i=0,len=l.size();i<len;i++){
            int k=i;
            for(int j=i;j<len;j++)
            {
                if(s[i]-s[j]==j-i)
                    k=j;
            }
            if(k!=i)
            {//说明从i到k是飞机
                String ss="";
                for(int j=i;j<k;j++)
                {
                    ss+=l.get(j)+",";
                    del.add(l.get(j));
                }
                ss+=l.get(k);
                model.a111222.add(ss);
                del.add(l.get(k));
                i=k;
            }
        }
        l.removeAll(del);
    }
    //拆炸弹
    public static void getBoomb(List<Integer> list,Model model){
        List<Card> del=new ArrayList<Card>();//要删除的Cards
        //王炸
        if(list.size()>=2 &&Common.getColor(list.get(0))==5 && Common.getColor(list.get(1))==5)
        {
            model.a4.add(list.get(0).name+","+list.get(1).name); //按名字加入
            del.add(list.get(0));
            del.add(list.get(1));
        }
        //如果王不构成炸弹咋先拆单
        if(Common.getColor(list.get(0))==5&&Common.getColor(list.get(1))!=5)
        {
            del.add(list.get(0));
            model.a1.add(list.get(0).name);
        }
        list.removeAll(del);
        //一般的炸弹
        for(int i=0,len=list.size();i<len;i++){
            if(i+3<len && Common.getValue(list.get(i))==Common.getValue(list.get(i+3)))
            {
                String s=list.get(i).name+",";
                s+=list.get(i+1).name+",";
                s+=list.get(i+2).name+",";
                s+=list.get(i+3).name;
                model.a4.add(s);
                for(int j=i;j<=i+3;j++)
                    del.add(list.get(j));
                i=i+3;
            }
        }
        list.removeAll(del);
    }
    //拆3带
    public static void getThree(List<Integer> list,Model model){
        List<Card> del=new ArrayList<Card>();//要删除的Cards
        //连续3张相同
        for(int i=0,len=list.size();i<len;i++){
            if(i+2<len&&Common.getValue(list.get(i))==Common.getValue(list.get(i+2)))
            {
                String s=list.get(i).name+",";
                s+=list.get(i+1).name+",";
                s+=list.get(i+2).name;
                model.a3.add(s);
                for(int j=i;j<=i+2;j++)
                    del.add(list.get(j));
                i=i+2;
            }
        }
        list.removeAll(del);
    }
    //拆对子
    public static void getTwo(List<Integer> list,Model model){
        List<Card> del=new ArrayList<Card>();//要删除的Cards
        //连续2张相同
        for(int i=0,len=list.size();i<len;i++){
            if(i+1<len&&Common.getValue(list.get(i))==Common.getValue(list.get(i+1)))
            {
                String s=list.get(i).name+",";
                s+=list.get(i+1).name;
                model.a2.add(s);
                for(int j=i;j<=i+1;j++)
                    del.add(list.get(j));
                i=i+1;
            }
        }
        list.removeAll(del);
    }
    //拆单牌
    public static void getSingle(List<Integer> list,Model model){
        List<Card> del=new ArrayList<Card>();//要删除的Cards
        //1
        for(int i=0,len=list.size();i<len;i++){
            model.a1.add(list.get(i).name);
            del.add(list.get(i));
        }
        list.removeAll(del);
    }
    //隐藏之前出过的牌
    public static void hideCards(List<Integer> list){
        for(int i=0,len=list.size();i<len;i++){
            list.get(i).setVisible(false);
        }
    }
    //检查牌的是否能出
    public static int checkCards(List<Integer> c,List<Integer>[] current){
        //找出当前最大的牌是哪个电脑出的,c是点选的牌
        List<Card> currentlist=(current[0].size()>0)?current[0]:current[2];
        judgeType.CardType cType=Common.jugdeType(c);
        //如果张数不同直接过滤
        if(cType!=judgeType.CardType.ct4&&c.size()!=currentlist.size())
            return 0;
        //比较我的出牌类型
        if(Common.jugdeType(c)!=Common.jugdeType(currentlist))
        {

            return 0;
        }
        //比较出的牌是否要大
        //王炸弹
        if(cType== judgeType.CardType.ct4)
        {
            if(c.size()==2)
                return 1;
            if(currentlist.size()==2)
                return 0;
        }
        //单牌,对子,3带,4炸弹
        if(cType==judgeType.CardType.ct1||cType==judgeType.CardType.ct2||cType==judgeType.CardType.ct3||cType==judgeType.CardType.ct4){
            if(Common.getValue(c.get(0))<=Common.getValue(currentlist.get(0)))
            {
                return 0;
            }else {
                return 1;
            }
        }
        //顺子,连队，飞机裸
        if(cType==judgeType.CardType.ct123||cType==judgeType.CardType.ct1122||cType==judgeType.CardType.t)
        {
            if(Common.getValue(c.get(0))<=Common.getValue(currentlist.get(0)))
                return 0;
            else
                return 1;
        }
        //按重复多少排序
        //3带1,3带2 ,飞机带单，双,4带1,2,只需比较第一个就行，独一无二的
        if(cType==judgeType.CardType.ct31||cType==judgeType.CardType.ct32||cType==judgeType.CardType.ct411||cType==judgeType.CardType.c422
                ||cType==judgeType.CardType.ct11122234||cType==judgeType.CardType.ct1112223344){
            List<Card> a1=Common.getOrder2(c); //我出的牌
            List<Card> a2=Common.getOrder2(currentlist);//当前最大牌
            if(Common.getValue(a1.get(0))<Common.getValue(a2.get(0)))
                return 0;
        }
        return 1;
    }
    //按照重复次数排序
    public static List getOrder2(List<Integer> list){
        List<Card> list2=new ArrayList<Card>(list);
        List<Card> list3=new ArrayList<Card>();
        List<Integer> list4=new ArrayList<Integer>();
        int len=list2.size();
        int a[]=new int[20];
        for(int i=0;i<20;i++)
            a[i]=0;
        for(int i=0;i<len;i++)
        {
            a[Common.getValue(list2.get(i))]++;
        }
        int max=0;
        for(int i=0;i<20;i++){
            max=0;
            for(int j=19;j>=0;j--){
                if(a[j]>a[max])
                    max=j;
            }

            for(int k=0;k<len;k++){
                if(Common.getValue(list2.get(k))==max){
                    list3.add(list2.get(k));
                }
            }
            list2.remove(list3);
            a[max]=0;
        }
        return list3;
    }
}
class Card_index{
    List a[]=new ArrayList[4];//单张
}


