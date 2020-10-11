package visitor;

/**
 * @author Jion
 */
public class Man extends People{

    public Man(String name){
        super();
        super.name = name;
    }

    @Override
    public void accept(Action action) {
        action.getManResult(this);
    }
}
