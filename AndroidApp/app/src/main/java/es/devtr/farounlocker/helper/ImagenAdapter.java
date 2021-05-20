package es.devtr.farounlocker.helper;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.browser.customtabs.CustomTabsIntent;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import es.devtr.farounlocker.R;

public class ImagenAdapter extends RecyclerView.Adapter<ImagenAdapter.ViewHolder> {

    private List<Imagen> list;
    private Context context;


    public class ViewHolder extends RecyclerView.ViewHolder {

        // Text View
        TextView textView;
        ImageView imageView;


        public ViewHolder(View view)
        {
            super(view);

            // initialise TextView with id
            textView = (TextView)view.findViewById(R.id.title);
            imageView = view.findViewById(R.id.imagen);
        }
    }


    public ImagenAdapter(List<Imagen> horizontalList, Context context)
    {
        this.list = horizontalList;

        if(list==null){
            list = new ArrayList<>();
        }

        this.context = context;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType)
    {

        View itemView
                = LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.imagen,
                        parent,
                        false);

        return new ViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position){

        final Imagen imagen = list.get(position);


        holder.textView.setText(imagen.getTitle());
        Glide.with(context)
                .load(imagen.getUrl())
                .into(holder.imageView);

        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
                CustomTabsIntent customTabsIntent = builder.build();
                customTabsIntent.launchUrl(context, Uri.parse(imagen.getUrl()));
            }
        });

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
