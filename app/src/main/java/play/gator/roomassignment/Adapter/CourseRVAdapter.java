package play.gator.roomassignment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import play.gator.roomassignment.Model.CourseModal;
import play.gator.roomassignment.R;

public class CourseRVAdapter extends ListAdapter<CourseModal, CourseRVAdapter.ViewHolder> {

    // creating a variable for on item click listener.
    private OnItemClickListener listener;
    Context context;

    // creating a constructor class for our adapter class.
    public CourseRVAdapter() {
        super(DIFF_CALLBACK);
    }

    // creating a call back for item of recycler view.
    private static final DiffUtil.ItemCallback<CourseModal> DIFF_CALLBACK = new DiffUtil.ItemCallback<CourseModal>() {
        @Override
        public boolean areItemsTheSame(CourseModal oldItem, CourseModal newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(CourseModal oldItem, CourseModal newItem) {

            return oldItem.getName().equals(newItem.getName()) &&
                    oldItem.getCapital().equals(newItem.getCapital()) &&
                    oldItem.getRegion().equals(newItem.getRegion())&&
                    oldItem.getFlag().equals(newItem.getFlag())&&
                    oldItem.getSubregion().equals(newItem.getSubregion())
                    &&oldItem.getPopulation().equals(newItem.getPopulation())
                    &&oldItem.getBorder().equals(newItem.getBorder())&&
                    oldItem.getLanguage().equals(newItem.getLanguage());
        }
    };

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.course_rv_item, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        CourseModal model = getCourseAt(position);
        holder.name.setText(model.getName());
        holder.capital.setText(model.getCapital());
        holder.region.setText(model.getRegion());
        context = holder.flag.getContext();
        Picasso.with(context)
                .load(model.getFlag())
                .into(holder.flag);
        holder.subregion.setText(model.getSubregion());
        holder.population.setText(model.getPopulation());
        holder.border.setText(model.getBorder());
        holder.language.setText(model.getLanguage());



    }


    public CourseModal getCourseAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, capital, region,subregion,population,border,language;

        ImageView flag;
        ViewHolder(@NonNull View itemView) {
            super(itemView);
            // initializing each view of our recycler view.
            name = itemView.findViewById(R.id.name);
            capital = itemView.findViewById(R.id.capital);
            flag = itemView.findViewById(R.id.flag);
            region = itemView.findViewById(R.id.region);
            subregion = itemView.findViewById(R.id.subregion);
            population = itemView.findViewById(R.id.population);
            border = itemView.findViewById(R.id.border);
            language = itemView.findViewById(R.id.language);


        }
    }

    public interface OnItemClickListener {
        void onItemClick(CourseModal model);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
