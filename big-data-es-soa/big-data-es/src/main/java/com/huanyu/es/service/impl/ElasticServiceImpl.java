/**
 * 文件名：ElasticServiceImpl.java 版权：Company Technologies Co.,Ltd.Copyright
 * YYYY-YYYY,All rights reserved 版权：Copyright (c) 2020, jia2040020@126.com All
 * Rights Reserved. 描述：<描述> 修改人：Administrator 修改时间：2020年4月6日 修改内容：<修改内容>
 */
package com.huanyu.es.service.impl;
 
import java.util.Iterator;
import java.util.List;

import com.huanyu.es.dao.ElasticRepository;
import com.huanyu.es.model.DocBean;
import com.huanyu.es.service.IElasticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.stereotype.Service;
 

/**
 * 
 * @author  sunsf
 * @date   2020/5/31 18:40
 * @param  
 * @return      
 * @exception   
 */
@Service("elasticService")
public class ElasticServiceImpl implements IElasticService
{
    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;
 
    @Autowired
    private ElasticRepository elasticRepository;
 
    private Pageable pageable = PageRequest.of(0, 10);
 
    public void createIndex()
    {
        elasticsearchTemplate.createIndex(DocBean.class);
    }
 
    public void deleteIndex(String index)
    {
        elasticsearchTemplate.deleteIndex(index);
    }
 
    public void save(DocBean docBean)
    {
        elasticRepository.save(docBean);
    }
 
    public void saveAll(List<DocBean> list)
    {
        elasticRepository.saveAll(list);
    }
 
    public Iterator<DocBean> findAll()
    {
        return elasticRepository.findAll().iterator();
    }
 
    public Page<DocBean> findByContent(String content)
    {
        // TODO Auto-generated method stub
        return null;
    }
 
    public Page<DocBean> findByFirstCode(String firstCode)
    {
        // TODO Auto-generated method stub
         return elasticRepository.findByContent(firstCode,pageable);
    }
 
    public Page<DocBean> findBySecordCode(String secordCode)
    {
        // TODO Auto-generated method stub
        return elasticRepository.findBySecordCode(secordCode, pageable);
    }
 
    public Page<DocBean> query(String key)
    {
        // TODO Auto-generated method stub
        return elasticRepository.findByContent(key, pageable);
    }
 
   
 
}