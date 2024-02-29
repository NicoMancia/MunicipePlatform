package it.unicam.cs.ids.municipeplatform.Contest;


import java.util.List;

public interface ContestService {
    ContestEntity createContest(ContestEntity contest, List<Long> contents);
    ContestEntity getContest(Long id);
    List<ContestEntity> getAllContest();
    void updateContest(ContestEntity contest, List<Long> contents);
    boolean subscribeContent(Long contentId, Long contestId);
    boolean unsubscribeContent(Long contentId, Long contestId);
    void terminateContest (Long contestId);
}