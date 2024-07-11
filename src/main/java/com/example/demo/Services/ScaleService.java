package com.example.demo.Services;
import com.example.demo.Entities.Scale;
import com.example.demo.Repositories.ScaleRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ScaleService {

    @Autowired
    private ScaleRepository scaleRepository;

    public List<Scale> findScaleHistoryById(Long id) {
        List<Scale> scaleHistory = new ArrayList<>();
        findScaleHistoryRecursive(id, scaleHistory);
        // 使用 Lambda 表达式简化 Comparator 的实现
        scaleHistory.sort((s1, s2) -> s1.getId().compareTo(s2.getId()));
        return scaleHistory;
    }

    public int getScaleHistoryNum(Long id) {
        List<Scale> scaleHistory = findScaleHistoryById(id);
        return scaleHistory.size();
    }

    private void findScaleHistoryRecursive(Long id, List<Scale> scaleHistory) {
        Scale scale = scaleRepository.findById(id).get();
        scaleHistory.add(scale);
        if (scale.getPreviousId() != null) {
            findScaleHistoryRecursive(scale.getPreviousId(), scaleHistory);
        }
    }
}

