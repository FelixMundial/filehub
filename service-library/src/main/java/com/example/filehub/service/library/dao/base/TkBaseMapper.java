package com.example.filehub.service.library.dao.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author yinfelix
 * @date 2020/4/16
 */
public interface TkBaseMapper<T> extends Mapper<T>, MySqlMapper<T> {
}
