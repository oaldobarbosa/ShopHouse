/*package com.example.shophouse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ImovelAdapter extends RecyclerView.Adapter<ImovelAdapter.ViewHolderClass> {



    //

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
    public ViewHolderClass onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {



        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.imovel_item, parent, false);
        return new ImovelViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolderClass holder, int position) {

        //

        holder.titulo.setText(data1[position]);
        holder.cidade.setText(data2[position]);
        holder.imagem.setImageResource(images[position]);

        //clicar no item
        holder.cardLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ViewImovel.class);
                intent.putExtra("data1", data1[position]);
                intent.putExtra("data2", data2[position]);
                intent.putExtra("imagem", images[position]);
                context.startActivity(intent);

            }
        });



    }

    @Override
    public int getItemCount() {
        //testando listar do banco
        return images.length;
    }

    public class ViewHolderClass extends RecyclerView.ViewHolder{

        TextView titulo, cidade, estado;

        //id item card
        ConstraintLayout cardLayout;


        public ViewHolderClass(@NonNull @NotNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.textViewTituloImovel);
            cidade = itemView.findViewById(R.id.textViewCidade);
            estado = itemView.findViewById(R.id.textViewEstado);
            //imagem = itemView.findViewById(R.id.imagemImovel);
            cardLayout = itemView.findViewById(R.id.cardLayout);

        }
    }

    public class ImovelViewHolder extends RecyclerView.ViewHolder{

        TextView titulo, cidade, estado;
        ImageView imagem;

        //id item card
        ConstraintLayout cardLayout;

        public ImovelViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.textViewTituloImovel);
            cidade = itemView.findViewById(R.id.textViewCidade);
            estado = itemView.findViewById(R.id.textViewEstado);
            imagem = itemView.findViewById(R.id.imagemImovel);
            cardLayout = itemView.findViewById(R.id.cardLayout);

        }
    }
}
*/