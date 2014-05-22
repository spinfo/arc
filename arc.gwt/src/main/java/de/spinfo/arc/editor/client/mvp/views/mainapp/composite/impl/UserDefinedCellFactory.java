package de.spinfo.arc.editor.client.mvp.views.mainapp.composite.impl;

import java.util.Iterator;
import java.util.List;

import com.google.gwt.cell.client.FieldUpdater;
import com.google.gwt.cell.client.TextCell;
import com.google.gwt.cell.client.ValueUpdater;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.user.cellview.client.CellList;
import com.google.gwt.user.cellview.client.CellTable;
import com.google.gwt.user.cellview.client.CellWidget;
import com.google.gwt.user.cellview.client.Column;
import com.google.gwt.user.cellview.client.DataGrid;
import com.google.gwt.user.cellview.client.Header;
import com.google.gwt.user.cellview.client.SimplePager;
import com.google.gwt.user.cellview.client.TextHeader;
import com.google.gwt.user.cellview.client.ColumnSortEvent.ListHandler;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.MultiSelectionModel;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SelectionModel;

import de.spinfo.arc.editor.shared.comperator.DateComparator;
import de.spinfo.arc.editor.shared.comperator.IdComparator;
import de.spinfo.arc.editor.shared.comperator.TitleComparator;
import de.spinfo.arc.editor.shared.model.PseudoDataSource;
import de.spinfo.arc.editor.shared.model.WorkingUnit;

public class UserDefinedCellFactory {
	
	static private List<WorkingUnit> workingUnits ;
	
	public UserDefinedCellFactory() {
		PseudoDataSource.setCreatingPseudoFormMods(true);
		PseudoDataSource.setCreatingPseudoPosTags(true);
		workingUnits = PseudoDataSource.createData(2, 2, 11);
	}
	
