# sqlconfig

 实际的项目中的SQL文不可能是固定不变的，更多的是有条件的动态SQL文，这时候可以使用freemarker来动态生成你需要的SQL文 ,
 本项目使用Freemarker+ spring jdbc完全替换iBatis,性能也不错完全适用一些中小型项目使用
  
 
 
  
  
  

     
     <sql-query name="delete">
       	 <sql>
	         <![CDATA[
	     			DELETE FROM document_db WHERE 1=1  <#include 'documentDbCondition'/>
	     	  ]]>
         </sql>
     </sql-query>
     
          
      <sql-query name="getDocumentDb">
       	 <sql>
	         <![CDATA[
	     			SELECT * FROM document_db WHERE 
	     			1=1 <#include 'documentDbCondition'/> 
	     			
	     			order by order_flag
	     			
	     			limit ${page},${rows}
	     	  ]]>
         </sql>
     </sql-query>
     
       <sql-query name="update">


		<sql>
           <![CDATA[
			   UPDATE document_db 
			   
			   SET
				     <#if systemCode??> 
				     	system_code='${systemCode}'  ,
					 </#if> 		   		
				     <#if updateUser??> 
				     	update_user='${updateUser}'  ,
					 </#if> 		   		
		   		
				     <#if id??> 
				     	id='${id}'  ,
					 </#if> 		   		
			   		
				     <#if orderFlag??> 
				     	order_flag='${orderFlag}'  ,
					 </#if> 		   		
				     <#if systemDesp??> 
				     	system_desp='${systemDesp}'  ,
					 </#if> 		   		
				     <#if systemName??> 
				     	system_name='${systemName}'  ,
					 </#if> 		   		
		   		
			   	update_time = now()        	
		        WHERE 1=1  <#include 'documentDbCondition'/>
                ]]>
         </sql>
            
  </sql-query>