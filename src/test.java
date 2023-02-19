import com.qkainan.domain.Game;
import com.qkainan.domain.Poker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        Poker p = new Poker();
        Game g = new Game();
        //定义一个集合用于存储每张牌的分值
        List sc = new ArrayList();

        p.initUser();
        p.initCard();
        g.groupIndex(sc);
        p.shuffleCard();
        p.getPorkCard();
        p.lookCard();
        p.qiangDiZhu();
        p.playCard();


    }
}
