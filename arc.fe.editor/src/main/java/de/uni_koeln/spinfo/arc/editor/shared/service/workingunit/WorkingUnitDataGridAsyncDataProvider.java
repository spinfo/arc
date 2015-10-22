package de.uni_koeln.spinfo.arc.editor.shared.service.workingunit;

import java.util.List;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.view.client.AsyncDataProvider;
import com.google.gwt.view.client.HasData;
import com.google.gwt.view.client.Range;

import de.uni_koeln.spinfo.arc.dto.annotatable.WorkingUnitDto;

public class WorkingUnitDataGridAsyncDataProvider extends
		AsyncDataProvider<WorkingUnitDto> {

	private AnnotationModelServiceAsync rpcService;

	public WorkingUnitDataGridAsyncDataProvider() {
		rpcService = GWT.create(AnnotationModelService.class);
	}

	@Override
	protected void onRangeChanged(HasData<WorkingUnitDto> display) {
		final Range range = display.getVisibleRange();
		rpcService.getWorkingUnits(new AsyncCallback<List<WorkingUnitDto>>() {

			// There's been a failure in the RPC call
			// Normally you would handle that in a good way,
			// here we just throw up an alert.
			public void onFailure(Throwable caught) {
				Window.alert("Super Error" + caught.getMessage());
			}

			// We've successfully for the data from the RPC call,
			// Now we update the row data with that result starting
			// at a particular row in the cell widget (usually the range start)
			public void onSuccess(List<WorkingUnitDto> result) {
				updateRowData(range.getStart(), result);
			}
		});
	}
}