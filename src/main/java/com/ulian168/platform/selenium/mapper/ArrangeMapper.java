/**
　* Copyright 2018-2038 ulian168.com
　* All right reserved.
　*/
package com.ulian168.platform.selenium.mapper;

import java.util.List;

import org.apache.ibatis.annotations.*;


import com.ulian168.platform.selenium.bean.ArrangeBean;
 
/**
 * @Title:ArrangeMapper
 * @Description:TODO
 * @author liuming
 * @date 2018年8月9日 下午4:01:28
 * @version V1.0
 */
@Mapper
public interface ArrangeMapper {
    /**
     * 获取所有编排信息.
     *
     * @param cityName 城市名
     */
    @Select("SELECT * FROM arrangeinfo order by create_time desc")
    // 返回 Map 结果集
    @Results({
            @Result(property = "name", column = "name"),
            @Result(property = "jobId", column = "jobid"),
            @Result(property = "serverIp", column = "serverip"),
            @Result(property = "path", column = "path"),
            @Result(property = "type", column = "type"),
            @Result(property = "state", column = "state"),
            @Result(property = "reportPath", column = "reportPath"),
            @Result(property = "createTime", column = "create_time"),
    })
    List<ArrangeBean> getAll();
    
    @Insert("INSERT INTO arrangeinfo(jobid,serverip,name, path, state, reportpath) VALUES(#{bean.jobId},#{bean.serverIp},#{bean.name}, #{bean.path}, #{bean.state}, #{bean.reportpath})")
    @Options(useGeneratedKeys = true)
    void add(@Param("bean") ArrangeBean bean);
}
