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

import static pl.zhr.hak.wykrywaczchorob.MainActivity.sharedPreferencesName;

public class SymptomAdapter extends RecyclerView.Adapter<SymptomAdapter.SymptomViewHolder> {
    class SymptomViewHolder extends RecyclerView.ViewHolder {

        private final CheckBox checkBoxCheckSymptom;
        private final TextView textViewSymptomName;

        public SymptomViewHolder(@NonNull View itemView) {
            super(itemView);
            this.checkBoxCheckSymptom = itemView.findViewById(R.id.checkBoxCheckSymptom);
            this.textViewSymptomName = itemView.findViewById(R.id.textViewSymptomName);

            checkBoxCheckSymptom.setOnCheckedChangeListener((buttonView, isChecked) -> {
                // jeśli symptom został zaznaczony zmień jego flagę w shared preferences na true i inkrementuj licznik zaznaocznych symptomów
                if (isChecked) {
                    String key = textViewSymptomName.getText().toString();
                    sharedPreferences.edit().putBoolean(key,
                            true).apply();
                    sharedPreferences.edit().putInt("symptomCounter", sharedPreferences.getInt("symptomCounter", 0) + 1).apply();
                }
                // jeśli symptom został odznaczony zmień jego flagę w shared preferences na false i dekrementuj licznik zaznaczonych symptomów
                else {
                    String key = textViewSymptomName.getText().toString();
                    sharedPreferences.edit().putBoolean(key,
                            false).apply();
                    if (sharedPreferences.getInt("symptomCounter", 0) > 0) {
                        sharedPreferences.edit().putInt("symptomCounter", sharedPreferences.getInt("symptomCounter", 0) - 1).apply();
                    }
                }
            });
        }
    }

    SharedPreferences sharedPreferences;
    private List<Symptom> mSymptomList;
    private final LayoutInflater mInflater;
    private Context mContext;
    public SymptomAdapter(List<Symptom> symptomList, Context context) {
        this.mSymptomList = symptomList;
        this.mContext = context;
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
        holder.textViewSymptomName.setText(mSymptomList.get(position).getSymptomName());

        // ustaw na starcie wszystkie symptomy na false w shared preferences
        sharedPreferences.edit().putBoolean(mSymptomList.get(position).getSymptomName(), false).apply();

        // ustaw na starcie licznik zaznaczonych symptomów na 0
        sharedPreferences.edit().putInt("symptomCounter", 0).apply();
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