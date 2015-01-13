package de.uni_koeln.spinfo.arc.editor.server.workingunit;

import java.util.Iterator;

import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.Word;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WorkingUnitImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.ChapterRange;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.FormAnnotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.LanguageRange;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.PageRange;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.PosAnnotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.RectangleAnnotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl.ChapterRangeImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl.FormAnnotationImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl.LanguageRangeImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl.PageRangeImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl.PosAnnotationImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.impl.RectangleAnnotationImpl;
import de.uni_koeln.spinfo.arc.dto.annotatable.WordDto;
import de.uni_koeln.spinfo.arc.dto.annotatable.WorkingUnitDto;
import de.uni_koeln.spinfo.arc.dto.annotatable.impl.WordDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotatable.impl.WorkingUnitDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.AnnotationDto.AnnotationTypes;
import de.uni_koeln.spinfo.arc.dto.annotation.ChapterRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.FormAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.LanguageRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.PageRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.PosAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.RectangleAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.impl.ChapterRangeDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.impl.FormAnnotationDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.impl.LanguageRangeDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.impl.PageRangeDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.impl.PosAnnotationDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.impl.RectangleAnnotationDtoImpl;

public class ModelConverterImpl implements ModelConverter {

	@Override
	public WorkingUnit convertFromDto(WorkingUnitDto workingUnitDto) {
		WorkingUnit toBeReturned = new WorkingUnitImpl(
				workingUnitDto.getDate(), workingUnitDto.getScore(),
				workingUnitDto.getUserId(), workingUnitDto.getTitle(),
				workingUnitDto.getStart(), workingUnitDto.getEnd());
		for (Iterator<PageRangeDto> iterator = workingUnitDto.getPages()
				.iterator(); iterator.hasNext();) {
			PageRangeDto prDto = iterator.next();
			PageRange pr = new PageRangeImpl(prDto.getDate(), prDto.getScore(),
					prDto.getUserId(), (int) prDto.getStart(),
					(int) prDto.getEnd(), prDto.getUrl());
			toBeReturned
					.setAnnotationAsType(
							de.uni_koeln.spinfo.arc.annotationmodel.annotation.Annotation.AnnotationTypes.PAGE_RANGE,
							pr);
		}
		for (Iterator<ChapterRangeDto> iterator = workingUnitDto.getChapters()
				.iterator(); iterator.hasNext();) {
			ChapterRangeDto crDto = iterator.next();
			ChapterRange cr = new ChapterRangeImpl(crDto.getDate(),
					crDto.getScore(), crDto.getUserId(),
					(int) crDto.getStart(), (int) crDto.getEnd(),
					crDto.getTitle());
			toBeReturned
					.setAnnotationAsType(
							de.uni_koeln.spinfo.arc.annotationmodel.annotation.Annotation.AnnotationTypes.CHAPTER_RANGE,
							cr);
		}
		for (Iterator<LanguageRangeDto> iterator = workingUnitDto
				.getLanguages().iterator(); iterator.hasNext();) {
			LanguageRangeDto lrDto = iterator.next();
			LanguageRange lr = new LanguageRangeImpl(lrDto.getDate(),
					lrDto.getScore(), lrDto.getUserId(),
					(int) lrDto.getStart(), (int) lrDto.getEnd(),
					lrDto.getTitle());
			toBeReturned
					.setAnnotationAsType(
							de.uni_koeln.spinfo.arc.annotationmodel.annotation.Annotation.AnnotationTypes.LANGUAGE_RANGE,
							lr);
		}

		return toBeReturned;
	}

	@Override
	public Word convertFromDto(WordDto wordDto) {
		FormAnnotationDto faDto = wordDto.getLastFormAnnotation();
		RectangleAnnotationDto raDto = wordDto.getLastRectangleAnnotation();
		FormAnnotation fa = new FormAnnotationImpl(faDto.getDate(),
				faDto.getScore(), faDto.getUserId(), faDto.getForm());
		RectangleAnnotation ra = new RectangleAnnotationImpl(raDto.getDate(),
				raDto.getScore(), raDto.getUserId(), raDto.getX(),
				raDto.getY(), raDto.getWidth(), raDto.getHeight());
		Word toBeReturned = new WordImpl(wordDto.getIndex(), fa, ra);
		return toBeReturned;
	}

	@Override
	public FormAnnotation convertFromDto(FormAnnotationDto faDto) {
		FormAnnotation formAnnotation = new FormAnnotationImpl(faDto.getDate(),
				faDto.getScore(), faDto.getUserId(), faDto.getForm());
		return formAnnotation;
	}

	@Override
	public PosAnnotation convertFromDto(PosAnnotationDto posDto) {
		PosAnnotation.PosTags tagToSave = PosAnnotation.PosTags.NOT_TAGGED;
		for (PosAnnotation.PosTags pt : PosAnnotation.PosTags.values()) {
			if (pt.toString().equals(posDto.getPos().toString()))
				tagToSave = pt;
		}
		PosAnnotation posAnnotation = new PosAnnotationImpl(posDto.getDate(),
				posDto.getScore(), posDto.getUserId(), tagToSave);
		return posAnnotation;
	}

