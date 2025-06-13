package br.com.autogyn.autogyn_oficina.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autogyn.autogyn_oficina.entity.Carro;
import br.com.autogyn.autogyn_oficina.entity.Cliente;
import br.com.autogyn.autogyn_oficina.entity.Moto;
import br.com.autogyn.autogyn_oficina.entity.Veiculo;
import br.com.autogyn.autogyn_oficina.enums.TipoVeiculos;
import br.com.autogyn.autogyn_oficina.factory.VeiculoFactory;
import br.com.autogyn.autogyn_oficina.repository.CarroRepository;
import br.com.autogyn.autogyn_oficina.repository.ClienteRepository;
import br.com.autogyn.autogyn_oficina.repository.MotoRepository;
import br.com.autogyn.autogyn_oficina.repository.VeiculoRepository;

@Service
public class VeiculoService {

    @Autowired
    private VeiculoRepository veiculoRepository;

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Optional<Veiculo> buscarPorID(Long id) {
        return veiculoRepository.findById(id);
    }

    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }

    public void deletarVeiculo(Long id) {
        veiculoRepository.deleteById(id);
    }

    // Agora so da para criar o veiculo passando o id do cliente para referenciar
    // que o veiculo e daquele cliente
    public Veiculo criarVeiculo(TipoVeiculos tipo, String placa, String modelo, String ano, String cor,
            Long clienteId) {

        // Cria o veículo pelo factory
        Veiculo veiculo = VeiculoFactory.criarVeiculo(tipo, placa, modelo, ano, cor);

        // Setar o tipo no veículo
        veiculo.setTipo(tipo);

        // Busca o cliente, lança exceção se não encontrar
        Cliente cliente = clienteRepository.findById(clienteId)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com id: " + clienteId));

        // Associa cliente ao veículo
        veiculo.setCliente(cliente);

        // Salva no repositório correto, conforme o tipo do veículo
        if (veiculo instanceof Carro) {
            return carroRepository.save((Carro) veiculo);
        } else if (veiculo instanceof Moto) {
            return motoRepository.save((Moto) veiculo);
        } else {
            return veiculoRepository.save(veiculo);
        }
    }

    public Veiculo atualizarVeiculo(Long id, String placa, String modelo, String ano, String cor) {
        Optional<Veiculo> veiculoOptional = veiculoRepository.findById(id);
        if (veiculoOptional.isEmpty()) {
            throw new IllegalArgumentException("Veiculo com ID " + id + " não encontrado.");
        }
        Veiculo veiculo = veiculoOptional.get();

        veiculo.setPlaca(placa);
        veiculo.setModelo(modelo);
        veiculo.setAno(ano);
        veiculo.setCor(cor);
        if (veiculo instanceof Carro) {
            return carroRepository.save((Carro) veiculo);
        } else if (veiculo instanceof Moto) {
            return motoRepository.save((Moto) veiculo);
        } else {
            return veiculoRepository.save(veiculo);
        }
    }

}
