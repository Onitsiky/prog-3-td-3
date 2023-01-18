package app.foot.service;

import app.foot.model.Exception.MatchException;
import app.foot.model.Exception.PlayerException;
import app.foot.model.PlayerScorer;
import app.foot.repository.PlayerScoreRepository;
import app.foot.repository.entity.MatchEntity;
import app.foot.repository.entity.PlayerEntity;
import app.foot.repository.entity.PlayerScoreEntity;
import app.foot.repository.mapper.PlayerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PlayerService {
  private final PlayerScoreRepository playerScorerepository;
  private final PlayerRepository playerRepository;
  private final MatchService matchService;

  public PlayerEntity getPlayerById (Integer id){
    return playerRepository.findById(id).get();
  }


  public void addPlayerScore (List<PlayerScorer> goals, Integer matchId){
    MatchEntity match = matchService.getMatchById(matchId);
    for (PlayerScorer goal : goals) {
      PlayerEntity scorer =getPlayerById(goal.getPlayer().getId());
          PlayerScoreEntity temp = PlayerScoreEntity.builder()
          .player(scorer)
          .ownGoal(goal.getIsOwnGoal())
          .minute(goal.getMinute())
          .match(match)
          .build();
      
     if(!goal.getPlayer().getIsGuardian() && (match.getTeamA().equals(scorer.getTeam()) || match.getTeamB().equals(scorer.getTeam()))){
       playerScorerepository.save(temp);
     }
     else if (goal.getPlayer().getIsGuardian()){
       throw new PlayerException("Goal not granted because player " + goal.getPlayer().getName() + " is a guardian");
     } else if (!match.getTeamA().equals(scorer.getTeam()) || !match.getTeamB().equals(scorer.getTeam())) {
       throw new MatchException("Goal not granted because player " + goal.getPlayer().getName() + " is not in teams list");
     }
    }
  }
}
