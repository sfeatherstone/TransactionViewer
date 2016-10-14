package com.simonfea.transactionviewer;

import com.simonfea.transactionviewer.model.Rates;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class RatesTest {
    Rates rates;

    @Before
    public void setup() {
        rates = new Rates();
        rates.addRate("a", "b", 2.0);
        rates.addRate("b", "c", 3.0);
        rates.addRate("c", "e", 5.0);
        rates.addRate("a", "d", 7.0);
        rates.addRate("d", "a", 11.0);
    }
    @Test
    public void check_rates() throws Exception {
        assertEquals(new Double(2.0), rates.getRate("a","b"));
        assertEquals(new Double(6.0), rates.getRate("a","c"));
        assertEquals(new Double(15.0), rates.getRate("b","e"));
        assertEquals(null, rates.getRate("e","d"));
        assertEquals(new Double(330.0), rates.getRate("d","e"));
    }
}