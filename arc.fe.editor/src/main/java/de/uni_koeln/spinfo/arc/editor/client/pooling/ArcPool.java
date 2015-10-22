package de.uni_koeln.spinfo.arc.editor.client.pooling;

import java.util.ArrayList;
import java.util.List;

import de.uni_koeln.spinfo.arc.editor.shared.until.DebugHelper;
/**
 * Class to store and maintain pooled instances of a type
 * 
 * @author D. Rival
 *
 * @param <T> the type of the object to be pooled
 */
public abstract class ArcPool<T> {
	
	private static boolean DEBUG = true;
	
	private final ArrayList<T> objects = new ArrayList<>();
	
	/**
	 * 
	 * @param amount the amount of objects to be returned from the pool. If pooled no new ones get created
	 * @return A List with pooled instances of type T
	 */
	public List<T> obtainMulti (int amount) {
		if (!objects.isEmpty()) {
			if (objects.size() >= amount ) {
				if (DEBUG) DebugHelper.print(this.getClass(), "List is not Empty and greater or equal to needed amount\n returning a subset of existing ones\n returned widgets:  " + objects.subList(0, amount).size() + " of total Widgets: " + objects.size() , true);
				return objects.subList(0, amount);
			}
			else {
				int difference = amount - objects.size();
				for (int i = 0; i < difference; i++) {
					objects.add(createNewInstance());
				} 
				return objects;
			}
		}
		else {
			for (int i = 0; i < amount; i++) {
//				Class
				objects.add(createNewInstance());
			} 
			if (DEBUG) DebugHelper.print(this.getClass(), "List is Empty\n created new ones: " + objects.size() , true);
			return  objects;
		}
		
	}
	
	public T obtainSingle() {
//		T instance = null;
//		System.out.println("is pool empty? : " + objects.isEmpty());
//		if (objects.isEmpty()) {
//			instance = createNewInstance();
//			objects.add(instance);
//		} else {
//			instance = objects.get(objects.size()-1);
//		}
//		return instance;
		T toReturn = objects.isEmpty() ? createNewInstance() : objects.get(objects.size()-1);
		return toReturn;
	}
	
	public void freeObjects(List<T> objects) {
		if (objects == null || objects.isEmpty()) throw new IllegalArgumentException("List cannot be null or empty.");
		objects.addAll(objects);
	}
	
	public void freeObject(T object) {
		if (object == null) throw new IllegalArgumentException("object cannot be null.");
		objects.add(object);
	}
	
	public int getAmountFreeObjects() {
		return objects.size();
	}
	 /**
	  * Override this method in order to implement specific creation of pooled objects. 
	  * This method is called by {@link #obtainMulti(int) and {@link #obtainSingle()} in order to create an object and
	  * append it to the list of pooled instances.<br>
	  * <b>THIS SHOULD NOT BE USED TO OBTAIN INSTANCES.<br>
	  * IMPLEMENTATIONS DESCRIBE JUST THE CREATION.<br>
	  * USE {@link #obtainMulti(int) OR {@link #obtainSingle()} TO OBTAIN ACTUAL INSTANCES INSTEAD!</b>
	  * 
	  * @return a type of the specified instance in order to append it to the pool
	  */
	 abstract public T createNewInstance();
	 
	 @Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("pooled instances size: " + objects.size());
		sb.append("\n");
		return sb.toString();
	}
}
