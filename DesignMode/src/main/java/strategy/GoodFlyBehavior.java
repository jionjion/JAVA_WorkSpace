package strategy;

/**
 * @author Jion
 */
public class GoodFlyBehavior implements FlyBehavior {
    @Override
    public void fly() {
        System.out.println("擅长飞翔的鸭子...");
    }
}
