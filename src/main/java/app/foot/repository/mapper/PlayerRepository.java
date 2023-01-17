package app.foot.repository.mapper;

import app.foot.repository.entity.PlayerEntity;
import org.springframework.data.jpa.repository.support.JpaRepositoryImplementation;

public interface PlayerRepository extends JpaRepositoryImplementation<PlayerEntity, Integer> {
}
