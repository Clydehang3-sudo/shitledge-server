package com.shitledge.shitledge_server.qa;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnswerRepository extends JpaRepository<Answer, Long> {

    List<Answer> findByQuestionIdOrderByCreatedAtDesc(Long questionId);

    long countByQuestionId(Long questionId);

    Optional<Answer> findTopByQuestionIdOrderByCreatedAtDesc(Long questionId);

    void deleteByQuestionIdIn(Collection<Long> questionIds);
}
