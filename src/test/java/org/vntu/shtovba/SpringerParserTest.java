package org.vntu.shtovba;

import org.junit.Test;

import java.io.File;

import static org.junit.Assert.assertThat;
import static org.hamcrest.core.IsNot.not;
/**
 * Created by Myckola on 03.11.2017.
 */
public class SpringerParserTest {

    @Test
    public void testParse() {
        Parser parser = new SpringerParser();
        String res = parser.parse("fuzzy");
        assertThat(res, not(""));
    }

    @Test
    public void testParseFromFile() {
        File file = new File("test.txt");
        Parser parser = new SpringerParser();
        String res = parser.parseFromFile(file);
        assertThat(res, not(""));
    }
}
