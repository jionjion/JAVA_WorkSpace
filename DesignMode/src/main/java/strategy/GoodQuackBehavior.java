package strategy;

/**
 * @author Jion
 */
public class GoodQuackBehavior implements QuackBehavior {
    /**
     * 叫
     */
    @Override
    public void quack() {
        System.out.println("鸭子嘎嘎叫...");
    }
}
