package com.beetle.bauhinia;

import com.google.gson.Gson;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

import org.apache.commons.io.FileUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

public class PrintDependenciesTest {
    @Test
    public void testPrint() throws IOException, JSONException {
        Gson gson = new Gson();
        final Dependency[] dependencies = gson.fromJson(getDependencies(), Dependency[].class);
        for (Dependency dependency : dependencies) {
            System.out.println(dependency);
        }
    }

    private String getDependencies() throws IOException, JSONException {
        File file = new File("imkit\\build\\publications\\maven\\pom-default.xml");
        if (file.exists()) {
            final String xml = FileUtils.readFileToString(file);
            XMLSerializer xmlSerializer = new XMLSerializer();
            JSON json = xmlSerializer.read(xml);
            JSONObject pom = new JSONObject(json.toString());
            JSONObject dependencies = pom.getJSONObject("dependencies");
            return dependencies.getJSONArray("dependency").toString();
        } else return "[]";
    }

    private static class Dependency {
        String groupId;
        String artifactId;
        String version;

        @Override
        public String toString() {//compile 'com.android.support:appcompat-v7:25.3.1'
            return String.format("compile '%s:%s:%s'", groupId, artifactId, version);
        }
    }
}
