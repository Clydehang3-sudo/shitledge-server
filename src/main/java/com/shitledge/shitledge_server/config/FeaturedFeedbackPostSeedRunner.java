package com.shitledge.shitledge_server.config;

import com.shitledge.shitledge_server.qa.Answer;
import com.shitledge.shitledge_server.qa.AnswerRepository;
import com.shitledge.shitledge_server.qa.Question;
import com.shitledge.shitledge_server.qa.QuestionRepository;
import com.shitledge.shitledge_server.qa.QuestionViewFactory;
import com.shitledge.shitledge_server.qa.TagCodec;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class FeaturedFeedbackPostSeedRunner implements CommandLineRunner {

    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    public FeaturedFeedbackPostSeedRunner(QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
    }

    @Override
    @Transactional
    public void run(String... args) {
        Question question = questionRepository.findByTitle(QuestionViewFactory.FEATURED_FEEDBACK_TITLE)
                .orElseGet(() -> questionRepository.save(new Question(
                        QuestionViewFactory.FEATURED_FEEDBACK_TITLE,
                        "最近我们小组在做一条功能型雪裤，核心不是更潮，是更安全+更省事。现在在验证几个点：急刹自动闪红灯提醒后方；膝盖做可抽拉髌骨带；臀部和膝盖做一体式护垫，减少额外穿护具的麻烦。目标是给单双板初中级、重安全、怕麻烦但愿意为功能买单的人。不是来硬广，真心想听反馈：你们会不会考虑买？最担心哪个坑？",
                        "雪场打工产品狗",
                        LocalDateTime.now().minusHours(13),
                        TagCodec.join(List.of("滑雪装备", "产品反馈", "安全", "脑洞"))
                )));

        long existingAnswerCount = answerRepository.countByQuestionId(question.getId());
        if (existingAnswerCount >= 50) {
            return;
        }

        if (existingAnswerCount > 0) {
            answerRepository.deleteByQuestionIdIn(List.of(question.getId()));
            answerRepository.flush();
        }

        LocalDateTime baseTime = LocalDateTime.now().minusHours(12);
        List<CommentSeed> comments = List.of(
                new CommentSeed("雪道夜猫子", "这个尾灯思路有点东西，夜场真的有用。", 12, 12),
                new CommentSeed("北坡新手", "我愿意试，带新手朋友下坡时后面经常看不清。", 10, 12),
                new CommentSeed("预算守门员", "先问价格，超过2k我会犹豫。", 18, 11),
                new CommentSeed("膝盖旧伤党", "髌骨带可抽拉这个细节挺实用，膝盖旧伤党关注。", 16, 11),
                new CommentSeed("电池警察", "灯带续航多久？一天滑6小时能撑住吗。", 14, 11),
                new CommentSeed("懒得手洗", "洗衣机能不能洗，电子件进水会不会废。", 17, 11),
                new CommentSeed("怕热体质", "护垫最好可拆，不然春雪天会热死。", 13, 10),
                new CommentSeed("单板裤型控", "版型别做太臃肿，单板党真的怕米其林感。", 19, 10),
                new CommentSeed("护具厌倦者", "感兴趣，省得每次穿护臀护膝像穿盔甲。", 11, 10),
                new CommentSeed("怀疑论者", "我有点怀疑急刹识别，误触会不会一直闪。", 15, 10),
                new CommentSeed("穷学生滑手", "预算1500以内可以冲，超了我就普通雪裤+护具。", 22, 9),
                new CommentSeed("小个子女生", "女款高腰版请安排，不然很多人直接劝退。", 16, 9),
                new CommentSeed("第一季入门", "初学者非常需要这种一体化，摔两次就懂了。", 9, 9),
                new CommentSeed("雪场摄影师", "能不能做黑白灰+一两个亮色，兼顾拍照。", 8, 9),
                new CommentSeed("轻量化执念", "我关心重量，护垫加灯带会不会太沉。", 12, 8),
                new CommentSeed("参数党", "防水透气参数要给，至少得看到靠谱等级。", 20, 8),
                new CommentSeed("懒人中级", "比单买护具省事，这点我会买单。", 7, 8),
                new CommentSeed("不想踩雷", "创意是好，但维护成本会不会偏高。", 15, 8),
                new CommentSeed("拆装爱好者", "灯带如果做成可拆电池，会安心很多。", 10, 7),
                new CommentSeed("缆车老屁股", "值不值看耐用，缆车坐久了裤屁股磨损很快。", 13, 7),
                new CommentSeed("夜滑党", "我夜滑多，这功能我愿意付费。", 24, 7),
                new CommentSeed("雾天幸存者", "建议加手动常亮模式，雾天可能更实用。", 11, 7),
                new CommentSeed("售后敏感体", "最怕灯坏了整条裤子就半残。", 16, 6),
                new CommentSeed("首发尝鲜", "首发价友好我会下单试一条。", 9, 6),
                new CommentSeed("公园玩家", "护垫厚度最好分级，公园党和新手需求不同。", 14, 6),
                new CommentSeed("弟弟监护人", "我会买给我弟，第一季入门安全优先。", 8, 6),
                new CommentSeed("雪场社牛", "红灯会不会影响拍照质感（笑），不过安全确实更重要。", 6, 6),
                new CommentSeed("测试控", "建议先找雪场做小规模体验，不然纸面参数没说服力。", 15, 5),
                new CommentSeed("腿粗也要滑", "髌骨带如果勒腿会很烦，调节范围要大。", 11, 5),
                new CommentSeed("买前先算", "价格区间呢？1k-1.5k我觉得竞争力最大。", 18, 5),
                new CommentSeed("冷静旁观", "我持保留态度，急刹时大家本来也会减速。", 12, 5),
                new CommentSeed("雪道被撞过", "但雪道拥挤时给后方提醒，确实可能救命。", 25, 4),
                new CommentSeed("汗蒸房腿", "透气性一定要做好，不想下午腿里全是汗。", 13, 4),
                new CommentSeed("一体化信徒", "护垫一体化我很喜欢，真的不想分层穿。", 10, 4),
                new CommentSeed("裤长受害者", "能做短腿友好版型吗，很多雪裤裤长灾难。", 19, 4),
                new CommentSeed("理赔思维", "灯带模块单独保修会更安心。", 16, 4),
                new CommentSeed("低温电池党", "续航和低温表现要讲清楚，-15度电池衰减很明显。", 20, 3),
                new CommentSeed("崇礼周末票", "感兴趣，特别适合第一次去崇礼那种新手局。", 9, 3),
                new CommentSeed("装备横评怪", "我会拿它跟Burton这类对比，核心看做工和缝线。", 14, 3),
                new CommentSeed("需求挖掘师", "你们抓住怕麻烦这个点了，这个挺准。", 7, 3),
                new CommentSeed("功能付费派", "只要不是噱头，我愿意付一点溢价。", 21, 3),
                new CommentSeed("老手不买账", "也可能是过度设计，高手可能根本不需要。", 12, 2),
                new CommentSeed("定位党", "但给初中级定位我觉得合理，市场不小。", 8, 2),
                new CommentSeed("白天滑手", "建议白天可关闭闪灯，夜滑再开。", 10, 2),
                new CommentSeed("实测党", "想看摔倒测试视频，尤其臀部缓冲效果。", 17, 2),
                new CommentSeed("渠道脑", "可以考虑和雪场租赁合作，先让人低成本体验。", 15, 2),
                new CommentSeed("路人甲", "这个思路不错，至少不是换个颜色就叫创新。", 7, 1),
                new CommentSeed("售后审计", "我关心售后，灯坏了换模块多少钱。", 18, 1),
                new CommentSeed("观望型消费者", "我先观望第一批真实反馈，再决定入不入。", 13, 1),
                new CommentSeed("总结侠", "总结：价格别飘、功能别虚，我愿意试。", 23, 1)
        );

        List<Answer> answers = comments.stream().map(comment -> new Answer(
                question,
                comment.authorName(),
                comment.content(),
                baseTime.plusMinutes(comment.minutesOffset()),
                comment.likes()
        )).toList();

        answerRepository.saveAll(answers);
    }

    private record CommentSeed(String authorName, String content, int likes, int minutesOffset) {
    }
}
