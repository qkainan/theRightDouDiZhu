import com.qkainan.domain.Game;
import com.qkainan.domain.Poker;

public class test {
    public static void main(String[] args) {

        Poker p = new Poker();
        Game g = new Game();

        p.creatCardBase();
        g.initUser();
        g.dealPorkCard();
        g.shuffleCard();
        g.lookCard();
        g.qiangDiZhu();

    }
}
