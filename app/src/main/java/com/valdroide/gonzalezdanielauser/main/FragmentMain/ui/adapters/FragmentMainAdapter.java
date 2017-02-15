package com.valdroide.gonzalezdanielauser.main.FragmentMain.ui.adapters;


import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.valdroide.gonzalezdanielauser.R;
import com.valdroide.gonzalezdanielauser.entities.Clothes;
import com.valdroide.gonzalezdanielauser.main.FragmentMain.ui.FragmentMain;
import com.valdroide.gonzalezdanielauser.utils.Utils;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FragmentMainAdapter extends RecyclerView.Adapter<FragmentMainAdapter.ViewHolder> {


    private List<Clothes> clothesList;
    private OnItemClickListener onItemClickListener;
    private Fragment fragment;

    public FragmentMainAdapter(List<Clothes> clothesList, OnItemClickListener onItemClickListener, Fragment fragment) {
        this.clothesList = clothesList;
        this.onItemClickListener = onItemClickListener;
        this.fragment = (FragmentMain) fragment;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);

        return new ViewHolder(view, fragment, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Clothes clothes = clothesList.get(position);
        holder.setOnItemClickListener(onItemClickListener, position, clothes);
        holder.textViewDescription.setText(clothes.getDESCRIPTION());
        Utils.setPicasso(fragment.getActivity(),clothes.getURL_PHOTO(), R.mipmap.ic_imge_clothes,holder.imageViewClothes);
    }

    @Override
    public int getItemCount() {
        return clothesList.size();
    }

    public void removeClothes(Clothes clothes) {
        clothesList.remove(clothes);
        notifyDataSetChanged();
    }

    public void setClothes(List<Clothes> clothes) {
        this.clothesList = clothes;
        notifyDataSetChanged();
    }
    public void updateAdapter(Clothes clothes) {
        for (int i = 0; i < this.clothesList.size(); i++) {
            if (this.clothesList.get(i).getID_CLOTHES_KEY() == clothes.getID_CLOTHES_KEY())
                this.clothesList.get(i).setISACTIVE(clothes.getISACTIVE());
            break;
        }
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.imageViewClothes)
        ImageView imageViewClothes;
        @Bind(R.id.textViewDescription)
        TextView textViewDescription;
        @Bind(R.id.linearConteiner)
        LinearLayout linearConteiner;

        public ViewHolder(View view, Fragment fragment, OnItemClickListener onItemClickListener) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void setOnItemClickListener(final OnItemClickListener listener, final int position, final Clothes clothes) {

            linearConteiner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(v, position);
                }
            });
//            linearConteiner.setOnLongClickListener(new View.OnLongClickListener() {
//                @Override
//                public boolean onLongClick(View v) {
//                    listener.onLongClick(v, position);
//                    return true;
//                }
//            });
        }
    }
}
