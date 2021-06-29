package com.example.shophouse;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class ImovelPropAdapter extends RecyclerView.Adapter<ImovelPropAdapter.ImovelPropViewHolder> {

    String data1[], data2[];
    int images[];
    Context context;

    public ImovelPropAdapter(Context ct, String s1[], String s2[], int img[]){
        context = ct;
        data1 = s1;
        data2 = s2;
        images = img;


    }

    @NonNull
    @NotNull
    @Override
    public ImovelPropViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.imovel_item, parent, false);
        return new ImovelPropViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ImovelPropViewHolder holder, int position) {
        holder.titulo.setText(data1[position]);
        holder.cidade.setText(data2[position]);
        holder.imagem.setImageResource(images[position]);

        //clicar no item
        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewImovelProp.class);
                intent.putExtra("data1", data1[position]);
                intent.putExtra("data2", data2[position]);
                intent.putExtra("imagem", images[position]);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return images.length;
    }

    public class ImovelPropViewHolder extends RecyclerView.ViewHolder{

        TextView titulo, cidade, estado;
        ImageView imagem;

        //id item card
        ConstraintLayout cardLayout;

        public ImovelPropViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.textViewTituloImovel);
            cidade = itemView.findViewById(R.id.textViewCidade);
            estado = itemView.findViewById(R.id.textViewEstado);
            imagem = itemView.findViewById(R.id.imagemImovel);
            cardLayout = itemView.findViewById(R.id.cardLayout);

        }
    }
}