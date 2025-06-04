package br.com.autogyn.autogyn_oficina.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.autogyn.autogyn_oficina.factory.ClienteFactory;
import br.com.autogyn.autogyn_oficina.model.Cliente;
import br.com.autogyn.autogyn_oficina.model.ClientePF;
import br.com.autogyn.autogyn_oficina.model.ClientePJ;
import br.com.autogyn.autogyn_oficina.repository.ClientePFRepository;
import br.com.autogyn.autogyn_oficina.repository.ClientePJRepository;
import br.com.autogyn.autogyn_oficina.repository.ClienteRepository;

@Service
public class ClienteService {

    private final ClientePFRepository clientePFRepository;
    private final ClientePJRepository clientePJRepository;
    private final ClienteRepository clienteRepository;

    // Construtor dos repository
    public ClienteService(ClientePFRepository clientePFRepository, ClientePJRepository clientePJRepository,
            ClienteRepository clienteRepository) {
        this.clientePFRepository = clientePFRepository;
        this.clientePJRepository = clientePJRepository;
        this.clienteRepository = clienteRepository;
    }

    // Buscando cliente por id
    public Optional<Cliente> buscarPorId(Long id) {
        return clienteRepository.findById(id);
    }

    // Lista todos (PF + PJ)
    public List<Cliente> listarTodos() {
        return clienteRepository.findAll();
    }

    // Metodo para deletar
    public void deletarCliente(Long id) {
        clienteRepository.deleteById(id);
    }

    // metodo para criar um usuario
    public Cliente criarCliente(String nome, String endereco, String telefone, String documento) {
        // Aqui quando passa o documento eu limpo ele com regex para poder fazer a
        // contagem dos caracters para verficar se e cnpj ou cpf
        ClienteFactory.TipoCliente tipo; //

        if (documento.length() == 14) {
            tipo = ClienteFactory.TipoCliente.PESSOA_FISICA;
        } else if (documento.length() == 18) {
            tipo = ClienteFactory.TipoCliente.PESSOA_JURIDICA;
        } else {
            throw new IllegalArgumentException("Documento inválido: deve ser CPF (11 dígitos) ou CNPJ (14 dígitos).");
        }

        Cliente cliente = ClienteFactory.criarCliente(tipo, nome, endereco, telefone, documento);
        // cliente instanceof ClientePF verifica se o objeto referenciado por cliente é
        // uma instância da classe ClientePF ou de alguma subclasse dela. Se for true
        // ele cria o formulario para ClientePF senao vai para PJ
        // ============================================================================
        // Aqui ele verifica o tipo do cliente dependendo do tipo ele cria o formulario
        // do Cliente.
        if (tipo == ClienteFactory.TipoCliente.PESSOA_FISICA && cliente instanceof ClientePF) {
            return clientePFRepository.save((ClientePF) cliente); // ele passa o cliente aqui para poder passar os dados
                                                                  // corretos com base no factory
        } else if (tipo == ClienteFactory.TipoCliente.PESSOA_JURIDICA && cliente instanceof ClientePJ) {
            return clientePJRepository.save((ClientePJ) cliente);
        } else {
            throw new IllegalArgumentException("Tipo de cliente não suportado ou instância incorreta.");
        }
    }

    // Atualizar Cliente
    public Cliente atualizarCliente(Long id, String nome, String endereco, String telefone, String documento) {
        // Primeiro, buscar o cliente pelo id
        Optional<Cliente> clienteOptional = clienteRepository.findById(id);
        if (clienteOptional.isEmpty()) {
            throw new IllegalArgumentException("Cliente com ID " + id + " não encontrado.");
        }

        Cliente cliente = clienteOptional.get();

        // Verificar o tipo do documento enviado para evitar inconsistência
        ClienteFactory.TipoCliente tipo;
        if (documento.length() == 14) {
            tipo = ClienteFactory.TipoCliente.PESSOA_FISICA;
        } else if (documento.length() == 18) {
            tipo = ClienteFactory.TipoCliente.PESSOA_JURIDICA;
        } else {
            throw new IllegalArgumentException("Documento inválido: deve ser CPF (11 dígitos) ou CNPJ (14 dígitos).");
        }

        // Verificar se o tipo do cliente existente bate com o tipo do documento
        // informado
        if ((cliente instanceof ClientePF && tipo != ClienteFactory.TipoCliente.PESSOA_FISICA) ||
                (cliente instanceof ClientePJ && tipo != ClienteFactory.TipoCliente.PESSOA_JURIDICA)) {
            throw new IllegalArgumentException("Tipo do cliente e documento não coincidem.");
        }

        // Atualizar os dados do cliente
        cliente.setNome(nome);
        cliente.setEndereco(endereco);
        cliente.setTelefone(telefone);

        if (cliente instanceof ClientePF) {
            ((ClientePF) cliente).setCpf(documento);
            return clientePFRepository.save((ClientePF) cliente);
        } else if (cliente instanceof ClientePJ) {
            ((ClientePJ) cliente).setCnpj(documento);
            return clientePJRepository.save((ClientePJ) cliente);
        } else {
            throw new IllegalArgumentException("Tipo de cliente não suportado para atualização.");
        }
    }
}
