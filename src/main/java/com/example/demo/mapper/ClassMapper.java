package com.example.demo.mapper;

import com.example.demo.entity.dto.ClassDTO;
import com.example.demo.entity.po.Class;
import com.example.demo.entity.vo.ClassVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author Ritsu
 * @description 针对表【class】的数据库操作Mapper
 * @createDate 2026-07-23 09:19:32
 * @Entity com.example.demo.entity.po.Class
 */
public interface ClassMapper {

    Long getMaxId();

    void add(ClassDTO classDTO);

    void deleteById(Long id);

    void update(ClassDTO classDTO);

    ClassVO getInfo(Long id);

    List<ClassVO> getPage(@Param("offset") Integer offset, @Param("pageSize") Integer pageSize);

    Long getCount();
}




