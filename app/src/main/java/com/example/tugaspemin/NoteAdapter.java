package com.example.tugaspemin;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.session.MediaSession;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import java.net.CookieManager;
import java.util.List;

import api.ApiConfig;
import api.ApiService;
import model.AddMahasiswaResponse;
import model.Note;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder> {
    private Context ctx;
    private List<Note> noteList;
    private Integer id;
    private String token ;



    public NoteAdapter(Context ctx, List<Note> noteList) {
        this.ctx = ctx;
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note dm = noteList.get(position);
        holder.tvId.setText(String.valueOf(dm.getId()));
        holder.tvTitle.setText(noteList.get(position).getJudul());
    }

    @Override
    public int getItemCount() {
        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;
        TextView tvBody;
        TextView tvId;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvNotesTitle);
            tvId = itemView.findViewById(R.id.tvNotesId);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    AlertDialog.Builder dialogPesan = new AlertDialog.Builder(ctx);
                    dialogPesan.setMessage("Pilih Menu : ");
                    dialogPesan.setCancelable(true);

                    id = Integer.parseInt(tvId.getText().toString());

                    dialogPesan.setPositiveButton("Hapus", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            deleteData();
                            dialogInterface.dismiss();
                        }
                    });
                    dialogPesan.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    dialogPesan.show();

                    return false;
                }
            });
        }
        private void deleteData(){
            Call<AddMahasiswaResponse> client = ApiConfig.getApiService().delete(token);
            client.enqueue(new Callback<AddMahasiswaResponse>() {
                @Override
                public void onResponse(Call<AddMahasiswaResponse> call, Response<AddMahasiswaResponse> response) {
                    String token = response.body().getToken();
                    String message = response.body().getMessage();
                    Toast.makeText(ctx, "Berhasil Menghapus Note", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(Call<AddMahasiswaResponse> call, Throwable t) {
                    Toast.makeText(ctx, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

}
