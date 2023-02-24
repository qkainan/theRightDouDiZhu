import com.qkainan.domain.Game;
import com.qkainan.domain.Poker;

public class test {
    public static void main(String[] args) {

        Poker poker = new Poker();
        Game game= new Game();

        poker.creatCardBase();
        game.initUser();
        game.shuffleCard(poker);
        game.dealPorkCard(poker);
        game.lookCard(poker);
        game.qiangDiZhu(poker);

    }
}
