package com.teradata.utils;

import com.teradata.model.lucene.DocumentFileDTO;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.nio.file.Paths;
import java.util.List;

/**
 * Lucene操作工具类
 * @author xuyaohui
 */
public class LuceneUtils {

    private static final String dirPath = "indexDir/";
    private static Directory dir ;

    public static IndexWriter getWriter()throws Exception{
        //Analyzer analyzer=new StandardAnalyzer(); // 标准分词器
        SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();//中文分词器
        IndexWriterConfig iwc=new IndexWriterConfig(analyzer);
        IndexWriter writer=new IndexWriter(dir, iwc);
        return writer;
    }

    /**
     * 生成索引
     */
    public static void index(List<DocumentFileDTO> dtos)throws Exception{
        dir= FSDirectory.open(Paths.get(dirPath));
        IndexWriter writer=getWriter();
        writer.deleteAll();
        for(DocumentFileDTO dto : dtos){
            Document doc=new Document();
            doc.add(new IntPoint("id", dto.getId()));
            doc.add(new StringField("name",dto.getTitle(), Field.Store.YES));
            doc.add(new TextField("desc", dto.getContent(), Field.Store.YES));
            doc.add(new StoredField("id", dto.getId()));
            writer.addDocument(doc);
        }
        writer.close();
    }
}
