package com.tkaviya.slstats.api.impl;

import com.tkaviya.slstats.api.service.IStatisticService;
import com.tkaviya.slstats.model.StreamingStatistic;
import org.springframework.stereotype.Service;

import java.util.*;

import static java.lang.String.valueOf;
import static java.util.concurrent.ThreadLocalRandom.current;

/***************************************************************************
 * Created:     01 / 11 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@Service
public class StatisticServiceImpl implements IStatisticService {

    private static final Map<String, ArrayList<StreamingStatistic>> userStatistics = new HashMap<>();
    private static final ArrayList<StreamingStatistic> statistics = new ArrayList<>();

    /* use this if you want to have the same values every time
        static {
            //generate a random number between 0 & 200,000 for YouTube streams
            statistics.add(new StreamingStatistic("YouTube", valueOf(current().nextInt(0,200000))));
            //generate a random number between 0 & 500,000 for Twitch streams
            statistics.add(new StreamingStatistic("Twitch", valueOf(current().nextInt(0,500000))));
            //generate a random number between 0 & 50,000 for Spotify streams
            statistics.add(new StreamingStatistic("Spotify", valueOf(current().nextInt(0,50000))));
            //generate a random number between 0 & 300,000 for TikTok streams
            statistics.add(new StreamingStatistic("TikTok", valueOf(current().nextInt(0,300000))));
            //generate a random number between 0 & 100,000 for iTunes streams
            statistics.add(new StreamingStatistic("iTunes", valueOf(current().nextInt(0,100000))));
            userStatistics.put("tkaviya", statistics);
        }
     */

    @Override
    public ArrayList<StreamingStatistic> getAllStats(String userName) {
        statistics.clear();
        //generate a random number between 0 & 200,000 for YouTube streams
        statistics.add(new StreamingStatistic("YouTube", valueOf(current().nextInt(0,200000))));
        //generate a random number between 0 & 500,000 for Twitch streams
        statistics.add(new StreamingStatistic("Twitch", valueOf(current().nextInt(0,500000))));
        //generate a random number between 0 & 50,000 for Spotify streams
        statistics.add(new StreamingStatistic("Spotify", valueOf(current().nextInt(0,50000))));
        //generate a random number between 0 & 300,000 for TikTok streams
        statistics.add(new StreamingStatistic("TikTok", valueOf(current().nextInt(0,300000))));
        //generate a random number between 0 & 100,000 for iTunes streams
        statistics.add(new StreamingStatistic("iTunes", valueOf(current().nextInt(0,100000))));
        userStatistics.put("tkaviya", statistics);
        return userStatistics.get(userName);
    }

    @Override
    public StreamingStatistic getStatsByType(String userName, String statisticType) {
        var stats = userStatistics.get(userName);
        for (StreamingStatistic statistic : stats) {
            if (statistic.getStatisticName().equals(statisticType)) {
                return statistic;
            }
        }
        return null;
    }
}
