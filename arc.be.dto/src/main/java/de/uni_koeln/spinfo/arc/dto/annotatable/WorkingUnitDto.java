package de.uni_koeln.spinfo.arc.dto.annotatable;

import java.util.List;

import de.uni_koeln.spinfo.arc.dto.annotation.ChapterRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.HasDetailsDto;
import de.uni_koeln.spinfo.arc.dto.annotation.HasTitleDto;
import de.uni_koeln.spinfo.arc.dto.annotation.LanguageRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.PageRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.RangeAnnotationDto;


public interface WorkingUnitDto extends HasDetailsDto, HasAnnotationsDto, HasTitleDto, RangeAnnotationDto  {
//String getId();

	public List<PageRangeDto> getPages();
	public List<ChapterRangeDto> getChapters();
	List<LanguageRangeDto> getLanguages();
	List<PageRangeDto> getPageRangesByRange(RangeAnnotationDto rangeAnnotation);
	
}
