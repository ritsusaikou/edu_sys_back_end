package com.example.demo.service;

import com.example.demo.entity.dto.ClassDTO;
import com.example.demo.entity.po.Class;
import com.example.demo.entity.vo.ClassVO;

import java.util.List;

/**
 * @author Ritsu
 * @description 针对表【class】的数据库操作Service
 * @createDate 2026-07-23 09:19:32
 */
public interface ClassService {

    Long getMaxId();

    void add(ClassDTO classDTO);

    void deleteById(Long id);

    void update(ClassDTO classDTO);

    ClassVO getInfo(Long id) throws Exception;

    List<ClassVO> getPage(Integer currentPage, Integer pageSize);

    Long getCount();
}
