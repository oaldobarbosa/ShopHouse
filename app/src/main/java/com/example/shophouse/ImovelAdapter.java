package com.example.shophouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class ImovelAdapter extends RecyclerView.Adapter<ImovelAdapter.ImovelViewHolder> {

    String data1[], data2[];
    int images[];
    Context context;

    public ImovelAdapter(Context ct, String s1[], String s2[], int img[]){
        context = ct;
        data1 = s1;
        data2 = s2;
        images = img;


    }

    @NonNull
    @NotNull
    @Override
    public ImovelViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.imovel_item, parent, false);
        return new ImovelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ImovelViewHolder holder, int position) {
        holder.titulo.setText(data1[position]);
        holder.cidade.setText(data2[position]);
        holder.imagem.setImageResource(images[position]);

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class ImovelViewHolder extends RecyclerView.ViewHolder{

        TextView titulo, cidade, estado;
        ImageView imagem;

        public ImovelViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.textViewTituloImovel);
            cidade = itemView.findViewById(R.id.textViewCidade);
            estado = itemView.findViewById(R.id.textViewEstado);
            imagem = itemView.findViewById(R.id.imagemImovel);

        }
    }
}
