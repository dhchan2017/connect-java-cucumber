package nz.co.clearpoint.service;

import org.apache.commons.io.FileUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Collection;
import java.util.Iterator;


public class CucumberReportMerger {

    public static void main(String[] args) throws Throwable {
        File reportsDirectory = new File(args[0]);
        File outputDirectory = new File(args[1]);

        CucumberReportMerger merger = new CucumberReportMerger();

        //merges data setup results only if there are no main results (therefor that means that data setup failed)

        if (reportsDirectory.exists()) {
            merger.mergeJSONReports(reportsDirectory, outputDirectory);
        } else {
            System.out.println("Merging only data setup tests results (error must have happened)");
        }

    }

    /**
     * Merge all reports together into master report in given reportDirectory
     *
     * @throws Exception
     */
    public void mergeJSONReports(File reportsDirectory, File outputDirectory) throws Throwable {

        JSONParser parser = new JSONParser();

        Collection<File> reportFiles = FileUtils.listFiles(reportsDirectory, new String[]{"json"}, true);

        File existingReport = new File(outputDirectory + "/cucumber.json");

        JSONArray mergedReport  = new JSONArray();

        if (existingReport.exists()) {
            try {
                Object obj = parser.parse(new FileReader(existingReport));
                mergedReport = (JSONArray) obj;
            } catch (Throwable t) {
                System.out.println("Error reading existing report, overwriting with a new one");
            }
        } else {
            System.out.println("No existing report, creating a new one");
        }

        for (File report : reportFiles) {
            try {
                Object obj = parser.parse(new FileReader(report));

                JSONArray reportArray = (JSONArray) obj;

                Iterator<JSONObject> iterator = reportArray.iterator();
                while (iterator.hasNext()) {
                    JSONObject object = iterator.next();

                    mergedReport.add(object);
                }
            } catch (Throwable t) {
                System.out.println("Error parsing " + report.getCanonicalPath());
            }
        }

        try (FileWriter file = new FileWriter(outputDirectory + "/cucumber.json")) {
            file.write(mergedReport.toJSONString());
            System.out.println("Successfully merged Cucumber json reports into single file");
        }
    }

}
