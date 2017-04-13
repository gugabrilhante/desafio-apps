package gustavo.brilhante.infoglobo;

import org.junit.Test;

import gustavo.brilhante.infoglobo.utils.DateUtils;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void dateTest() throws Exception {
        String dateBefore = "2017-03-08T12:52:43-0300";
        String dateResult = "08/03/2017 12:52";

        assertEquals(dateResult, DateUtils.convertDateFromService(dateBefore));

    }
}