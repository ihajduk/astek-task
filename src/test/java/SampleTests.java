import org.junit.Test;
import pl.parser.nbp.utils.StringParserUtil;

import java.math.BigDecimal;

/**
 * Created by iwha on 1/14/2017.
 */
public class SampleTests {
    private StringParserUtil stringParserUtil = new StringParserUtil();

    @Test
    public void test(){
        BigDecimal val = BigDecimal.valueOf(0.011111).setScale(4, BigDecimal.ROUND_UP);
        System.out.println(val);
    }
}
