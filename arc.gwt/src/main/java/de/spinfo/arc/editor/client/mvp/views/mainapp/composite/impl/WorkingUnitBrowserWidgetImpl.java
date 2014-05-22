package de.spinfo.arc.editor.client.mvp.views.mainapp.composite.impl;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.spinfo.arc.editor.client.mvp.views.mainapp.composite.WorkingUnitBrowserWidget;
import de.spinfo.arc.editor.client.mvp.views.mainapp.composite.presenter.MainAppWidgetPresenter;
import de.spinfo.arc.editor.client.mvp.views.mainapp.composite.presenter.WorkingUnitBrowserPresenter;
import de.spinfo.arc.editor.shared.comperator.DateComparator;
import de.spinfo.arc.editor.shared.comperator.IdComparator;
import de.spinfo.arc.editor.shared.comperator.TitleComparator;
import de.spinfo.arc.editor.shared.model.PseudoDataSource;
import de.spinfo.arc.editor.shared.model.WorkingUnit;

public class WorkingUnitBrowserWidgetImpl extends Composite implements WorkingUnitBrowserWidget {

	private static WorkingUnitBrowserWidgetUiBinder uiBinder = GWT
			.create(WorkingUnitBrowserWidgetUiBinder.class);

	interface WorkingUnitBrowserWidgetUiBinder extends
			UiBinder<Widget, WorkingUnitBrowserWidgetImpl> {
	}

	private WorkingUnitBrowserPresenter presenter;


	@Override
	public void setPresenter(WorkingUnitBrowserPresenter presenter) {
		this.presenter = presenter;
		
	}

	@UiField(provided = true)
	DataGrid<WorkingUnit> workingUnitDataGrid;
	@UiField
	SimplePager pager;
	
	public WorkingUnitBrowserWidgetImpl() {
		// must be before initWidget
//		workingUnitDataGrid = UserDefinedCellFactory.buildDataGrid();
		Header<String> idHeader =  new TextHeader("id");
		Header<String> titleHeader =  new TextHeader("title");
		Header<String> uploadDateHeader =  new TextHeader("upload date");
		
		workingUnitDataGrid = new DataGrid<WorkingUnit>();
		
		Column<WorkingUnit, String> columnId = new Column<WorkingUnit, String> (new TextCell()) {
			
			@Override
			public String getValue(WorkingUnit wu) {
				return wu.getId()+"";
			}
			
		};
		columnId.setDataStoreName("idcolumn");
		columnId.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		columnId.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		workingUnitDataGrid.addColumn(columnId, idHeader, idHeader);
		
		Column<WorkingUnit, String> columnTitle = new Column<WorkingUnit, String> (new TextCell()) {
			
			@Override
			public String getValue(WorkingUnit wu) {
				return wu.getTitle();
			}
			
		};
		columnTitle.setDataStoreName("titlecolumn");
		columnTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		columnTitle.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		workingUnitDataGrid.addColumn(columnTitle, titleHeader, titleHeader);
		
		Column<WorkingUnit, String> columnUploadDate = new Column<WorkingUnit, String> (new TextCell()) {
			
			@Override
			public String getValue(WorkingUnit wu) {
				return wu.getUploadDate().toLocaleString();
			}
			
		};
		columnUploadDate.setDataStoreName("uploaddatecolumn");
		columnUploadDate.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		columnUploadDate.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		workingUnitDataGrid.addColumn(columnUploadDate, uploadDateHeader, uploadDateHeader);
		
		
		///////////////////
		//set Data
		///////////////////
		
		ListDataProvider<WorkingUnit> dataProvider = new ListDataProvider<WorkingUnit>();
		
		dataProvider.addDataDisplay(workingUnitDataGrid);
		
		List<WorkingUnit> theList = dataProvider.getList();
		
		PseudoDataSource.createData(111, 11, 111);
		PseudoDataSource.populate(theList, dataProvider);
		
		workingUnitDataGrid.setRowCount(theList.size(), true);
		
		workingUnitDataGrid.setPageSize(50);
		
//		workingUnitDataGrid.setValueUpdater(new ValueUpdater<WorkingUnit>() {
//			
//			@Override
//			public void update(WorkingUnit value) {
//				GWT.log("cellList ValueUpdater - You clicked on item: " + value.getTitle());
//				
//			}
//		});
		
		///////////////////
		// enable sorting
		///////////////////
		
		columnId.setSortable(true);
		columnTitle.setSortable(true);
		columnUploadDate.setSortable(true);
		
		// handler that gets called if the user clicks on a sortable column
		// calls the widgets setVisibleRangeAndClearData which calls the data providers
		// onRangeChanged in the normal way
		ListHandler<WorkingUnit> columnSortHandler = new ListHandler<WorkingUnit>(
				dataProvider.getList());
		
		columnSortHandler.setComparator(columnTitle, new TitleComparator());
		columnSortHandler.setComparator(columnId, new IdComparator());
		columnSortHandler.setComparator(columnUploadDate, new DateComparator());
		
		
		workingUnitDataGrid.addColumnSortHandler(columnSortHandler);
		// We know that the data is sorted by ID by default.
		workingUnitDataGrid.getColumnSortList().push(columnId);
		///////////////////
		//set Selection handler
		///////////////////
		
		setUpWithSingleSelectionModel();
		
		initWidget(uiBinder.createAndBindUi(this));
		this.setSize("100%", "100%");
		pager.setDisplay(workingUnitDataGrid);
		
	}

	private void setUpWithSingleSelectionModel() {
		final SingleSelectionModel<WorkingUnit> singleSelectionModel = new SingleSelectionModel<WorkingUnit>();
		workingUnitDataGrid.setSelectionModel(singleSelectionModel);
		
		singleSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
//				multiSelectionModel.setSelected(multiSelectionModel.getSelectedSet().iterator().next(), true);
				GWT.log("singleSelectionModel onSelectionChange - ");
				WorkingUnit selectedWorkingUnit = singleSelectionModel.getSelectedObject();
					GWT.log("WorkingUnitBrowserWidgetImpl selected item: " + selectedWorkingUnit.getTitle());
					presenter.onWorkingUnitSelected(selectedWorkingUnit);
//					presenter.onWorkingUnitClicked(selectedWorkingUnit);
			}

		});
		
		
	}
	private void setUpWithMultiSelectionModel() {
		final SingleSelectionModel<WorkingUnit> singleSelectionModel = new SingleSelectionModel<WorkingUnit>();
		workingUnitDataGrid.setSelectionModel(singleSelectionModel);
		
		singleSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
//				multiSelectionModel.setSelected(multiSelectionModel.getSelectedSet().iterator().next(), true);
				GWT.log("multiSelectionModel onSelectionChange - ");
				Iterator<WorkingUnit> it = singleSelectionModel.getSelectedSet().iterator();
				while (it.hasNext()) {
					GWT.log("selected item: " + it.next().getTitle());
				}
			}
		});
		
		
	}
	
}