	public static  WorkingUnit getWorkingUnit() {
		System.out.println(workingUnits.get(0));
		return workingUnits.get(0);
	}
	
	
	public static CellTable<WorkingUnit> buildCellTable() {
		
		Header<String> idHeader =  new TextHeader("id");
		Header<String> titleHeader =  new TextHeader("title");
		Header<String> uploadDateHeader =  new TextHeader("upload date");
		
		CellTable<WorkingUnit> cellTable = new CellTable<WorkingUnit>();
		
		Column<WorkingUnit, String> columnId = new Column<WorkingUnit, String> (new TextCell()) {
			
			@Override
			public String getValue(WorkingUnit wu) {
				return wu.getId()+"";
			}
			
		};
		columnId.setDataStoreName("idcolumn");
		cellTable.addColumn(columnId, idHeader, idHeader);
		
		Column<WorkingUnit, String> columnTitle = new Column<WorkingUnit, String> (new TextCell()) {
			
			@Override
			public String getValue(WorkingUnit wu) {
				return wu.getTitle();
			}
			
		};
		columnTitle.setDataStoreName("titlecolumn");
		cellTable.addColumn(columnTitle, titleHeader, titleHeader);
		
		Column<WorkingUnit, String> columnUploadDate = new Column<WorkingUnit, String> (new TextCell()) {
			
			@Override
			public String getValue(WorkingUnit wu) {
				return wu.getUploadDate().toLocaleString();
			}
			
		};
		columnUploadDate.setDataStoreName("uploaddatecolumn");
		cellTable.addColumn(columnUploadDate, uploadDateHeader, uploadDateHeader);
		
		
		///////////////////
		//set Data
		///////////////////
		
		ListDataProvider<WorkingUnit> dataProvider = new ListDataProvider<WorkingUnit>();
		
		dataProvider.addDataDisplay(cellTable);
		
		List<WorkingUnit> theList = dataProvider.getList();
		
		PseudoDataSource.populate(theList, dataProvider);
		
		cellTable.setRowCount(theList.size(), true);
		
		cellTable.setPageSize(10);
		
//		cellTable.setValueUpdater(new ValueUpdater<WorkingUnit>() {
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
		
		
		cellTable.addColumnSortHandler(columnSortHandler);
		// We know that the data is sorted by ID by default.
		cellTable.getColumnSortList().push(columnId);
		///////////////////
		//set Selection handler
		///////////////////
		
		
		final MultiSelectionModel<WorkingUnit> multiSelectionModel = new MultiSelectionModel<WorkingUnit>();
		cellTable.setSelectionModel(multiSelectionModel);
		
		multiSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
//				multiSelectionModel.setSelected(multiSelectionModel.getSelectedSet().iterator().next(), true);
				GWT.log("multiSelectionModel onSelectionChange - ");
				Iterator<WorkingUnit> it = multiSelectionModel.getSelectedSet().iterator();
				while (it.hasNext())
					GWT.log("selected item: " + it.next().getTitle());
			}
		});
		
		
		
		return cellTable;
	}
	
	public static DataGrid<WorkingUnit> buildDataGrid() {
		
		Header<String> idHeader =  new TextHeader("id");
		Header<String> titleHeader =  new TextHeader("title");
		Header<String> uploadDateHeader =  new TextHeader("upload date");
		
		DataGrid<WorkingUnit> cellTable = new DataGrid<WorkingUnit>();
		
		Column<WorkingUnit, String> columnId = new Column<WorkingUnit, String> (new TextCell()) {
			
			@Override
			public String getValue(WorkingUnit wu) {
				return wu.getId()+"";
			}
			
		};
		columnId.setDataStoreName("idcolumn");
		columnId.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		columnId.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		cellTable.addColumn(columnId, idHeader, idHeader);
		
		Column<WorkingUnit, String> columnTitle = new Column<WorkingUnit, String> (new TextCell()) {
			
			@Override
			public String getValue(WorkingUnit wu) {
				return wu.getTitle();
			}
			
		};
		columnTitle.setDataStoreName("titlecolumn");
		columnTitle.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		columnTitle.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		cellTable.addColumn(columnTitle, titleHeader, titleHeader);
		
		Column<WorkingUnit, String> columnUploadDate = new Column<WorkingUnit, String> (new TextCell()) {
			
			@Override
			public String getValue(WorkingUnit wu) {
				return wu.getUploadDate().toLocaleString();
			}
			
		};
		columnUploadDate.setDataStoreName("uploaddatecolumn");
		columnUploadDate.setHorizontalAlignment(HasHorizontalAlignment.ALIGN_LEFT);
		columnUploadDate.setVerticalAlignment(HasVerticalAlignment.ALIGN_TOP);
		cellTable.addColumn(columnUploadDate, uploadDateHeader, uploadDateHeader);
		
		
		///////////////////
		//set Data
		///////////////////
		
		ListDataProvider<WorkingUnit> dataProvider = new ListDataProvider<WorkingUnit>();
		
		dataProvider.addDataDisplay(cellTable);
		
		List<WorkingUnit> theList = dataProvider.getList();
		
		PseudoDataSource.populate(theList, dataProvider);
		
		cellTable.setRowCount(theList.size(), true);
		
		cellTable.setPageSize(30);
		
//		cellTable.setValueUpdater(new ValueUpdater<WorkingUnit>() {
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
		
		
		cellTable.addColumnSortHandler(columnSortHandler);
		// We know that the data is sorted by ID by default.
		cellTable.getColumnSortList().push(columnId);
		///////////////////
		//set Selection handler
		///////////////////
		
		
		final MultiSelectionModel<WorkingUnit> multiSelectionModel = new MultiSelectionModel<WorkingUnit>();
		cellTable.setSelectionModel(multiSelectionModel);
		
		multiSelectionModel.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {
			
			@Override
			public void onSelectionChange(SelectionChangeEvent event) {
//				multiSelectionModel.setSelected(multiSelectionModel.getSelectedSet().iterator().next(), true);
				GWT.log("multiSelectionModel onSelectionChange - ");
				Iterator<WorkingUnit> it = multiSelectionModel.getSelectedSet().iterator();
				while (it.hasNext()) {
					GWT.log("selected item: " + it.next().getTitle());
					GWT.log("selected item: " + it.next().getTitle());
					
				}
			}
		});
		
		
		
		return cellTable;
	}
}
