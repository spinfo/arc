package de.spinfo.arc.editor.client.mvp.views;

import com.google.gwt.user.client.ui.IsWidget;

/**
 * Implementing Views embody the frame of the working unit Editor view
 * It consists of a splitpanel which has a west-side for editing range modifications
 * like Chapter-Ranges in page measures. The center area is split vertically in a north and a south area.
 * the north one should display plain words which are clickable and editable in respect of modifications
 * like form and part-of-speech tagging. The south-area displays the underlying image which comes usually from 
 * a PDF file where the textual content is OCR'ed and the image is embedded in the document.
 * 
 * @author drival
 *
 */
public interface WorkingUnitEditorFrame extends IsWidget {

}
