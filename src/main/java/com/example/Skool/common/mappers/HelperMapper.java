package com.example.Skool.common.mappers;

import com.example.Skool.common.dtos.PageResponseDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.util.List;


@Mapper(componentModel = "spring")
public class HelperMapper {

    public <T> PageResponseDto<T> toPageResponseDto(Page<T> page){
        PageResponseDto<T> pageResponseDto = new PageResponseDto<>();
        pageResponseDto.setContent(page.getContent());
        pageResponseDto.setNumberOfElements(page.getNumberOfElements());
        pageResponseDto.setTotalNumberOfElements(page.getTotalElements());
        pageResponseDto.setHasPrevious(page.hasPrevious());
        pageResponseDto.setHasNext(page.hasNext());
        return pageResponseDto;
    }

    public <T, R> PageResponseDto<R> toPageResponseDto(Page<T> page, List<R> content){
        PageResponseDto<R> pageResponseDto = new PageResponseDto<>();
        pageResponseDto.setContent(content);
        pageResponseDto.setNumberOfElements(page.getNumberOfElements());
        pageResponseDto.setTotalNumberOfElements(page.getTotalElements());
        pageResponseDto.setHasPrevious(page.hasPrevious());
        pageResponseDto.setHasNext(page.hasNext());
        return pageResponseDto;
    }
}