	@Override
	public PosAnnotationDto convertFromDomain(PosAnnotation pos) {
		PosAnnotationDto.PosTags tagToSave = PosAnnotationDto.PosTags.NOT_TAGGED;
		for (PosAnnotationDto.PosTags pt : PosAnnotationDto.PosTags.values()) {
			if (pt.toString().equals(pos.getPos().toString()))
				tagToSave = pt;
		}
		PosAnnotationDto posAnnotationDto = new PosAnnotationDtoImpl(
				pos.getDate(), pos.getScore(), pos.getUserId(), tagToSave);
		return posAnnotationDto;
	}

	@Override
	public RectangleAnnotation convertFromDto(RectangleAnnotationDto raDto) {
		RectangleAnnotation rectangleAnnotation = new RectangleAnnotationImpl(
				raDto.getDate(), raDto.getScore(), raDto.getUserId(),
				raDto.getX(), raDto.getY(), raDto.getWidth(), raDto.getHeight());
		return rectangleAnnotation;
	}

	@Override
	public ChapterRange convertFromDto(ChapterRangeDto crDto) {
		ChapterRange chapterRange = new ChapterRangeImpl(crDto.getDate(),
				crDto.getScore(), crDto.getUserId(), (int) crDto.getStart(),
				(int) crDto.getEnd(), crDto.getTitle());
		return chapterRange;
	}

	@Override
	public LanguageRange convertFromDto(LanguageRangeDto lrDto) {
		LanguageRange languageRange = new LanguageRangeImpl(lrDto.getDate(),
				lrDto.getScore(), lrDto.getUserId(), (int) lrDto.getStart(),
				(int) lrDto.getEnd(), lrDto.getTitle());
		return languageRange;
	}

	@Override
	public WorkingUnitDto convertFromDomain(WorkingUnit workingUnit) {
		WorkingUnitDto toBeReturned = new WorkingUnitDtoImpl(
				workingUnit.getDate(), workingUnit.getScore(),
				workingUnit.getUserId(), workingUnit.getTitle(),
				workingUnit.getStart(), workingUnit.getEnd());
		for (Iterator<PageRange> iterator = workingUnit.getPages().iterator(); iterator
				.hasNext();) {
			PageRange pr = iterator.next();
			PageRangeDto prDto = new PageRangeDtoImpl(pr.getDate(),
					pr.getScore(), pr.getUserId(), (int) pr.getStart(),
					(int) pr.getEnd(), pr.getUrl());
			toBeReturned.setAnnotationAsType(AnnotationTypes.PAGE_RANGE, prDto);
		}
		for (Iterator<ChapterRange> iterator = workingUnit.getChapters()
				.iterator(); iterator.hasNext();) {
			ChapterRange cr = iterator.next();
			ChapterRangeDto crDto = new ChapterRangeDtoImpl(cr.getDate(),
					cr.getScore(), cr.getUserId(), (int) cr.getStart(),
					(int) cr.getEnd(), cr.getTitle());
			toBeReturned.setAnnotationAsType(AnnotationTypes.CHAPTER_RANGE,
					crDto);
		}
		for (Iterator<LanguageRange> iterator = workingUnit.getLanguages()
				.iterator(); iterator.hasNext();) {
			LanguageRange lr = iterator.next();
			LanguageRangeDto lrDto = new LanguageRangeDtoImpl(lr.getDate(),
					lr.getScore(), lr.getUserId(), (int) lr.getStart(),
					(int) lr.getEnd(), lr.getTitle());
			toBeReturned.setAnnotationAsType(AnnotationTypes.LANGUAGE_RANGE,
					lrDto);
		}

		return toBeReturned;
	}

	// @Override
	// public WordDto convertFromDomain(Word word) {
	// FormAnnotation fa = word.getLastFormAnnotation();
	// RectangleAnnotation ra = word.getLastRectangleAnnotation();
	// FormAnnotationDto fDto = new FormAnnotationDtoImpl(fa.getDate(),
	// fa.getScore(), fa.getUserId(), fa.getForm());
	// RectangleAnnotationDto rDto = new
	// RectangleAnnotationDtoImpl(ra.getDate(), ra.getScore(), ra.getUserId(),
	// ra.getX(), ra.getY(), ra.getWidth(), ra.getHeight());
	// WordDto toBeReturned = new WordDtoImpl(
	// word.getIndex(),
	// fDto,
	// rDto
	// );
	//
	//
	// return toBeReturned;
	// }
	//
	@Override
	public WordDtoImpl convertFromDomainImpl(Word word) {
		FormAnnotation fa = word.getLastFormAnnotation();
		RectangleAnnotation ra = word.getLastRectangleAnnotation();
		FormAnnotationDto fDto = new FormAnnotationDtoImpl(fa.getDate(),
				fa.getScore(), fa.getUserId(), fa.getForm());
		RectangleAnnotationDto rDto = new RectangleAnnotationDtoImpl(
				ra.getDate(), ra.getScore(), ra.getUserId(), ra.getX(),
				ra.getY(), ra.getWidth(), ra.getHeight());
		WordDto toBeReturned = new WordDtoImpl(word.getIndex(), fDto, rDto);
		if (word.getLastPosAnnotation() != null) {
			PosAnnotationDto posDto = convertFromDomain(word
					.getLastPosAnnotation());
			toBeReturned.setAnnotationAsType(AnnotationTypes.POS, posDto);
		}
		toBeReturned.setTaggerPosOptions(word.getTaggerPosOptions());
		return (WordDtoImpl) toBeReturned;
	}
}
