package decorator;

/**
 * 美式咖啡
 *
 * @author Jion
 */
public class ShortBlackCoffee extends Coffee {

    /**
     * 构造器
     */
    public ShortBlackCoffee() {
        // 为其指定属性
        setDescription("美式咖啡");
        setPrice(4D);
    }
}
