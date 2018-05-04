package adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bumptech.glide.Glide;
import com.minclusion.iteration1.R;

import entities.cookandlearn.Dish;
import interfaces.ListItemClickListener;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.MenuViewHolder> {

    TextView itemName, itemPrice;
    ImageView itemPicture;
    ListItemClickListener listener;

    List<Dish> dishes;

    Context context;

    public DishAdapter(Context context, ListItemClickListener itemClickListener,
                       List<Dish> dishes) {
        this.listener = itemClickListener;
        this.dishes = dishes;

        //set the application context
        this.context = context;
    }

    @Override
    public MenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());

        // inflate a special view for this dish
        View view = inflater.inflate(R.layout.dish_list_item, parent, false);

        //get a reference to elements shown in
        itemName =  view.findViewById(R.id.dish_item_name);
        itemPrice = view.findViewById(R.id.dish_item_cost);
        itemPicture = view.findViewById(R.id.menu_item_picture);

        //return the item
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuViewHolder holder, int position) {
        itemName.setText(dishes.get(position).getName());
        itemPrice.setText("");

        // construct a resouurce id
        int resID = context.getResources().getIdentifier(dishes.get(position).getImage(),
                "raw", context.getPackageName());
        // load image with Glide library
        Glide.with(context)
                .load(resID)
                .into(itemPicture);
    }

    @Override
    public int getItemCount() {
        return dishes.size();
    }

    public int getCategoryCount() {
        // count the number of elements in different categories
        Map<String, Integer> map = new HashMap<String, Integer>();
        for(Dish d: dishes){
            if(map.keySet().contains(d))
            {
                map.put(d.getName(), map.get(d.getName()) + 1);

            }else
            {
                map.put(d.getName(), 1);
            }
        }
        return map.size();
    }

    public List<Dish> getDishes() {
        return dishes;
    }

    public class MenuViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public MenuViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            //Log.d("List item clicked", clickedPosition + "");
            //Toast.makeText(context, "Menu selected showsing ingredients.", Toast.LENGTH_SHORT).show();
            listener.onListItemClick(clickedPosition);
        }

    }

}
