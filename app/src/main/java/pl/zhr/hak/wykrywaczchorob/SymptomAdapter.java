package pl.zhr.hak.wykrywaczchorob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SymptomAdapter extends RecyclerView.Adapter<SymptomAdapter.SymptomViewHolder> {
    class SymptomViewHolder extends RecyclerView.ViewHolder {
        private final CheckBox checkBoxCheckSymptom;
        private final TextView textViewSymptomName;
        public SymptomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.checkBoxCheckSymptom = itemView.findViewById(R.id.checkBoxCheckSymptom);
            this.textViewSymptomName = itemView.findViewById(R.id.textViewSymptomName);
        }
    }

    private List<Symptom> mSymptomList;
    private final LayoutInflater mInflater;
    private Context mContext;
    public SymptomAdapter(List<Symptom> symptomList, Context context) {
        this.mSymptomList = symptomList;
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public SymptomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.listitem_symptom, parent, false);
        return new SymptomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomViewHolder holder, int position) {
        holder.textViewSymptomName.setText(mSymptomList.get(position).getSymptomName());
        holder.checkBoxCheckSymptom.setChecked(holder.checkBoxCheckSymptom.isChecked());

        holder.checkBoxCheckSymptom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

            }
        });
    }

    @Override
    public int getItemCount() {
        return mSymptomList.size();
    }

    void setSymptoms(List<Symptom> symptoms) {
        mSymptomList = symptoms;
        notifyDataSetChanged();
    }
}
