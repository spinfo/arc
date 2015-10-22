package de.uni_koeln.spinfo.arc.editor.server.workingunit;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.WorkingUnit;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WordImpl;
import de.uni_koeln.spinfo.arc.annotationmodel.annotatable.impl.WorkingUnitImpl;
import de.uni_koeln.spinfo.arc.dto.annotatable.WorkingUnitDto;
import de.uni_koeln.spinfo.arc.dto.annotatable.impl.WordDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotatable.impl.WorkingUnitDtoImpl;
import de.uni_koeln.spinfo.arc.dto.annotation.ChapterRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.FormAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.LanguageRangeDto;
import de.uni_koeln.spinfo.arc.dto.annotation.PosAnnotationDto;
import de.uni_koeln.spinfo.arc.dto.annotation.RectangleAnnotationDto;
import de.uni_koeln.spinfo.arc.editor.server.persistance.config.MongoConfiguration;
import de.uni_koeln.spinfo.arc.editor.server.persistance.service.update.WordUpdater;
import de.uni_koeln.spinfo.arc.editor.server.persistance.service.update.WorkingUnitUpdater;
import de.uni_koeln.spinfo.arc.editor.shared.service.workingunit.AnnotationModelService;

//@SuppressWarnings("serial")
public class AnnotationModelServiceImpl extends RemoteServiceServlet implements AnnotationModelService {

	private static final long serialVersionUID = 1L;

	ApplicationContext ctx;
	ModelConverter modelConverter;
	MongoOperations mongoOperations;

	WordUpdater wordUpdater;
	WorkingUnitUpdater workingUnitUpdater;

	public AnnotationModelServiceImpl() {
		ctx = new AnnotationConfigApplicationContext(MongoConfiguration.class);
		modelConverter = new ModelConverterImpl();
		mongoOperations = (MongoOperations) ctx.getBean("mongoTemplate");

		wordUpdater = new WordUpdater();
		workingUnitUpdater = new WorkingUnitUpdater();
	}

	@Override
	public List<WorkingUnitDto> getWorkingUnits() {
		List<WorkingUnitDto> toBeReturned = new ArrayList<>();

		List<WorkingUnitImpl> retrieved = mongoOperations
				.findAll(WorkingUnitImpl.class);

		for (Iterator iterator = retrieved.iterator(); iterator.hasNext();) {
			WorkingUnit workingUnit = (WorkingUnit) iterator.next();
			toBeReturned.add(modelConverter.convertFromDomain(workingUnit));
		}
		return toBeReturned;
	}

	@Override
	public WorkingUnitDto getWorkingUnit(String title) {
		WorkingUnit retrieved = mongoOperations.findOne(
				Query.query(Criteria.where("title").is(title)),
				WorkingUnitImpl.class);
		WorkingUnitDto toBeReturned = modelConverter
				.convertFromDomain(retrieved);
		return toBeReturned;
	}

	@Override
	public WorkingUnitDtoImpl getWorkingUnitImpl(String title) {
		return (WorkingUnitDtoImpl) getWorkingUnit(title);
	}

	@Override
	public List<WordDtoImpl> getWords(int start, int end) {
		List<WordImpl> retrieved = mongoOperations.find(
				Query.query(Criteria.where("index").gte(start).lte(end)),
				WordImpl.class);
		List<WordDtoImpl> toBeReturned = new ArrayList<>();
		for (Iterator<WordImpl> iterator = retrieved.iterator(); iterator
				.hasNext();) {
			WordImpl wordImpl = iterator.next();
			toBeReturned.add(modelConverter.convertFromDomainImpl(wordImpl));
		}
		return toBeReturned;
	}

	@Override
	public synchronized String updateRectangle(long index,
			RectangleAnnotationDto rectangleAnnotation) {
		// WordUpdater wordUpdater = new WordUpdater();
		return wordUpdater.pushRect(index,
				modelConverter.convertFromDto(rectangleAnnotation)).toString();
	}

	@Override
	public synchronized String updateForm(long index,
			FormAnnotationDto formAnnotation) {
		// WordUpdater wordUpdater = new WordUpdater();
		return wordUpdater.pushForm(index,
				modelConverter.convertFromDto(formAnnotation)).toString();
	}

	@Override
	public synchronized String updatePos(long index,
			PosAnnotationDto posAnnotation) {
		// WordUpdater wordUpdater = new WordUpdater();
		return wordUpdater.pushPos(index,
				modelConverter.convertFromDto(posAnnotation)).toString();
	}

	@Override
	public synchronized String pushChapter(String workingUnitTitle,
			ChapterRangeDto chapterRange) {
		// WorkingUnitUpdater workingUnitUpdater = new WorkingUnitUpdater();
		return workingUnitUpdater.pushChapter(workingUnitTitle,
				modelConverter.convertFromDto(chapterRange)).toString();
	}

	@Override
	public synchronized String pushLanguage(String workingUnitTitle,
			LanguageRangeDto languageRange) {
		// WorkingUnitUpdater workingUnitUpdater = new WorkingUnitUpdater();
		return workingUnitUpdater.pushLanguage(workingUnitTitle,
				modelConverter.convertFromDto(languageRange)).toString();
	}

	@Override
	public synchronized String pullChapter(String workingUnitTitle,
			ChapterRangeDto chapterRange) {
		// WorkingUnitUpdater workingUnitUpdater = new WorkingUnitUpdater();
		return workingUnitUpdater.pullChapter(workingUnitTitle,
				modelConverter.convertFromDto(chapterRange)).toString();
	}

	@Override
	public synchronized String pullLanguage(String workingUnitTitle,
			LanguageRangeDto languageRange) {
		// WorkingUnitUpdater workingUnitUpdater = new WorkingUnitUpdater();
		return workingUnitUpdater.pullLanguage(workingUnitTitle,
				modelConverter.convertFromDto(languageRange)).toString();
	}

}
