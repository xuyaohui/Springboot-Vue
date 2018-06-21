package com.teradata.controller.lucene;

import com.teradata.model.common.Result;
import com.teradata.model.lucene.DocumentFileDTO;
import com.teradata.model.user.SysUserVO;
import com.teradata.utils.LuceneUtils;
import com.teradata.utils.ResultUtil;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/search")
public class LuceneController{

    private static Logger logger= LoggerFactory.getLogger(LuceneController.class);

    @GetMapping("/getAllFile")
    public Result<SysUserVO> getAllLuceneFile(@RequestParam String text) throws Exception{
        String indexDir="indexDir/";
        //查询词汇
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        IndexReader reader= DirectoryReader.open(dir);
        IndexSearcher is=new IndexSearcher(reader);
        SmartChineseAnalyzer analyzer=new SmartChineseAnalyzer();//中文分词器
        QueryParser parser=new QueryParser("desc", analyzer);//进行分析的字段
        Query query= parser.parse(text);
        long start=System.currentTimeMillis();
        TopDocs hits=is.search(query, 10);//查询结果选择前10个
        long end=System.currentTimeMillis();
        logger.info("匹配 "+text+" ，总共花费"+(end-start)+"毫秒"+"查询到"+hits.totalHits+"个记录");
        QueryScorer scorer=new QueryScorer(query);
        Fragmenter fragmenter=new SimpleSpanFragmenter(scorer);
        SimpleHTMLFormatter simpleHTMLFormatter=new SimpleHTMLFormatter("<b><font color='red'>","</font></b>");//设置查询结果红色字体加粗（可自行修改默认为粗体）
        Highlighter highlighter=new Highlighter(simpleHTMLFormatter, scorer);
        highlighter.setTextFragmenter(fragmenter);
        List<DocumentFileDTO> dtos=new ArrayList<DocumentFileDTO>();
        for(ScoreDoc scoreDoc:hits.scoreDocs){
            Document doc=is.doc(scoreDoc.doc);
            DocumentFileDTO dtoTemp=new DocumentFileDTO();
            dtoTemp.setTitle(doc.get("name"));
            String desc = doc.get("desc");
            TokenStream tokenStream=analyzer.tokenStream("desc", new StringReader(desc));
            String content=highlighter.getBestFragment(tokenStream, desc);
            dtoTemp.setContent(content);
            dtos.add(dtoTemp);
        }
        reader.close();
        return ResultUtil.success(dtos, hits.scoreDocs.length);
    }

}
