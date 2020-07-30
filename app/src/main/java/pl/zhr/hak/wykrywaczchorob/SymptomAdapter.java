package pl.zhr.hak.wykrywaczchorob;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static pl.zhr.hak.wykrywaczchorob.activities.LoginActivity.sharedPreferencesName;

public class SymptomAdapter extends RecyclerView.Adapter<SymptomAdapter.SymptomViewHolder> {
    static class SymptomViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.checkBoxCheckSymptom)
        CheckBox checkBoxCheckSymptom;
        @BindView(R.id.textViewSymptomName)
        TextView textViewSymptomName;

        SymptomViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private final LayoutInflater mInflater;
    private static List<Symptom> mSymptomList;
    private SharedPreferences sharedPreferences;

    public SymptomAdapter(List<Symptom> symptomList, Context context) {
        mSymptomList = symptomList;
        mInflater = LayoutInflater.from(context);
        sharedPreferences = context.getSharedPreferences(sharedPreferencesName, 0);
    }

    @NonNull
    @Override
    public SymptomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.listitem_symptom, parent, false);
        return new SymptomViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomViewHolder holder, int position) {
        // set the count of the selected symptoms to 0 at the start
        sharedPreferences.edit().putInt("symptomFlag", 0).apply();
        holder.textViewSymptomName.setText(mSymptomList.get(position).getSymptomName());
        holder.checkBoxCheckSymptom.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // if a symptom was selected, change its isChecked flag to true and increment the counter of known symptoms
            if (isChecked) {
                mSymptomList.get(position).setChecked(true);
                sharedPreferences.edit().putInt("symptomCounter", sharedPreferences.getInt("symptomCounter", 0) + 1).apply();
            }
            // if a symptom was unchecked, change its flag in isChecked to false and decrement the count of selected symptoms
            else {
                mSymptomList.get(position).setChecked(false);
                if (sharedPreferences.getInt("symptomCounter", 0) > 0) {
                    sharedPreferences.edit().putInt("symptomCounter", sharedPreferences.getInt("symptomCounter", 0) - 1).apply();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mSymptomList.size();
    }

    public static List<Symptom> getChecked() {
        return mSymptomList;
    }

    public void uncheckAll() {
        for (Symptom symptom : mSymptomList) {
            if (symptom.getChecked()) {
                symptom.setChecked(false);
            }
        }
    }
}