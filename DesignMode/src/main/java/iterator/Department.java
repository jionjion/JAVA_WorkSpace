package iterator;

/**
 *  学系,需要遍历的成员
 * @author Jion
 */
public class Department {

    /** 名字 */
    private String name;

    /** 描述 */
    private String description;

    public Department(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
