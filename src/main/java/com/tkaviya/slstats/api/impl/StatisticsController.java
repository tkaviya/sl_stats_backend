package com.tkaviya.slstats.api.impl;

import com.tkaviya.slstats.api.service.IStatisticService;
import com.tkaviya.slstats.model.StreamingStatistic;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

/***************************************************************************
 * Created:     01 / 11 / 2022                                             *
 * Platform:    Ubuntu Linux x86_64                                        *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

@RestController
@RequestMapping(value = "/stats", produces = MediaType.APPLICATION_JSON_VALUE)
public class StatisticsController {

    final IStatisticService iStatisticService;

    public StatisticsController(IStatisticService iStatisticService) {
        this.iStatisticService = iStatisticService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public List<StreamingStatistic> statsPage(Principal principal) {
        return iStatisticService.getAllStats(principal.getName());
    }

    @GetMapping("/{statisticType}")
    @PreAuthorize("hasRole('USER')")
    public StreamingStatistic statsPage(Principal principal, @PathVariable("statisticType") String statisticType) {
        return iStatisticService.getStatsByType(principal.getName(), statisticType);
    }
}
