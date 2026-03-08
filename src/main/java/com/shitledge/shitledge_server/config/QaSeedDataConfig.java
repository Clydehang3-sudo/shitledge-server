package com.shitledge.shitledge_server.config;

import com.shitledge.shitledge_server.qa.Answer;
import com.shitledge.shitledge_server.qa.AnswerRepository;
import com.shitledge.shitledge_server.qa.Question;
import com.shitledge.shitledge_server.qa.QuestionRepository;
import com.shitledge.shitledge_server.qa.TagCodec;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class QaSeedDataConfig {

    @Bean
    CommandLineRunner seedQuestions(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        return args -> {
            if (questionRepository.count() > 0) {
                return;
            }

            Question q1 = new Question(
                    "为什么人总在凌晨 1:40 认为自己能彻底改变人生？",
                    "白天我完全理性，到了凌晨就会打开备忘录写《新生活总纲》，第二天再也不看。这个行为到底是意志觉醒还是睡眠不足导致的叙事膨胀？",
                    "林北北",
                    LocalDateTime.now().minusDays(2),
                    TagCodec.join(List.of("深夜", "自我管理", "行为学"))
            );

            Question q2 = new Question(
                    "如何科学论证拖延不是懒，而是能量管理？",
                    "我想要一个听起来足够严谨、又不完全违背常识的解释模型，用来安抚自己和老板。欢迎给出可复现的拖延曲线。",
                    "周三下午",
                    LocalDateTime.now().minusDays(1).minusHours(5),
                    TagCodec.join(List.of("拖延", "职场", "心理"))
            );

            Question q3 = new Question(
                    "为什么有些人朋友圈像上市公司年报？",
                    "季度总结、年度复盘、战略升级、组织变革……每条都像在路演。我只是想知道他今晚吃了什么。",
                    "阿屎同学",
                    LocalDateTime.now().minusHours(18),
                    TagCodec.join(List.of("社交媒体", "表达", "互联网观察"))
            );

            Question q4 = new Question(
                    "“我只是看看她主页”算不算现实检验行为？",
                    "理论上我已经放下，实践上我会周期性访问。这个动作到底是情感收尾，还是浏览器缓存依赖？",
                    "匿名但不完全匿名",
                    LocalDateTime.now().minusHours(9),
                    TagCodec.join(List.of("关系", "社交平台", "自我欺骗"))
            );

            Question q5 = new Question(
                    "什么叫做高质量自我感动？",
                    "我做了很复杂的计划、买了很高级的笔记本、截图发给朋友，最后没有执行。这种满足感为什么如此真实？",
                    "认真摆烂研究员",
                    LocalDateTime.now().minusHours(3),
                    TagCodec.join(List.of("效率", "情绪", "认知偏差"))
            );

            questionRepository.saveAll(List.of(q1, q2, q3, q4, q5));

            answerRepository.saveAll(List.of(
                    new Answer(
                            q1,
                            "半夜哲学家",
                            "这属于典型的“低成本重启幻觉”。凌晨大脑给了你叙事快感，但没有配套执行资源。建议把“新生活总纲”改成“明天中午前只做一件事”。",
                            LocalDateTime.now().minusDays(1).minusHours(20),
                            17
                    ),
                    new Answer(
                            q2,
                            "工位气象局",
                            "可以把拖延看成能量分配失败：任务不清晰时，大脑会把资源投向即时反馈更高的行为。先拆任务，再开始，不然你会一直“准备开始”。",
                            LocalDateTime.now().minusHours(22),
                            23
                    ),
                    new Answer(
                            q3,
                            "冷静围观者",
                            "朋友圈年报的核心不是分享生活，而是维持“我在持续上升”的叙事稳定性。它是一种低频、公开、可归档的人设维护。",
                            LocalDateTime.now().minusHours(13),
                            11
                    ),
                    new Answer(
                            q4,
                            "标签心理学",
                            "严格说是“延迟性脱敏失败”。你不是在看主页，你是在做情绪复习。把入口藏起来，比靠意志更有效。",
                            LocalDateTime.now().minusHours(7),
                            29
                    ),
                    new Answer(
                            q5,
                            "仪式感工程师",
                            "高质量自我感动 = 高质量准备动作 + 低质量执行闭环。你获得了“我在努力”的身份奖励，但没有进入“我在完成”的结果奖励。",
                            LocalDateTime.now().minusHours(2),
                            34
                    )
            ));
        };
    }
}
