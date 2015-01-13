package de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.view.impl;

import java.util.Iterator;

import com.google.gwt.cell.client.TextCell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;

import de.uni_koeln.spinfo.arc.dto.annotatable.WorkingUnitDto;
import de.uni_koeln.spinfo.arc.editor.client.mvp.events.ui.BusyIndicator;
import de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.presenter.WorkingUnitBrowserWidgetPresenter;
import de.uni_koeln.spinfo.arc.editor.client.mvp.workingunitbrowser.view.WorkingUnitBrowserWidget;
import de.uni_koeln.spinfo.arc.editor.shared.service.workingunit.WorkingUnitDataGridAsyncDataProvider;

public class WorkingUnitBrowserWidgetImpl extends Composite implements
		WorkingUnitBrowserWidget {

	private static WorkingUnitBrowserWidgetUiBinder uiBinder = GWT
			.create(WorkingUnitBrowserWidgetUiBinder.class);

	interface WorkingUnitBrowserWidgetUiBinder extends
			UiBinder<Widget, WorkingUnitBrowserWidgetImpl> {
	}

	private WorkingUnitBrowserWidgetPresenter presenter;

	@Override
	public void setPresenter(WorkingUnitBrowserWidgetPresenter presenter) {
		this.presenter = presenter;

	}

	@UiField(provided = true)
	DataGrid<WorkingUnitDto> workingUnitDataGrid;
	
	@UiField
	SimplePager pager;

	// ListDataProvider<WorkingUnit> dataProvider;
	WorkingUnitDataGridAsyncDataProvider dataProviderAsync;

	public WorkingUnitBrowserWidgetImpl() {
		// must be before initWidget
		// workingUnitDataGrid = UserDefinedCellFactory.buildDataGrid();
		Header<String> idHeader = new TextHeader("title");
		Header<String> titleHeader = new TextHeader("amount pages");
		Header<String> uploadDateHeader = new TextHeader("upload date");

		workingUnitDataGrid = new DataGrid<WorkingUnitDto>();

		Column<WorkingUnitDto, String> columnId = new Column<WorkingUnitDto, String>(new TextCell()) {

			@Override
			public String getValue(WorkingUnitDto wu) {
				return wu.getTitle();
			}

		};
		columnId.setDataStoreName("idcolumn");
		columnId.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		columnId.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		workingUnitDataGrid.addColumn(columnId, idHeader, idHeader);

		Column<WorkingUnitDto, String> columnTitle = new Column<WorkingUnitDto, String>(
				new TextCell()) {

			@Override
			public String getValue(WorkingUnitDto wu) {
				return wu.getPages().size() + "";
			}

		};
		columnTitle.setDataStoreName("titlecolumn");
		columnTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		columnTitle.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		workingUnitDataGrid.addColumn(columnTitle, titleHeader, titleHeader);

		Column<WorkingUnitDto, String> columnUploadDate = new Column<WorkingUnitDto, String>(
				new TextCell()) {

			@Override
			public String getValue(WorkingUnitDto wu) {
				return wu.getDate().toLocaleString();
			}

		};
		columnUploadDate.setDataStoreName("uploaddatecolumn");
		columnUploadDate
				.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		columnUploadDate.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		workingUnitDataGrid.addColumn(columnUploadDate, uploadDateHeader,
				uploadDateHeader);

		// /////////////////
		// set Data
		// /////////////////

		// createWithListDataProvider();

		createWithAsyncDataProvider();

		workingUnitDataGrid.setPageSize(50);

		// workingUnitDataGrid.setValueUpdater(new ValueUpdater<WorkingUnit>() {
		//
		// @Override
		// public void update(WorkingUnit value) {
		// GWT.log("cellList ValueUpdater - You clicked on item: " +
		// value.getTitle());
		//
		// }
		// });

		// /////////////////
		// enable sorting
		// /////////////////

		// columnId.setSortable(true);
		// columnTitle.setSortable(true);
		// columnUploadDate.setSortable(true);

		// handler that gets called if the user clicks on a sortable column
		// calls the widgets setVisibleRangeAndClearData which calls the data
		// providers
		// onRangeChanged in the normal way
		// ListHandler<WorkingUnit> columnSortHandler = new
		// ListHandler<WorkingUnit>(
		// dataProvider.getList());
		//
		// columnSortHandler.setComparator(columnTitle, new TitleComparator());
		// columnSortHandler.setComparator(columnId, new IdComparator());
		// columnSortHandler.setComparator(columnUploadDate, new
		// DateComparator());

		// workingUnitDataGrid.addColumnSortHandler(columnSortHandler);
		// We know that the data is sorted by ID by default.
		// workingUnitDataGrid.getColumnSortList().push(columnId);
		// /////////////////
		// set Selection handler
		// /////////////////

		setUpWithSingleSelectionModel();

		initWidget(uiBinder.createAndBindUi(this));
		this.setSize("100%", "100%");
		pager.setDisplay(workingUnitDataGrid);
		BusyIndicator.free();

	}

	private void createWithAsyncDataProvider() {
		dataProviderAsync = new WorkingUnitDataGridAsyncDataProvider();
		dataProviderAsync.addDataDisplay(workingUnitDataGrid);
		dataProviderAsync.updateRowCount(99, false);
		GWT.log("createWithAsyncDataProvider");
	}

	// private void createWithListDataProvider() {
	// dataProvider = new ListDataProvider<WorkingUnit>();
	// dataProvider.addDataDisplay(workingUnitDataGrid);
	//
	// List<WorkingUnit> theList = dataProvider.getList();
	//
	// PseudoDataSource.createData(111, 11, 111);
	// PseudoDataSource.populate(theList, dataProvider);
	//
	// workingUnitDataGrid.setRowCount(theList.size(), true);
	//
	// }

	private void setUpWithSingleSelectionModel() {
		final SingleSelectionModel<WorkingUnitDto> singleSelectionModel = new SingleSelectionModel<WorkingUnitDto>();
		workingUnitDataGrid.setSelectionModel(singleSelectionModel);

		singleSelectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

					@Override
					public void onSelectionChange(SelectionChangeEvent event) {
						// multiSelectionModel.setSelected(multiSelectionModel.getSelectedSet().iterator().next(),
						// true);
						GWT.log("singleSelectionModel onSelectionChange - ");
						WorkingUnitDto selectedWorkingUnit = singleSelectionModel
								.getSelectedObject();
						GWT.log("WorkingUnitBrowserWidgetImpl selected item: "
								+ selectedWorkingUnit.getTitle());
						presenter.onWorkingUnitSelected(selectedWorkingUnit);
						// presenter.onWorkingUnitClicked(selectedWorkingUnit);
					}

				});

	}

	private void setUpWithMultiSelectionModel() {
		final SingleSelectionModel<WorkingUnitDto> singleSelectionModel = new SingleSelectionModel<WorkingUnitDto>();
		workingUnitDataGrid.setSelectionModel(singleSelectionModel);

		singleSelectionModel
				.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

					@Override
					public void onSelectionChange(SelectionChangeEvent event) {
						// multiSelectionModel.setSelected(multiSelectionModel.getSelectedSet().iterator().next(),
						// true);
						GWT.log("multiSelectionModel onSelectionChange - ");
						Iterator<WorkingUnitDto> it = singleSelectionModel
								.getSelectedSet().iterator();
						while (it.hasNext()) {
							GWT.log("selected item: " + it.next().getTitle());
						}
					}
				});

	}

}
