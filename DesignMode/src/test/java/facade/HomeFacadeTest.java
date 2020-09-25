package facade;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Jion
 */
public class HomeFacadeTest {

    @Test
    public void test(){
        HomeFacade homeFacade = new HomeFacade();
        homeFacade.ready();
        homeFacade.enjoy();
        homeFacade.end();
    }
}