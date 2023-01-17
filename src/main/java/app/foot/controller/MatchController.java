package app.foot.controller;

import app.foot.model.Match;
import app.foot.model.PlayerScorer;
import app.foot.repository.mapper.MatchMapper;
import app.foot.service.MatchService;
import app.foot.service.PlayerService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class MatchController {
    private final PlayerService playerService;
    private final MatchService service;
    private final MatchMapper mapper;

    @GetMapping("/matches")
    public List<Match> getMatches() {
        return service.getMatches();
    }

    @PostMapping("/matches/{matchId}/goals")
    public Match addGoals (
        @PathVariable Integer matchId,
        @RequestBody List<PlayerScorer> scores
    ){
        playerService.addPlayerScore(scores,matchId);
        return mapper.toDomain(service.getMatchById(matchId));
    }
}
