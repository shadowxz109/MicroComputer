package com.shadowxz.util;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shadowxz.domain.Constant;

/**
 * @Description:
 * @Author: xiangzhong23737
 * @Date: create by 2018/5/21 23:03
 * @Modified by:
 */
public class LogUtil {

    static Logger logger = LoggerFactory.getLogger(LogUtil.class);

    public static Map<String,Object> errorLog(Map<String,Object> result,String err,Exception e){
        logger.error(err,e);
        result.put("msg_no", Constant.GET_DATA_ERR);
        result.put("msg",err);
        return result;
    }
}
