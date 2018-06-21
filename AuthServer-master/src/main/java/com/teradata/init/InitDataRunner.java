package com.teradata.init;

import com.teradata.controller.LoginController;
import com.teradata.model.lucene.DocumentFileDTO;
import com.teradata.utils.LuceneUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xuyaohui
 *
 * 用于初始化数据
 */
@Component
public class InitDataRunner implements CommandLineRunner {


    private static Logger logger= LoggerFactory.getLogger(InitDataRunner.class);

    @Override
    public void run(String... strings) throws Exception {
        logger.info("开始加载数据...");
        List<DocumentFileDTO> dtos=new ArrayList<DocumentFileDTO>();
        DocumentFileDTO dto=new DocumentFileDTO();
        dto.setId(1);
        dto.setTitle("JAVA学习");
        dto.setContent("JAVA学习是一件痛苦的事！");
        dtos.add(dto);
        DocumentFileDTO dto1=new DocumentFileDTO();
        dto1.setId(2);
        dto1.setTitle("PYTHON学习");
        dto1.setContent("PYTHON学习是一件痛苦的事！");
        dtos.add(dto1);
        try {
            LuceneUtils.index(dtos);
        }catch (Exception e){
            e.printStackTrace();
        }

        logger.info("加载数据完成...");
    }
}
