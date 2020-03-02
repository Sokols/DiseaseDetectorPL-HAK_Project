package pl.zhr.hak.wykrywaczchorob;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PatientAdapter extends RecyclerView.Adapter<PatientAdapter.PatientViewHolder> {
    class PatientViewHolder extends RecyclerView.ViewHolder {

        private final TextView textViewName;
        private final TextView textViewSurname;
        private final TextView textViewDisease;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);
            this.textViewName = itemView.findViewById(R.id.textViewName);
            this.textViewSurname = itemView.findViewById(R.id.textViewSurname);
            this.textViewDisease = itemView.findViewById(R.id.textViewDisease);
        }
    }

    private List<Patient> mPatientList;
    private final LayoutInflater mInflater;
    private Context mcontext;
    public PatientAdapter(List<Patient> patientList, Context context) {
        this.mPatientList = patientList;
        this.mcontext = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.listitem_patient, parent, false);
        return new PatientViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        String [] diseaseList = new String [6];
        setDiseases(diseaseList);
        holder.textViewName.setText(mPatientList.get(position).getName());
        holder.textViewSurname.setText(mPatientList.get(position).getSurname());
        holder.textViewDisease.setText(diseaseList[mPatientList.get(position).getDiseaseID()]);
    }

    @Override
    public int getItemCount() { return mPatientList.size(); }

    void setPatients(List<Patient> patients) {
        mPatientList = patients;
        notifyDataSetChanged();
    }

    public void setDiseases(String [] diseases) {
        diseases[1] = mcontext.getString(R.string.coronavirus);
        diseases[2] = mcontext.getString(R.string.food_poisoning);
        diseases[3] = mcontext.getString(R.string.flu);
        diseases[4] = mcontext.getString(R.string.angina);
        diseases[5] = mcontext.getString(R.string.hypochondria);
    }
}
