package org.hzz;

import org.hzz.autoconfigure.AutoConfiguration;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class ReadFile {
    private static final String LOCATION = "META-INF/spring/%s.imports";
    private static final String COMMENT_START = "#";
    public static void main(String[] args) {
        List<String> list = readFile(AutoConfiguration.class);
        list.forEach(System.out::println);
    }

    /**
     * Read the file from the classpath.
     * @param annotation
     * @return
     */
    public static List<String> readFile(Class<?> annotation){
        String location = String.format(LOCATION, annotation.getName());
        Enumeration<URL> urlsInClasspath = findUrlsInClasspath(ReadFile.class.getClassLoader(), location);
        List<String> result = new ArrayList<>();

        if (urlsInClasspath != null) {
            while (urlsInClasspath.hasMoreElements()) {
                URL url = urlsInClasspath.nextElement();
                result.addAll(readCandidateConfigurations(url));
            }
        }
        return result;
    }


    /**
     * Read the candidate configurations from the given URL.
     * @param url
     * @return
     */
    private static List<String> readCandidateConfigurations(URL url) {

        try(BufferedReader read = new BufferedReader(
            new InputStreamReader(getInputStream(url), StandardCharsets.UTF_8))){
            List<String> candidates = new ArrayList<>();
            String line;

            while ((line = read.readLine()) != null) {
                line = stripComment(line);
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }
                candidates.add(line);
            }

            return candidates;
        } catch (IOException e) {
            throw new IllegalArgumentException("Unable to load configurations from location [" + url + "]", e);
        }
    }

    private static String stripComment(String line) {
        int commentStart = line.indexOf(COMMENT_START);
        if (commentStart == -1) {
            return line;
        }
        return line.substring(0, commentStart);
    }

    /**
     * Get the input stream from the given URL.
     * @param url
     * @return
     */
    private static InputStream getInputStream(URL url) {
        try {
            return url.openStream();
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to open stream for URL [" + url + "]", ex);
        }
    }

    /**
     * Find all URLs in the classpath that match the given location.
     * @param classLoader
     * @param location
     * @return
     */
    public static Enumeration<URL> findUrlsInClasspath(ClassLoader classLoader, String location){
        try {
            return classLoader.getResources(location);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Failed to load configurations from location [" + location + "]", ex);
        }
    }
}
