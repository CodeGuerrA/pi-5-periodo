package br.com.autogyn.autogyn_oficina.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autogyn.autogyn_oficina.entity.Peca;
import br.com.autogyn.autogyn_oficina.repository.PecasRepository;

@Service
public class PecasService {

    @Autowired
    private PecasRepository pecasRepository;

    public List<Peca> listarTodas() {
        return pecasRepository.findAll();
    }

    public Optional<Peca> buscarPorId(long id) {
        return pecasRepository.findById(id);
    }

    public Peca criarPeca(String codigo, String descricao, BigDecimal precoUnitario, Integer quantidadeEstoque) {
        Peca peca = new Peca();

        peca.setCodigo(codigo);
        peca.setDescricao(descricao);
        peca.setPrecoUnitario(precoUnitario);
        peca.setQuantidadeEstoque(quantidadeEstoque);
        return pecasRepository.save(peca);
    }

    public Peca atualizarPeca(long id, String codigo, String descricao, BigDecimal precoUnitario,
            Integer quantidadeEstoque) {
        Optional<Peca> pecaOptional = pecasRepository.findById(id);
        if (pecaOptional.isEmpty()) {
            throw new IllegalArgumentException("Peça com ID " + id + " não encontrado.");
        }

        Peca peca = pecaOptional.get();
        peca.setCodigo(codigo);
        peca.setDescricao(descricao);
        peca.setPrecoUnitario(precoUnitario);
        peca.setQuantidadeEstoque(quantidadeEstoque);
        return pecasRepository.save(peca);
    }

    public void deletarPeca(Long id) {
        pecasRepository.deleteById(id);
    }

    // Metodo para quando escolheremos a quantidade que queremos usar no itemPecaOS
    // seja reduzido do estoque principal que e Peca
    public void reduzirEstoque(Long pecaId, Integer quantidade) {
        Peca peca = pecasRepository.findById(pecaId)
                .orElseThrow(() -> new IllegalArgumentException("Peça com ID " + pecaId + " não encontrada."));

        if (peca.getQuantidadeEstoque() < quantidade) {
            throw new IllegalArgumentException("Estoque insuficiente para a peça com ID " + pecaId + ".");
        }

        peca.setQuantidadeEstoque(peca.getQuantidadeEstoque() - quantidade);
        pecasRepository.save(peca);
    }
}
