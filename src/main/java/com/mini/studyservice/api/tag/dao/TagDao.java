package com.mini.studyservice.api.tag.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mini.studyservice.api.tag.dto.Tag;
import com.mini.studyservice.api.tag.dto.TagDto;
import com.mini.studyservice.api.tag.dto.TagMapping;

@Mapper
public interface TagDao {
//	public int insertTage(Tag tag);
//	public int insertTagMapping(TagMapping tagMapping);
	public int insertTages(List<Tag> tagList);
	public int insertTagMappings(List<TagMapping> tagMappingList);
	public List<Integer> getTagIds(List<Tag> tagList);
	public List<Tag> getTags(int board_id);
	public List<TagDto> getTagsWithBoardId(List<Integer> board_ids);
}
