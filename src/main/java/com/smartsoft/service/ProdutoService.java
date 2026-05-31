package com.smartsoft.service;

import com.smartsoft.dto.ProdutoRequest;
import com.smartsoft.entity.CategoriaProduto;
import com.smartsoft.entity.Produto;
import com.smartsoft.entity.Stock;
import com.smartsoft.entity.Tenant;
import com.smartsoft.exception.BusinessException;
import com.smartsoft.exception.ResourceNotFoundException;
import com.smartsoft.repository.CategoriaProdutoRepository;
import com.smartsoft.repository.ProdutoRepository;
import com.smartsoft.repository.StockRepository;
import com.smartsoft.repository.TenantRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class ProdutoService {

    private final ProdutoRepository produtoRepository;
    private final CategoriaProdutoRepository categoriaRepository;
    private final StockRepository stockRepository;
    private final TenantRepository tenantRepository;

    public ProdutoService(ProdutoRepository produtoRepository,
                          CategoriaProdutoRepository categoriaRepository,
                          StockRepository stockRepository,
                          TenantRepository tenantRepository) {
        this.produtoRepository = produtoRepository;
        this.categoriaRepository = categoriaRepository;
        this.stockRepository = stockRepository;
        this.tenantRepository = tenantRepository;
    }

    @Transactional
    public Produto criarProduto(ProdutoRequest request, String tenantNif) {
        Tenant tenant = tenantRepository.findByNifAndAtivoTrue(tenantNif)
                .orElseThrow(() -> new ResourceNotFoundException("Tenant", "nif", tenantNif));

        if (request.getCodigoProduto() != null &&
                produtoRepository.existsByCodigoProdutoAndTenantNif(request.getCodigoProduto(), tenantNif)) {
            throw new BusinessException("Já existe um produto com o código: " + request.getCodigoProduto());
        }

        Produto produto = new Produto();
        produto.setTenant(tenant);
        produto.setCodigoProduto(request.getCodigoProduto());
        produto.setNomeProduto(request.getNomeProduto());
        produto.setDescricao(request.getDescricao());
        produto.setPrecoVenda(request.getPrecoVenda());
        produto.setPrecoCusto(request.getPrecoCusto());
        produto.setTaxaIva(request.getTaxaIva() != null ? request.getTaxaIva() : "17%");
        produto.setCodigoBarras(request.getCodigoBarras());
        produto.setSku(request.getSku());
        produto.setUnidadeMedida(request.getUnidadeMedida() != null ? request.getUnidadeMedida() : "unidade");
        produto.setAtivo(true);

        if (request.getCategoriaId() != null) {
            categoriaRepository.findById(UUID.fromString(request.getCategoriaId()))
                    .ifPresent(produto::setCategoria);
        }

        Produto saved = produtoRepository.save(produto);

        // Initialize stock record
        Stock stock = new Stock();
        stock.setTenant(tenant);
        stock.setProduto(saved);
        stock.setQuantidadeAtual(0);
        stock.setQuantidadeMinima(10);
        stock.setQuantidadeMaxima(1000);
        stockRepository.save(stock);

        return saved;
    }

    @Transactional(readOnly = true)
    public Page<Produto> listarProdutos(String tenantNif, Pageable pageable) {
        return produtoRepository.findByTenantNifAndAtivoTrue(tenantNif, pageable);
    }

    @Transactional(readOnly = true)
    public Produto buscarProduto(UUID id, String tenantNif) {
        Produto produto = produtoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produto", "id", id));
        if (!produto.getTenant().getNif().equals(tenantNif)) {
            throw new BusinessException("Acesso negado a este produto");
        }
        return produto;
    }

    @Transactional
    public Produto atualizarProduto(UUID id, ProdutoRequest request, String tenantNif) {
        Produto produto = buscarProduto(id, tenantNif);

        if (request.getNomeProduto() != null) produto.setNomeProduto(request.getNomeProduto());
        if (request.getDescricao() != null) produto.setDescricao(request.getDescricao());
        if (request.getPrecoVenda() != null) produto.setPrecoVenda(request.getPrecoVenda());
        if (request.getPrecoCusto() != null) produto.setPrecoCusto(request.getPrecoCusto());
        if (request.getTaxaIva() != null) produto.setTaxaIva(request.getTaxaIva());
        if (request.getUnidadeMedida() != null) produto.setUnidadeMedida(request.getUnidadeMedida());
        if (request.getSku() != null) produto.setSku(request.getSku());
        if (request.getCodigoBarras() != null) produto.setCodigoBarras(request.getCodigoBarras());

        if (request.getCategoriaId() != null) {
            categoriaRepository.findById(UUID.fromString(request.getCategoriaId()))
                    .ifPresent(produto::setCategoria);
        }

        return produtoRepository.save(produto);
    }

    @Transactional
    public void desativarProduto(UUID id, String tenantNif) {
        Produto produto = buscarProduto(id, tenantNif);
        produto.setAtivo(false);
        produtoRepository.save(produto);
    }
}
