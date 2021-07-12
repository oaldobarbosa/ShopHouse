  package com.example.shophouse;

public class Imovel {
    private String cidade;
    private String descricao;
    private String email;
    private String endereco;
    private String estado;
    private String id_user;
    private String telefone;
    private String titulo;
    private String img;

    private Imovel(){}

    private Imovel(String cidade, String descricao, String email, String endereco, String estado, String id_user,
                   String telefone, String titulo, String img){

        this.cidade = cidade;
        this.descricao = descricao;
        this.email = email;
        this.endereco = endereco;
        this.estado = estado;
        this.id_user = id_user;
        this.telefone = telefone;
        this.titulo = titulo;
        this.img = img;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String url) {
        this.img = url;
    }

    public void SalvarDadosImovel(){

    }
}
