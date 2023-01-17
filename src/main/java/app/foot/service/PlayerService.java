package app.foot.service;

import app.foot.model.Exception.PlayerException;
import app.foot.model.Player;
import app.foot.model.PlayerScorer;
import app.foot.repository.PlayerScoreRepository;
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


  public List<PlayerScoreEntity> addPlayerScore (List<PlayerScorer> goals, Integer matchId){
    List<PlayerScoreEntity> result = new ArrayList<>();
    for (PlayerScorer goal : goals) {
      PlayerScoreEntity temp = PlayerScoreEntity.builder()
          .player(getPlayerById(goal.getPlayer().getId()))
          .ownGoal(goal.getIsOwnGoal())
          .minute(goal.getMinute())
          .match(matchService.getMatchById(matchId))
          .build();
     if(!goal.getPlayer().getIsGuardian() && !result.contains(temp)){
       playerScorerepository.save(temp);
       result.add(temp);
     }
     else {
       throw new PlayerException("Goal not granted, player " + goal.getPlayer().getName() + " is a guardian");
     }
    }
    return result;
  }
}
