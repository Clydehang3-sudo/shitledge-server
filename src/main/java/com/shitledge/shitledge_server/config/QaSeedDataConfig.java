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
                    "我只是周期性点进她主页，这算复吸吗？",
                    "理论上我已经放下，实践上我每晚都会“顺手”看一眼。这个动作到底是情绪收尾，还是执念续费？",
                    "战略性嘴硬",
                    LocalDateTime.now().minusDays(2).minusHours(3),
                    TagCodec.join(List.of("关系", "自我欺骗", "深夜行为"))
            );

            Question q2 = new Question(
                    "如何分辨自己是在努力，还是在表演努力？",
                    "我买了新本子、开了新文档、做了新计划，完成感很强，但事情基本没推进。有没有一个可执行的区分标准？",
                    "工位方法派",
                    LocalDateTime.now().minusDays(1).minusHours(8),
                    TagCodec.join(List.of("效率", "拖延", "职场"))
            );

            Question q3 = new Question(
                    "为什么有些人做计划像上市敲钟？",
                    "每次制定目标都要发朋友圈、做海报、写宣言，仪式很足，进度很少。计划公开是不是一种替代性完成？",
                    "旁观型执行者",
                    LocalDateTime.now().minusHours(20),
                    TagCodec.join(List.of("计划", "社交媒体", "行为观察"))
            );

            Question q4 = new Question(
                    "人真的能体面地放下一个根本没开始的人吗？",
                    "我们没在一起，也没说清楚结束，但我已经在脑内写完三季剧情。请问这种“无名分内耗”怎么终止？",
                    "无剧情不入睡",
                    LocalDateTime.now().minusHours(14),
                    TagCodec.join(List.of("情感", "脑补", "边界"))
            );

            Question q5 = new Question(
                    "为什么每次说“最后一次看他朋友圈”都像战略欺骗？",
                    "我每次都说“最后一次”，然后就进入“再确认一次”模式。是不是大脑把偷窥包装成复盘了？",
                    "克制失败样本",
                    LocalDateTime.now().minusHours(9),
                    TagCodec.join(List.of("社交平台", "复吸", "习惯"))
            );

            Question q6 = new Question(
                    "我反复打开聊天框又关闭，这属于哪一类病理活动？",
                    "字打好了，删；删完了，再打；最后什么都没发。这个循环到底叫谨慎，还是情绪心电图？",
                    "打字又撤回",
                    LocalDateTime.now().minusHours(4),
                    TagCodec.join(List.of("沟通", "焦虑", "成年礼貌"))
            );

            questionRepository.saveAll(List.of(q1, q2, q3, q4, q5, q6));

            answerRepository.saveAll(List.of(
                    new Answer(
                            q1,
                            "低频复盘师",
                            "这不叫观察，这叫给执念续费。你不是在收尾，你是在延长片尾字幕。",
                            LocalDateTime.now().minusDays(1).minusHours(18),
                            41
                    ),
                    new Answer(
                            q2,
                            "精装拖延研究员",
                            "你做的是“努力前戏”，不是努力本体。真正的努力有一个硬指标：今天有没有留下可验证的产出。",
                            LocalDateTime.now().minusHours(23),
                            36
                    ),
                    new Answer(
                            q3,
                            "仪式感会计",
                            "公开计划会提前释放成就感，像先把庆功宴办了再开工。仪式可以有，但别把它算进进度条。",
                            LocalDateTime.now().minusHours(17),
                            28
                    ),
                    new Answer(
                            q4,
                            "边界维护员",
                            "你放不下的不是人，是你自己写的剧情。先停更想象，再谈体面收场。",
                            LocalDateTime.now().minusHours(11),
                            52
                    ),
                    new Answer(
                            q5,
                            "行为拆解师",
                            "这属于典型战略欺骗：口头版本叫“最后一次”，执行版本叫“再确认一次”。你需要的是断路径，不是立誓。",
                            LocalDateTime.now().minusHours(7),
                            45
                    ),
                    new Answer(
                            q6,
                            "礼貌障碍门诊",
                            "你不是不会说，你是在同时追求“表达真实”和“零风险后果”。这两个目标一起开，就会无限撤回。",
                            LocalDateTime.now().minusHours(2),
                            33
                    )
            ));
        };
    }
}
