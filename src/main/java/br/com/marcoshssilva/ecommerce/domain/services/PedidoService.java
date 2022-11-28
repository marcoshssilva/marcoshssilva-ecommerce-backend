package br.com.marcoshssilva.ecommerce.domain.services;

import java.util.Date;

import br.com.marcoshssilva.ecommerce.domain.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.marcoshssilva.ecommerce.domain.entities.Cliente;
import br.com.marcoshssilva.ecommerce.domain.entities.ItemPedido;
import br.com.marcoshssilva.ecommerce.domain.entities.Pedido;
import br.com.marcoshssilva.ecommerce.domain.repositories.ItemPedidoRepository;
import br.com.marcoshssilva.ecommerce.domain.repositories.PedidoRepository;
import br.com.marcoshssilva.ecommerce.core.security.UserDetailsImpl;

@Service
public class PedidoService {

    @Autowired
    private ClienteService cliService;

    @Autowired
    private PedidoRepository repo;

    @Autowired
    private ItemPedidoRepository ipRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ProdutoService produtoService;

    public Pedido listarPorId(Integer id) {
        return repo.findById(id).orElseThrow(() -> new ObjectNotFoundException("Pedido não encontrado."));
    }

    @Transactional()
    public Pedido cadastrar(Pedido obj) {
        obj.setId(null);
        obj.setInstante(new Date());
        obj = repo.save(obj);

        for (ItemPedido ip : obj.getItens()) {
            ip.setPreco(produtoService.buscar(ip.getProduto().getId()).getPreco());
            ip.setPedido(obj);
            ip.setCurso(produtoService.buscar(ip.getProduto().getId()));
        }

        ipRepo.saveAll(obj.getItens());
        obj.setCliente(cliService.buscar(obj.getCliente().getId()));

        emailService.sendOrderConfirmationHtmlEmail(obj);

        return obj;
    }

    public Page<Pedido> encontrarPagina(Integer page, Integer linesPerPage, String direction, String orderBy) {
        UserDetailsImpl user = UserService.authenticated();
        PageRequest of = PageRequest.of(page, linesPerPage, Direction.fromString(direction), orderBy);
        Cliente c = cliService.buscar(user.getUsername());
        return repo.findByCliente(c, of);
    }
}
