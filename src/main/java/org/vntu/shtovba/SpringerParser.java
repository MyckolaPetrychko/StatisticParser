package org.vntu.shtovba;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Myckola on 03.11.2017.
 */
public class SpringerParser extends AbstractParser implements Parser {

    private static final String QUERY_NAME                  = "query";

    private static final String URL                         = "https://link.springer.com/search";

    private static final String CONTENT_PARSE_TAG_NAME      = "ol";

    private static final String CONTENT_ITEM_PARSE_TAG_NAME = "li";

    private static final String CONTENT_ID                  = "kb-nav--aside";

    private static final String CONTENT_TYPE_ID             = "content-type-facet";

    private static final String DISCIPLINE_ID               = "discipline-facet";

    private static final String SUBDISCIPLINE_ID            = "sub-discipline-facet";

    private static final String LANGUAGE_ID                 = "language-facet";

    private static final String TITLE_CONTENT_CLASS         = "facet-title";

    private static final String AMOUNT_CONTENT_CLASS        = "facet-amount";

    public SpringerParser() {

    }

    public Map<String, Map<String, String>> parseOneQuery(String oneQuery) {
        Connection connection = Jsoup.connect(URL + "?" + QUERY_NAME + "=" + oneQuery);
        Map<String, Map<String, String>> result = new HashMap<>();
        try {
            Document document = connection.get();
            Element body = document.body();
            Element content = body.getElementById(CONTENT_ID);

            Element contentType = content.getElementById(CONTENT_TYPE_ID);
            Elements contentList = contentType.getElementsByTag(CONTENT_PARSE_TAG_NAME);
            result.put("Content Type", parseContentType(contentList.get(0).getElementsByTag(CONTENT_ITEM_PARSE_TAG_NAME)));

            Element discipline = content.getElementById(DISCIPLINE_ID);
            Elements disciplineList = discipline.getElementsByTag(CONTENT_PARSE_TAG_NAME);
            result.put("Discipline", parseContentType(disciplineList.get(0).getElementsByTag(CONTENT_ITEM_PARSE_TAG_NAME)));

            Element subDiscipline = content.getElementById(SUBDISCIPLINE_ID);
            Elements subDisciplineList = subDiscipline.getElementsByTag(CONTENT_PARSE_TAG_NAME);
            result.put("SubDiscipline", parseContentType(subDisciplineList.get(0).getElementsByTag(CONTENT_ITEM_PARSE_TAG_NAME)));

            Element language = content.getElementById(LANGUAGE_ID);
            Elements languageList = language.getElementsByTag(CONTENT_PARSE_TAG_NAME);
            result.put("Language", parseContentType(languageList.get(0).getElementsByTag(CONTENT_ITEM_PARSE_TAG_NAME)));

        } catch (IOException e) {

        }
        return result;
    }

    private Map<String, String> parseContentType(Elements itemsOfTheList) {
        Map<String, String> res = new HashMap<>();
        for (Element e : itemsOfTheList) {
            res.put(e.getElementsByClass(TITLE_CONTENT_CLASS).get(0).textNodes().get(0).text(),
                    e.getElementsByClass(AMOUNT_CONTENT_CLASS).get(0).textNodes().get(0).text());
        }
        return res;
    }

}
