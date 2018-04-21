package com.example.biblereading;

/**
 * Created by Neramit777 on 3/17/2018 at 1:26 PM.
 */

public class churchAdapter{

//    private static final int TYPE_ITEM = 0;
//    private static final int TYPE_SEPARATOR = 1;
//
//    private ArrayList<Friend> mData = new ArrayList<Friend>();
//    private TreeSet<Integer> sectionHeader = new TreeSet<Integer>();
//
//    private LayoutInflater mInflater;
//
//    public churchAdapter(Context context) {
//        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//    }
//
//    public void addItem(final Friend item) {
//        mData.add(item);
//        notifyDataSetChanged();
//    }
//
//    public void addSectionHeaderItem(final Friend item) {
//        mData.add(item);
//        sectionHeader.add(mData.size() - 1);
//        notifyDataSetChanged();
//    }
//
//    @Override
//    public int getItemViewType(int position) {
//        return sectionHeader.contains(position) ? TYPE_SEPARATOR : TYPE_ITEM;
//    }
//
//    @Override
//    public int getViewTypeCount() {
//        return 2;
//    }
//
//    @Override
//    public int getCount() {
//        return mData.size();
//    }
//
//    @Override
//    public Friend getItem(int position) {
//        return mData.get(position);
//    }
//
//    @Override
//    public long getItemId(int position) {
//        return position;
//    }
//
//    private class ViewHolder {
//        TextView textView;
//        CircleImageView circleImageView;
//        ImageView statusImage;
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent) {
//        ViewHolder holder;
//        int rowType = getItemViewType(position);
//
//        if (convertView == null) {
//            holder = new ViewHolder();
//            switch (rowType) {
//                case TYPE_ITEM:
//                    convertView = mInflater.inflate(R.layout.friend_listview, null);
//                    holder.textView = (TextView) convertView.findViewById(R.id.friendname);
//                    holder.statusImage = (ImageView) convertView.findViewById(R.id.status_image);
//                    holder.circleImageView = (CircleImageView) convertView.findViewById(R.id.image_user);
//
//                    String friendDisplayName = mData.get(position).getDisplayName();
//                    if (friendDisplayName != null)
//                        holder.textView.setText(friendDisplayName);
//                    else holder.textView.setText(mData.get(position).getFriendUsername());
//
////                    if (mData.get(position).getFavorite()) {
////                        holder.statusImage.setImageResource(R.drawable.star);
////                    }
//
//                    if (mData.get(position).getFriendStatus()==0){
//                        holder.statusImage.setImageResource(R.drawable.letter1);
//                    }
//
//                    String friendDisplayPictureURL = mData.get(position).getDisplayPictureURL();
//                    if (friendDisplayPictureURL != null) {
//                        Glide.with(convertView)
//                                .load(mData.get(position).getDisplayPictureURL())  //Test
//                                .into(holder.circleImageView);
//                    }
//                    break;
//                case TYPE_SEPARATOR:
//                    convertView = mInflater.inflate(R.layout.section_listview, null);
//                    holder.textView = (TextView) convertView.findViewById(R.id.textSequence);
//                    holder.textView.setText(mData.get(position).getFriendUsername());
//                    break;
//            }
//            convertView.setTag(holder);
//        } else {
//            holder = (ViewHolder) convertView.getTag();
//        }
//
//        return convertView;
//    }


}