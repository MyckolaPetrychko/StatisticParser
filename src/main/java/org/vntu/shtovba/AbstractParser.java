package org.vntu.shtovba;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Myckola on 06.11.2017.
 */
public abstract class AbstractParser implements Parser{

    protected ObjectMapper mapper;

    public AbstractParser() {
        mapper = new ObjectMapper();
    }

    @Override
    public String parse(String query) {
        Map<String, Map<String, String>> result = parseOneQuery(query);
        String jsonRes = null;
        try {
            jsonRes = mapper.writeValueAsString(result);
        } catch(IOException e) {

        }
        return jsonRes;
    }

    @Override
    public String parseFromFile(File file) {
        List<Map<String, Map<String, String>>> result = new ArrayList<>();
        try (BufferedReader bf = new BufferedReader(new FileReader(file))) {
            String line;
            while((line = bf.readLine()) != null) {
                result.add(parseOneQuery(line));
            }
        } catch(IOException e) {

        }
        String resJson = null;
        try {
            resJson = mapper.writeValueAsString(result);
        } catch (IOException e) {

        }
        return resJson;
    }
}
