package visitor;

/**
 * @author Jion
 */
public abstract class Action {

    /** 获得一个男歌手的测评 */
    public abstract void getManResult(Man man);

    /** 获得一个女歌手的测评 */
    public abstract void getWoManResult(Woman woman);

}
