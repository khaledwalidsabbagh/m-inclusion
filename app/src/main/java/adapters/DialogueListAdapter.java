package adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.minclusion.iteration1.R;

import java.util.List;

import db.Dialogue;
import interfaces.ListItemClickListener;

public class DialogueListAdapter extends RecyclerView.Adapter<DialogueListAdapter.DialogueViewHolder> implements ListItemClickListener{
    private Context ctx;
    private List<Dialogue> dialogues;
    private ListItemClickListener listener;

    public DialogueListAdapter(Context context, List<Dialogue> dialogues, ListItemClickListener listener) {
        this.ctx = context;
        this.dialogues = dialogues;
        this.listener = listener;
    }
    @Override
    public DialogueViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.mydialoguelist, parent, false);
        return new DialogueViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DialogueViewHolder holder, int position) {

        holder.titleTxt.setText(dialogues.get(position).getTitleAr());
        holder.descTxt.setText(dialogues.get(position).getDescriptionAr());
        holder.dialogueImg.setImageResource(ctx.getResources().getIdentifier(dialogues.get(position).getImagePath(), "raw", ctx.getPackageName()));
    }

    @Override
    public int getItemCount() {
        return dialogues.size();
    }

    @Override
    public void onListItemClick(int clickedItemIndex) {

    }

    class DialogueViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private DialogueViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        TextView titleTxt = (TextView) itemView.findViewById(R.id.dialogueTitle);
        ImageView dialogueImg = (ImageView) itemView.findViewById(R.id.dialogueImg);
        TextView descTxt = (TextView) itemView.findViewById(R.id.dialogueDesc);

        @Override
        public void onClick(View view) {
            listener.onListItemClick(getPosition());
        }
    }

}


