package com.Polynt.ui.adapters;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SpinnerAdapter;

import com.Polynt.ui.viewbinders.ViewBinder;


/**
 *
 * @param <T>
 *            The entity that will be passed into this adapter
 */

public class ArrayListAdapter<T> extends BaseAdapter implements SpinnerAdapter, Filterable{

	protected Activity mContext;
	protected List<T> arrayList;
	protected ViewBinder<T> viewBinder;
	protected List<T> ActualListCopy;


	public ArrayListAdapter(Activity context, List<T> arrayList,
			ViewBinder<T> viewBinder) {
		mContext = context;
		this.arrayList = arrayList;
		this.viewBinder = viewBinder;
		this.ActualListCopy = arrayList;
	}

	public ArrayListAdapter(Activity context, ViewBinder<T> viewBinder) {
		this(context, new ArrayList<T>(), viewBinder);
	}

	public void setArrayList(List<T> arrayList) {
		this.arrayList = arrayList;
	}

	/**
	 * Clears the internal list
	 */
	public void clearList() {
		arrayList.clear();
		notifyDataSetChanged();
	}

	/**
	 * Adds a entity to the list and calls {@link #notifyDataSetChanged()}.
	 * Should not be used if lots of entities are added.
	 * 
	 * @see #addAll(java.util.List)
	 */
	public void add(T entity) {
		arrayList.add(entity);
		notifyDataSetChanged();
	}

	/**
	 * Adds a entities to the list and calls {@link #notifyDataSetChanged()}.
	 * Can be used {{@link java.util.List#subList(int, int)}.
	 * 
	 * @see #addAll(java.util.List)
	 */
	public void addAll(List<T> entityList) {
		arrayList.addAll(entityList);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return arrayList.size();
	}

	@Override
	public Object getItem(int position) {
		return arrayList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View view, ViewGroup arg2) {

		View convertView = view;

		if (convertView == null) {
			convertView = viewBinder.createView(mContext);
			
		}
		

		viewBinder.bindView(arrayList.get(position), position, 0,
				convertView, mContext);

		return convertView;
	}
	
	public T getItemFromList(int index) {
		return arrayList.get(index);
	}
	
	public List<T> getList() {
		return arrayList;
	}

	@Override
	public Filter getFilter() {
		Filter filer = new Filter() {
			@Override
			protected void publishResults(CharSequence constraint, FilterResults results) {
				@SuppressWarnings("unchecked")
				List<T> list = (List<T>) results.values;
				arrayList = list;
				notifyDataSetChanged();
			}

			@Override
			protected FilterResults performFiltering(CharSequence constraint) {
				FilterResults results = new FilterResults();
				List<T> FilteredNames = new ArrayList<T>();
				constraint = constraint.toString().toLowerCase();
				if (constraint.length() > 0) {
					for (int i = 0; i < ActualListCopy.size(); i++) {
						T data = ActualListCopy.get(i);
						FilteredNames.add(data);
					}
					results.values = FilteredNames;
					results.count = FilteredNames.size();
					return results;
				} else {
					results.values = ActualListCopy;
					results.count = ActualListCopy.size();
					return results;
				}

			}
		};
		return filer;
	}
	
}
