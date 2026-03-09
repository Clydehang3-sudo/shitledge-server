package com.shitledge.shitledge_server.config;

import com.shitledge.shitledge_server.qa.AnswerRepository;
import com.shitledge.shitledge_server.qa.Question;
import com.shitledge.shitledge_server.qa.QuestionRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class LegacySampleCleanupRunner implements CommandLineRunner {

    private static final List<String> LEGACY_SAMPLE_TITLES = List.of(
            "A Longitudinal Study of Doomscrolling Before Bed",
            "Peer Review of Group Chat Apologies",
            "Quantifying Monday Motivation Leakage",
            "为什么人总在凌晨 1:40 认为自己能彻底改变人生？",
            "如何科学论证拖延不是懒，而是能量管理？",
            "为什么有些人朋友圈像上市公司年报？",
            "“我只是看看她主页”算不算现实检验行为？",
            "什么叫做高质量自我感动？",
            "我只是周期性点进她主页，这算复吸吗？",
            "如何分辨自己是在努力，还是在表演努力？",
            "为什么有些人做计划像上市敲钟？",
            "人真的能体面地放下一个根本没开始的人吗？",
            "为什么每次说“最后一次看他朋友圈”都像战略欺骗？",
            "我反复打开聊天框又关闭，这属于哪一类病理活动？"
    );

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public LegacySampleCleanupRunner(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        List<Question> samples = questionRepository.findByTitleIn(LEGACY_SAMPLE_TITLES);
        if (samples.isEmpty()) {
            return;
        }

        List<Long> questionIds = samples.stream().map(Question::getId).toList();
        answerRepository.deleteByQuestionIdIn(questionIds);
        answerRepository.flush();
        questionRepository.deleteAllByIdInBatch(questionIds);
    }
}
