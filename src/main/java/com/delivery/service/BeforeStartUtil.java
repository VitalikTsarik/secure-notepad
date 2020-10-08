package com.delivery.service;

import com.delivery.entity.Text;
import com.delivery.entity.User;
import com.delivery.repository.TextRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class BeforeStartUtil {

    @Autowired
    private TextRepo textRepo;

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void addTexts() {
//        Text text = new Text();
//        text.setText("RAndom text random text random text random text random textrandom text random text random text////.../././.;;;");
//        textRepo.save(text);
//
//        Text text1 = new Text();
//        text1.setText("Very-very-very-secret text.....");
//        textRepo.save(text1);
    }

}
