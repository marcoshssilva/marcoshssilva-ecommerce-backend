package br.com.marcoshssilva.ecommerce.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.marcoshssilva.ecommerce.domain.Cliente;
import br.com.marcoshssilva.ecommerce.domain.ItemPedido;
import br.com.marcoshssilva.ecommerce.domain.Pedido;
import br.com.marcoshssilva.ecommerce.repositories.ItemPedidoRepository;
import br.com.marcoshssilva.ecommerce.repositories.PedidoRepository;
import br.com.marcoshssilva.ecommerce.security.UserSS;
import br.com.marcoshssilva.ecommerce.services.exceptions.ObjectNotFoundException;

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
        UserSS user = UserService.authenticated();
        PageRequest of = PageRequest.of(page, linesPerPage, Direction.fromString(direction), orderBy);
        Cliente c = cliService.buscar(user.getUsername());
        return repo.findByCliente(c, of);
    }
}
