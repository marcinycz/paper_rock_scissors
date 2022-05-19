package pl.marcinycz.paper_rock_scissors;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PaperRockScissorsRepository extends JpaRepository <PaperRockScissors, Long> {
}
