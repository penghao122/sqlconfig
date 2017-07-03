package com.duowan.common.util.loader;

import java.io.Reader;
import java.io.StringReader;
import java.util.HashMap;
import java.util.Map;

import freemarker.cache.TemplateLoader;


public class SqlTemplateLoader implements TemplateLoader {
	
	private Map<String, String> cache = new HashMap<String, String>();
	
    public SqlTemplateLoader(Map<String, String> cache) {
		super();
		this.cache = cache;
	}

	public void closeTemplateSource(Object templateSource) {
    }
    
    public Object findTemplateSource(String name) {
    	String template = loadQuery(name);
    	if(template == null) {
    		return null;
    	}
		return new StringTemplateSource(name,template,System.currentTimeMillis());   
    }
    
    public String loadQuery(final String queryName) {
        if (cache.containsKey(queryName)) {
        	return cache.get(queryName);   
        } else {
            return null;
        }
    }
    
    public long getLastModified(Object templateSource) {
        return ((StringTemplateSource)templateSource).lastModified;
    }
    
    public Reader getReader(Object templateSource, String encoding) {
        return new StringReader(((StringTemplateSource)templateSource).source);
    }
    
    private static class StringTemplateSource {
        private final String name;
        private final String source;
        private final long lastModified;
        
        StringTemplateSource(String name, String source, long lastModified) {
            if(name == null) {
                throw new IllegalArgumentException("name == null");
            }
            if(source == null) {
                throw new IllegalArgumentException("source == null");
            }
            if(lastModified < -1L) {
                throw new IllegalArgumentException("lastModified < -1L");
            }
            this.name = name;
            this.source = source;
            this.lastModified = lastModified;
        }
        
        public boolean equals(Object obj) {
            if(obj instanceof StringTemplateSource) {
                return name.equals(((StringTemplateSource)obj).name);
            }
            return false;
        }
        
        public int hashCode() {
            return name.hashCode();
        }
    }
	   

}
