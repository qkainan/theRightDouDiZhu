import com.qkainan.domain.Game;
import com.qkainan.domain.Poker;

import java.util.*;

public class test {
    public static void main(String[] args) {

        Poker p = new Poker();
        Game g = new Game();

        p.creatCardBase();
        g.initUser();
        g.getPorkCard();
        g.shuffleCard();
        g.lookCard();
        g.qiangDiZhu();

    }
}
