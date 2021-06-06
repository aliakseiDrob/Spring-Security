package com.epam.esm.controller;

import com.epam.esm.dto.TagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.model.TagModel;
import com.epam.esm.model.assembler.TagModelAssembler;
import com.epam.esm.service.TagService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import javax.annotation.security.RolesAllowed;


@RestController
@RequestMapping("/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;
    private final PagedResourcesAssembler<Tag> pagedResourcesAssembler;
    private final TagModelAssembler tagModelAssembler;

    @GetMapping
    @RolesAllowed({"ADMIN","USER"})
    public PagedModel<TagModel> getAll(Pageable pageable) {
        return pagedResourcesAssembler.toModel(tagService.findAll(pageable),
                tagModelAssembler);
    }
    @GetMapping("/{id}")
    @RolesAllowed({"ADMIN","USER"})
    public TagModel getTag(@PathVariable Long id) {
       return  tagModelAssembler.toModel(tagService.findById(id));
    }

    @PostMapping()
    @RolesAllowed("ADMIN")
    @ResponseStatus(HttpStatus.CREATED)
    public Long save(@RequestBody TagDto dto) {
        return tagService.save(dto);
    }


    @DeleteMapping("/{id}")
    @RolesAllowed("ADMIN")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTag(@PathVariable Long id) {
        tagService.delete(id);
    }
}
