package org.vntu.shtovba;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by Myckola on 06.11.2017.
 */
public class WebOfScienceParser extends AbstractParser implements Parser {

    private String content;

    private static final String PUBLICATION_YEAR_ID = "PublicationYear";

    private static final String SCIENCE_CATEGORIES_ID = "JCRCategories";

    private static final String DOCUMENT_TYLES_ID = "DocumentType";

    private static final String ORG_ENHANCED_NAME_ID = "OrgEnhancedName";

    private static final String LIST_ITEM_CLASS = "refine-subitem-list";

    private static final String SUB_ITEM_CLASS = "refine-subitem";

    private static final String SUB_ITEM_TAG_TO_PARSE = "refine-subitem-title";

    public WebOfScienceParser() {
        try {
            content = new Scanner(new File("Web of Science.html")).useDelimiter("\\Z").next();
        } catch (IOException e) {

        }
    }

    public Map<String, Map<String, String>> parseOneQuery(String query) {
        Map<String, Map<String, String>> result = new HashMap<>();

        Document document = Jsoup.parse(content);
        Element pubYear = document.getElementById(PUBLICATION_YEAR_ID);
        Elements pubYears = pubYear.getElementsByClass(LIST_ITEM_CLASS).get(0).getElementsByClass(SUB_ITEM_CLASS);

        result.put("Publication Years", parseSubitemList(pubYears));

        Element scienceCategory = document.getElementById(SCIENCE_CATEGORIES_ID);
        Elements scienceCategories = scienceCategory.getElementsByClass(LIST_ITEM_CLASS).get(0).getElementsByClass(SUB_ITEM_CLASS);

        result.put("Science Categories", parseSubitemList(scienceCategories));

        Element documentType = document.getElementById(DOCUMENT_TYLES_ID);
        Elements documentTypeS = documentType.getElementsByClass(LIST_ITEM_CLASS).get(0).getElementsByClass(SUB_ITEM_CLASS);

        result.put("Document Types", parseSubitemList(documentTypeS));

        Element orgEnhanced = document.getElementById(ORG_ENHANCED_NAME_ID);
        Elements orgEnhancedS = orgEnhanced.getElementsByClass(LIST_ITEM_CLASS).get(0).getElementsByClass(SUB_ITEM_CLASS);

        result.put("Organizations Enhanced", parseSubitemList(orgEnhancedS));

        return result;
    }

    private Map<String, String> parseSubitemList(Elements itemsOfTheList) {
        Map<String, String> res = new HashMap<>();
        for (Element e : itemsOfTheList) {
            String item = e.getElementsByClass(SUB_ITEM_TAG_TO_PARSE).get(0).textNodes().get(0).text();
            int index = item.indexOf('(');
            String key = item.substring(0, index);
            String value = item.substring(index + 1, item.length() - 1);
            res.put(key, value);
        }
        return res;
    }
}
