package com.duowan.common.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;


public class NamedQueryUtilTest {

	
	private static Logger log = Logger.getLogger(NamedQueryUtil.class);
	

	@Test
	public void testSelectUserFromAccessGroupByPagePerformance(){
		NamedQueryUtil util = new NamedQueryUtil("queries/mysql/query-sa-mysql.xml");
		final Map<String,String> model3  = new HashMap<String,String>();
		model3.put("name", "penghao122@126.com");
		long start  = System.currentTimeMillis();
		int count = 100000;
	
		for(int i=0;i<count;i++) {
			Assert.assertNotNull(util.loadFreeMarkSql("selectUserFromAccessGroupByPage", model3));
		}
		long end  = System.currentTimeMillis();
		float cost = end-start;
		log.info("cost-Time:=" + cost+" tps:"+(count / cost * 1000));
	}

	@Test
	public void testSelectUserFromAccessGroupByPage(){
		NamedQueryUtil util = new NamedQueryUtil("queries/mysql/query-sa-mysql.xml");
		final Map<String,Object> testMap  = new HashMap<String,Object>();
		testMap.put("name", "ddd");
		Assert.assertNotNull(util.loadFreeMarkSql("selectUserFromWhere", testMap));
		log.info("selectUserFromAccessGroupByPage:="+util.loadFreeMarkSql("selectUserFromAccessGroupByPage", testMap));
	
	}
	
	@Test
	public void testMacro(){
		NamedQueryUtil util = new NamedQueryUtil("queries/mysql/query-sa-mysql.xml");
		final Map<String,Object> testMap  = new HashMap<String,Object>();

		Assert.assertNotNull(util.loadFreeMarkSql("macoroTest", testMap));
		log.info("macoroTest:="+util.loadFreeMarkSql("macoroTest", testMap));
	
	}
	
	
}
