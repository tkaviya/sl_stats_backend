package com.tkaviya.slstats.api.service;

import com.tkaviya.slstats.model.StreamingStatistic;

import java.util.ArrayList;

/***************************************************************************
 * Created:     01 / 11 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

public interface IStatisticService {
    ArrayList<StreamingStatistic> getAllStats(String userName);

    StreamingStatistic getStatsByType(String userName, String statisticType);
}
