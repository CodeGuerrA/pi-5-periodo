package br.com.autogyn.autogyn_oficina.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.autogyn.autogyn_oficina.entity.ItemPecaOS;
import br.com.autogyn.autogyn_oficina.entity.OrdemServico;
import br.com.autogyn.autogyn_oficina.entity.Peca;
import br.com.autogyn.autogyn_oficina.repository.ItemPecaOSRepository;
import br.com.autogyn.autogyn_oficina.repository.OrdemServicoRepository;
import br.com.autogyn.autogyn_oficina.repository.PecasRepository;

@Service
public class ItemPecaOsService {

    @Autowired
    private ItemPecaOSRepository itemPecaOSRepository;
    @Autowired
    private PecasService pecasService;
    @Autowired
    private PecasRepository pecaRepository;
    @Autowired
    private OrdemServicoRepository ordemServicoRepository;

    public List<ItemPecaOS> listarTodos() {
        return itemPecaOSRepository.findAll();
    }

    public Optional<ItemPecaOS> buscarPorId(Long id) {
        return itemPecaOSRepository.findById(id);
    }

    public void deletarItem(Long id) {
        itemPecaOSRepository.deleteById(id);
    }

    public ItemPecaOS criarItem(Long pecaId, Integer quantidade) {
        Optional<Peca> pecaOptional = pecaRepository.findById(pecaId);
        if (pecaOptional.isEmpty()) {
            throw new IllegalArgumentException("Peça com ID " + pecaId + " não encontrada.");
        }

        Peca peca = pecaOptional.get();

        // Reduz o estoque da peça
        pecasService.reduzirEstoque(pecaId, quantidade);

        // Calcula o preço final
        BigDecimal precoFinal = peca.getPrecoUnitario().multiply(BigDecimal.valueOf(quantidade));

        // Cria o item da peça da OS
        ItemPecaOS item = new ItemPecaOS();
        item.setPeca(peca);
        item.setQuantidade(quantidade);
        item.setPrecoFinal(precoFinal);

        return itemPecaOSRepository.save(item);
    }

    public ItemPecaOS atualizarItem(Long id, Integer novaQuantidade) {
        Optional<ItemPecaOS> itemOptional = itemPecaOSRepository.findById(id);
        if (itemOptional.isEmpty()) {
            throw new IllegalArgumentException("ItemPecaOS com ID " + id + " não encontrado.");
        }

        ItemPecaOS item = itemOptional.get();
        item.setQuantidade(novaQuantidade);

        BigDecimal precoFinal = item.getPeca().getPrecoUnitario().multiply(BigDecimal.valueOf(novaQuantidade));
        item.setPrecoFinal(precoFinal);

        return itemPecaOSRepository.save(item);
    }
}
