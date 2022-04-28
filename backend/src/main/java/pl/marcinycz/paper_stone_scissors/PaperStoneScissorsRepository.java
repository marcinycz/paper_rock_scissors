package pl.marcinycz.paper_stone_scissors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaperStoneScissorsRepository extends JpaRepository <PaperStoneScissors, Long> {
}
