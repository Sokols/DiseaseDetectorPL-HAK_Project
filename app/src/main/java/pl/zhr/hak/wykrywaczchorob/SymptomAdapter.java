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

import static pl.zhr.hak.wykrywaczchorob.LoginActivity.sharedPreferencesName;

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

    SharedPreferences sharedPreferences;
    private static List<Symptom> mSymptomList;
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
        // ustaw na starcie licznik zaznaczonych symptomów na 0
        sharedPreferences.edit().putInt("symptomCounter", 0).apply();

        holder.textViewSymptomName.setText(mSymptomList.get(position).getSymptomName());
        holder.checkBoxCheckSymptom.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // jeśli symptom został zaznaczony zmień jego flagę isChecked na true i inkrementuj licznik zaznaocznych symptomów
            if (isChecked) {
                mSymptomList.get(position).setChecked(true);
                sharedPreferences.edit().putInt("symptomCounter", sharedPreferences.getInt("symptomCounter", 0) + 1).apply();
            }
            // jeśli symptom został odznaczony zmień jego flagę w isChecked na false i dekrementuj licznik zaznaczonych symptomów
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

    public static List<Symptom> getChecked() { return mSymptomList; }
}