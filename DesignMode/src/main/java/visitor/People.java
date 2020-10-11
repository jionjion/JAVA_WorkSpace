package visitor;

/**
 *
 * @author Jion
 */
public abstract class People {

    protected String name;

    /** 提供方法,让访问者可以访问 */
    public abstract void accept(Action action);
}
