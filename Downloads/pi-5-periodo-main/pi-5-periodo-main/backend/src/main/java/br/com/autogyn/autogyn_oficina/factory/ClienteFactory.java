package br.com.autogyn.autogyn_oficina.factory;

import br.com.autogyn.autogyn_oficina.entity.Cliente;
import br.com.autogyn.autogyn_oficina.entity.ClientePF;
import br.com.autogyn.autogyn_oficina.entity.ClientePJ;

public class ClienteFactory {

    public enum TipoCliente {
        PESSOA_FISICA,
        PESSOA_JURIDICA
    }

    // Dependendo do tipo ira passar se o documento sera o cpf ou cnpj
    // testamdp
    public static Cliente criarCliente(TipoCliente tipo, String nome, String endereco, String telefone,
            String documento) {
        switch (tipo) {
            case PESSOA_FISICA:
                return new ClientePF(nome, endereco, telefone, documento);
            case PESSOA_JURIDICA:
                return new ClientePJ(nome, endereco, telefone, documento);
            default:
                throw new IllegalArgumentException("Tipo de cliente inválido");
        }
    }
}
