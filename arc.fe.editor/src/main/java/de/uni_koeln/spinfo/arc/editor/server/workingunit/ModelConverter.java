package de.uni_koeln.spinfo.arc.editor.server.workingunit;

import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.Word;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.ChapterRange;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.FormAnnotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.LanguageRange;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.PosAnnotation;
import de.uni_koeln.spinfo.arc.annotationmodel.annotation.RectangleAnnotation;
import de.uni_koeln.spinfo.arc.dto.annotatable.WordDto;
import de.uni_koeln.spinfo.arc.dto.annotatable.WorkingUnitDto;
import de.uni_koeln.spinfo.arc.dto.annotatable.impl.WordDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.ChapterRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.FormAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.LanguageRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.PosAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.RectangleAnnotationDto;

/**
 * Implementing Classes take care of converting the Domain Model to the GWT
 * translatable DTO back and forth. Instances are meant to be used by the
 * AsncService which need Java Types which can be compiled to Javascript (thus
 * do not have Spring Data imports and annotations)
 * 
 * @author david
 *
 */
public interface ModelConverter {

	WorkingUnit convertFromDto(WorkingUnitDto workingUnitDto);

	Word convertFromDto(WordDto wordDto);

	WorkingUnitDto convertFromDomain(WorkingUnit workingUnit);

	// WordDto convertFromDomain (Word word);
	WordDtoImpl convertFromDomainImpl(Word word);

	PosAnnotation convertFromDto(PosAnnotationDto posDto);

	RectangleAnnotation convertFromDto(RectangleAnnotationDto raDto);

	FormAnnotation convertFromDto(FormAnnotationDto faDto);

	ChapterRange convertFromDto(ChapterRangeDto crDto);

	LanguageRange convertFromDto(LanguageRangeDto lrDto);

	PosAnnotationDto convertFromDomain(PosAnnotation pos);

}
