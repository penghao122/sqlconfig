package com.duowan.common.util;



import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import com.duowan.common.util.loader.SqlTemplateLoader;
import com.duowan.common.util.sql.tag.NamedQueriesElement;
import com.duowan.common.util.sql.tag.SQLQueryElement;
import com.thoughtworks.xstream.XStream;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;



public final class NamedQueryUtil {

    private  Logger log = Logger.getLogger(NamedQueryUtil.class);

    private  Map<String, String> xmlCache = new HashMap<String, String>();

    private  Map<String, String> cache = new HashMap<String, String>();

    private  XStream xstream;

    private  Configuration cfg  = new Configuration();  

    public  NamedQueryUtil(final String xmlFileName) {
    	xstream = new XStream();
    	xstream.processAnnotations(new Class[] { NamedQueriesElement.class,SQLQueryElement.class,});
    	cfg.setNumberFormat("#########");
    	cfg.setBooleanFormat("true,false");
    	cfg.setCacheStorage(new freemarker.cache.MruCacheStorage(20, 250));
    	cfg.setObjectWrapper(new DefaultObjectWrapper());
    	cfg.setTemplateUpdateDelay(60000);
    	SqlTemplateLoader sqlmpLoader = new SqlTemplateLoader(cache);
    	cfg.setTemplateLoader(sqlmpLoader); 
    	clean();
    	
    	recursiveLoadQueries(xmlFileName);
    }



    /**
     * 
     * 将配置 文 件加入Cache
     * 
     * @param xmlFileName
     */
    public  void recursiveLoadQueries(final String xmlFileName) {
    	final NamedQueriesElement queries = loadQueries(xmlFileName);
        final List<SQLQueryElement> sqls = queries.getSqlQueries();
        if (CollectionUtils.isNotEmpty(sqls)) {
            for (SQLQueryElement sql : sqls) {
            		cache.put(sql.getName(), trim(sql.getSql()));
            		log.info("recursiveLoadQueries() name:"+sql.getName());
            }
        }
    }

    
    public String loadSql(String sqlName){
    	return cache.get(sqlName);
    }
    
    public String loadFreeMarkSql(String name, Map<String, ?> model) {  
		try {
			Template template = cfg.getTemplate(name);
			Writer out = new StringWriter(8 * 1024);
			template.process(model, out);
			return out.toString();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Parse sql failed,name:" + name	+ " model:" + model, e);
		}
    }  
    
    private  NamedQueriesElement loadQueries(final String xmlFileName) {
        String xmlContent = StringUtils.EMPTY;
        if (xmlCache.containsKey(xmlFileName)) {
            xmlContent = xmlCache.get(xmlFileName);
        } else {
            final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(xmlFileName);
            try {
                final byte[] buf = new byte[is.available()];
                is.read(buf);
                xmlContent = new String(buf);
                xmlCache.put(xmlFileName, xmlContent);
                log.info("loadQueries() xmlFileName:"+xmlFileName);
            } catch (IOException e) {
            
                throw new RuntimeException("Fail to read file [" + xmlFileName + "].");
            }

        }
        final NamedQueriesElement queries = (NamedQueriesElement) xstream.fromXML(xmlContent);
        return queries;
    }

    private static String trim(final String s) {
        return StringUtils.trim(s);
    }

    private  void clean(){
    	xmlCache.clear();
    }
}
