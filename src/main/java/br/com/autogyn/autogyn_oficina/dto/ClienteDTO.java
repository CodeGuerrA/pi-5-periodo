package br.com.autogyn.autogyn_oficina.dto;

//Criei o DTO para em vez de passar no controller todos os parametros passa do CLienteDTO com os parametros que quero que ja vai criar os Clientes
//usei para nao expor dados sensiveis e somente aqueles que quero boa pratica
public class ClienteDTO {
    private String nome;
    private String endereco;
    private String telefone;
    private String documento;

    // Getters e setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }
}
