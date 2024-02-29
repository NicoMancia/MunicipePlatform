package it.unicam.cs.ids.municipeplatform.Contest;

import it.unicam.cs.ids.municipeplatform.Content.ContentEntity;
import it.unicam.cs.ids.municipeplatform.Content.ContentRepository;
import it.unicam.cs.ids.municipeplatform.User.UserRole;
import it.unicam.cs.ids.municipeplatform.User.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ContestServiceImpl implements ContestService {
    private final ContestRepository contestRepository;
    private final ContentRepository contentRepository;
    private final UserService userService;

    public ContestServiceImpl(ContestRepository contestRepository,
                              ContentRepository contentRepository,
                              UserService userService)
    {
        this.contestRepository = contestRepository;
        this.contentRepository = contentRepository;
        this.userService = userService;
    }

    @Override
    public ContestEntity createContest(ContestEntity contest, List<Long> contents)
    {
        if (contest == null)
        {
            throw new IllegalArgumentException("| ERROR | Contest is NULL");
        }

        if (userService.getRole(contest.getCreator().getIdUser())
                != UserRole.ANIMATOR) {
            throw new IllegalStateException("| ERROR | You need to be an animator to do this.");
        }

        contest.setCreator(userService.getUser(contest.getCreator().getIdUser()));

        contents.forEach(c -> {
            contest.subscribe(contentRepository.findById(c)
                    .orElseThrow(() -> new IllegalArgumentException("| ERROR | Cannot add contents that don't exist!")));
        });

        return contestRepository.save(contest);
    }

    public ContestEntity getContest(Long id)
    {
        return this.contestRepository.findById(id).orElse(null);
    }

    @Override
    public List<ContestEntity> getAllContest()
    {
        return StreamSupport.stream(contestRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

    @Override
    public void updateContest(ContestEntity contest, List<Long> contents)
    {
        if (contest == null) {
            throw new IllegalArgumentException("Contest is NULL.");
        }

        ContestEntity original = contestRepository.findById(contest.getId())
                .orElseThrow(() -> new IllegalArgumentException("Contest doesn't exist."));

        for (Long id : contents)
        {
            ContentEntity elem = this.contentRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Content doesn't exist."));
            contest.getContents().add(elem);
        }
        contest.setCreator(original.getCreator());

        contestRepository.save(contest);
    }

    @Override
    public boolean subscribeContent(Long contentId, Long contestId) {

        ContestEntity contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new IllegalArgumentException("Contest doesn't exist."));

        if (!contest.isContestOpen())
              return false;

        ContentEntity content = contentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("Content doesn't exist."));

        contest.subscribe(content);
        contestRepository.save(contest);
        return true;
    }
    @Override
    public boolean unsubscribeContent(Long contentId, Long contestId) {

        ContestEntity contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new IllegalArgumentException("Contest doesn't exist."));

        if (!contest.isContestOpen())
            return false;

        ContentEntity content = contentRepository.findById(contentId)
                .orElseThrow(() -> new IllegalArgumentException("Content doesn't exist."));

        contest.unsubscribe(content);
        contestRepository.save(contest);
        return true;
    }

    @Override
    public void terminateContest(Long contestId)
    {
        ContestEntity contest = contestRepository.findById(contestId)
                .orElseThrow(() -> new IllegalArgumentException("Contest doesn't exist."));

        contest.setContestOpen(false);

        contestRepository.save(contest);
    }
}