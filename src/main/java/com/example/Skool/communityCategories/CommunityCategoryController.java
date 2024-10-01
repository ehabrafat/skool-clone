package com.example.Skool.communityCategories;

import com.example.Skool.common.dtos.PageResponseDto;
import com.example.Skool.common.mappers.HelperMapper;
import com.example.Skool.communityCategories.dtos.CommunityCategoryDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("categories")
@RequiredArgsConstructor
public class CommunityCategoryController {
    private final CommunityCategoryService communityCategoryService;
    private final HelperMapper helperMapper;

    @PostMapping
    public CommunityCategory createCategory(@Valid @RequestBody CommunityCategoryDto categoryDto) {
        return this.communityCategoryService.createCategory(categoryDto.getName());
    }

    @GetMapping
    public ResponseEntity<PageResponseDto<CommunityCategoryProjection>> getAllCategories(
            @RequestParam(value = "page_num", defaultValue = "0", required = false) int pageNum,
            @RequestParam(value = "page_size", defaultValue = "10", required = false) int pageSize
    ) {
        Page<CommunityCategoryProjection> pages = this.communityCategoryService.getAllCategories(pageNum, pageSize);
        return ResponseEntity.ok(helperMapper.toPageResponseDto(pages));
    }

}
