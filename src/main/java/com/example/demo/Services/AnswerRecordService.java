package com.example.demo.Services;
import com.example.demo.Entities.AnswerRecord;
import com.example.demo.Repositories.AnswerRecordRepository;
import com.example.demo.Entities.User;

import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AnswerRecordService {

    private final AnswerRecordRepository answerRecordRepository;

    public AnswerRecordService(AnswerRecordRepository answerRecordRepository) {
        this.answerRecordRepository = answerRecordRepository;
    }

    public List<AnswerRecord> getAnswerRecordsByScaleId(Long scaleId) {
        List<AnswerRecord> answerRecords = answerRecordRepository.findByScaleId(scaleId, Sort.by(Sort.Direction.DESC, "finishTimestamp"));
        return answerRecords.stream()
            .filter(record -> record.isCompleted() == true)
            .collect(Collectors.toList());
    }

    public User getUserByRecordId(Long recordId) {
        // 根据recordId查询UserAccount
        // 这里假设AnswerRecordRepository有一个findById方法
        Optional<AnswerRecord> answerRecordOptional = answerRecordRepository.findById(recordId);
        if (answerRecordOptional.isPresent()) {
            AnswerRecord answerRecord = answerRecordOptional.get();
            return answerRecord.getUser();
        } else {
            // Optional对象不包含任何值，你需要决定如何处理这种情况
            // 例如，你可以返回null，或者抛出一个异常
            return null;
        }
    }
}