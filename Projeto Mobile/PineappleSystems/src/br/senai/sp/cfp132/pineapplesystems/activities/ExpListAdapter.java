package br.senai.sp.cfp132.pineapplesystems.activities;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;
import br.senai.sp.cfp132.pineapplesystems.R;

public class ExpListAdapter extends BaseExpandableListAdapter {
	private HashMap<String, List<String>> listData;
	private List<String> listGroup;
	private LayoutInflater inflater;
	private boolean patInvalidos;

	public ExpListAdapter(Context ctx,
			HashMap<String, List<String>> listData, List<String> listGroup, boolean patInvalidos) {
		this.listData = listData;
		this.listGroup = listGroup;
		inflater = (LayoutInflater) ctx
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.patInvalidos = patInvalidos;
	}
	
	public ExpListAdapter() {
	}

	@Override
	public int getGroupCount() {

		return listGroup.size();
	}

	@Override
	public int getChildrenCount(int arg0) {

		return listData.get(listGroup.get(arg0)).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		return listGroup.get(groupPosition);
	}

	@Override
	public Object getChild(int parent, int child) {
		return listData.get(listGroup.get(parent)).get(child);
	}

	@Override
	public long getGroupId(int arg0) {

		return arg0;
	}

	@Override
	public long getChildId(int parent, int child) {
		return child;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public View getGroupView(int parent, boolean isExpanded, View convertView,
			ViewGroup parentView) {
		ViewHolderGroup holder;

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.parent_layout, null);
			holder = new ViewHolderGroup();
			convertView.setTag(holder);

			holder.parent_txt = (TextView) convertView
					.findViewById(R.id.parent_txt);
		} else {
			holder = (ViewHolderGroup) convertView.getTag();
		}

		holder.parent_txt.setText(listGroup.get(parent));

		return convertView;
	}

	@Override
	public View getChildView(int parent, int child, boolean lastChild,
			View convertView, ViewGroup parentView) {

		ViewHolderItem holder;
		
		String val = (String) getChild(parent, child);

		if (convertView == null) {
			convertView = inflater.inflate(R.layout.child_layout, null);
			holder = new ViewHolderItem();
			convertView.setTag(holder);

			holder.child_txt = (TextView) convertView
					.findViewById(R.id.child_txt);
			
			if (patInvalidos == true) {
				holder.child_txt.setTextColor(Color.rgb(255, 70, 0));				
			}
		} else {
			holder = (ViewHolderItem) convertView.getTag();
		}

		holder.child_txt.setText(val);

		return convertView;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return patInvalidos;
	}

	class ViewHolderGroup {
		TextView parent_txt;
	}

	class ViewHolderItem {
		TextView child_txt;
	}

}
