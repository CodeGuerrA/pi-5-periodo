package br.com.autogyn.autogyn_oficina.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import br.com.autogyn.autogyn_oficina.factory.VeiculoFactory;
import br.com.autogyn.autogyn_oficina.model.Carro;
import br.com.autogyn.autogyn_oficina.model.Moto;
import br.com.autogyn.autogyn_oficina.model.Veiculo;
import br.com.autogyn.autogyn_oficina.repository.CarroRepository;
import br.com.autogyn.autogyn_oficina.repository.MotoRepository;
import br.com.autogyn.autogyn_oficina.repository.VeiculoRepository;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final MotoRepository motoRepository;
    private final CarroRepository carroRepository;

    public VeiculoService(VeiculoRepository veiculoRepository, MotoRepository motoRepository,
            CarroRepository carroRepository) {
        this.veiculoRepository = veiculoRepository;
        this.motoRepository = motoRepository;
        this.carroRepository = carroRepository;
    }

    public Optional<Veiculo> buscarPorID(Long id) {
        return veiculoRepository.findById(id);
    }

    public List<Veiculo> listarTodos() {
        return veiculoRepository.findAll();
    }

    public void deletarVeiculo(Long id) {
        veiculoRepository.deleteById(id);
    }

    public Veiculo criarVeiculo(VeiculoFactory.TipoVeiculos tipo, String placa, String modelo, String ano, String cor) {
        Veiculo veiculo = VeiculoFactory.criarVeiculo(tipo, placa, modelo, ano, cor);

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
