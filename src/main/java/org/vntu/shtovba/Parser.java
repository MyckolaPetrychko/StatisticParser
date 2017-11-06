package org.vntu.shtovba;

import java.io.File;
import java.util.Map;

/**
 * Created by Myckola on 03.11.2017.
 */
public interface Parser {
    String parse(String query);
    String parseFromFile(File file);
    Map<String, Map<String, String>> parseOneQuery(String query);
}
