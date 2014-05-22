package de.spinfo.arc.editor.client;


import java.util.Date;
import java.util.List;





import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootLayoutPanel;

import de.spinfo.arc.editor.client.cactus.views.impl.ModificationDialogViewImpl;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class CactusTest implements EntryPoint {
	
	public void onModuleLoad() {
		
//Modifiable mod = new ModifiableImpl();
		
//		Modification stringMod1= new ModificationStringImpl("firstMod", new Date(), 1, mod);
//		Modification stringMod2= new ModificationStringImpl("secondMod", new Date(), 1, mod);
//		
//		UsableGwtRectangle rect1 = new UsableGwtRectangleImpl(1, 1, 20, 20);
//		UsableGwtRectangle rect2 = new UsableGwtRectangleImpl(6, 6, 1000, 1000);
//		
//		Modification rectMod1 = new ModificationRectangleImpl(rect1, new Date(), 1, mod);
//		Modification rectMod2 = new ModificationRectangleImpl(rect2, new Date(), 1, mod);
//		
//		mod.appendModification(MODIFICATION.Types.TEXT_MOD, stringMod1);
//		mod.appendModification(MODIFICATION.Types.TEXT_MOD, stringMod2);
//		mod.appendModification(MODIFICATION.Types.TEXT_MOD, rectMod1);
//		mod.appendModification(MODIFICATION.Types.TEXT_MOD, rectMod2);
//		
//		List<Modification> textList = mod.getModifications(MODIFICATION.Types.TEXT_MOD);
//////		List<Modificaton<Modifiable2>> rectList = mod.getModifications(Mod.Types.RECTANGLE_MOD);
////		
//		for (Modification e : textList) {
////		System.out.println(e.toString());
//		 if (e instanceof ModificationStringImpl) {
//			 ModificationStringImpl rm = (ModificationStringImpl) e;
//			 RootLayoutPanel.get().add(new Label("StringMOd!"));
//			 RootLayoutPanel.get().add(new Label(rm.getPayload()));
////			 if ( instanceof )
//		 } else if (e instanceof ModificationRectangleImpl) {
//			 ModificationRectangleImpl rm = (ModificationRectangleImpl) e;
//			 RootLayoutPanel.get().add(new Label("RectMOd!"));
//			 RootLayoutPanel.get().add(new Label(rm.getPayload().getX()+""));
//		 }
//	}
		
		
		
		
		
		
//		RootLayoutPanel.get().add(new SuperTreeSample());
		RootLayoutPanel.get().add(new CactusPanel());
		
//		new ModificationDialogViewImpl().show();
		
		
		
		
		
		
	}
}
