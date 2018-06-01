package com.ciandt.paul.dao;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ciandt.paul.Config;
import com.ciandt.paul.entity.FifaRank;
import com.ciandt.paul.utils.BigQueryUtils;
import com.ciandt.paul.utils.GCSUtils;

/**
 * Class responsible for reading Fifa Rank data
 */
@Service
public class FifaRankDAO {

    @Autowired
    private Config config;
    @Autowired
    private BigQueryUtils bigQueryUtils;
    @Autowired
    private GCSUtils gcsUtils;

    private static Map<Integer, List<FifaRank>> cache;

    static {
        cache = new HashMap<Integer, List<FifaRank>>();
    }

    /**
     * Read the rank for a specific team and year
     */
    public FifaRank fetch(String teamName, Integer year) throws IOException, InterruptedException, DataNotAvailableException {
    	List<FifaRank> fifaRankList = this.fetch(year);
    	String cleanTeamName = teamName.replaceAll("[^\\p{Alpha}]+"," ");
    	for (FifaRank fifaRank : fifaRankList) {
    		String fifaRankTeamName = fifaRank.getTeamName().replaceAll("[^\\p{Alpha}]+"," ");
        	if (cleanTeamName.equalsIgnoreCase(fifaRankTeamName)) {
                return fifaRank;
            }
        }

        throw new DataNotAvailableException("FifaRank for " + teamName, year);
    }

    /**
     * Read the rank for a specific year
     */
    public List<FifaRank> fetch(Integer year) throws IOException, InterruptedException, DataNotAvailableException {

        //check the cache
        if (cache.get(year) != null) {
            return cache.get(year);
        }

        List<FifaRank> fifaRankList = new ArrayList<>();
        FifaRank fifaRank = null;

        // The data for 2006, 2010 and 2014 is inside GCS, bucket project-paul-the-octopus-datasets
        // 2018 is on BigQuery
        if ((year == 2006) || (year == 2010) || (year == 2014)) {
            String filename = "fifa_rank_" + year + ".csv";
            String content = gcsUtils.readFile(config.getDatasetBucket(), filename);
            Reader in = new StringReader(content);
            Iterable<CSVRecord> records = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(in);
            for (CSVRecord record : records) {
                fifaRank = new FifaRank(record);
                fifaRankList.add(fifaRank);
            }
        } else {
            List<List<String>> queryResult = null;

            String query = "SELECT * FROM paul_the_octopus_dataset.fifa_rank LIMIT 3000";
            queryResult = bigQueryUtils.executeQuery(query);

            if ((queryResult == null) || (queryResult.size() == 0)) {
                throw new DataNotAvailableException("FifaRank", year);
            }

            for (List<String> row : queryResult) {
                fifaRank = new FifaRank(row);
                fifaRankList.add(fifaRank);
            }
        }

        cache.put(year, fifaRankList);
        
        return fifaRankList;
    }
}
