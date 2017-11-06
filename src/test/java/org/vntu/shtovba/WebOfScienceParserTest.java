package org.vntu.shtovba;

import org.junit.Test;

import static org.hamcrest.core.IsNot.not;
import static org.junit.Assert.assertThat;

/**
 * Created by Myckola on 06.11.2017.
 */
public class WebOfScienceParserTest {

    @Test
    public void testParse() {
        Parser parser = new WebOfScienceParser();
        String res = parser.parse("fuzzy");
        assertThat(res, not(""));
    }

}
